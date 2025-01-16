package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The ShopController class manages the user interface and interactions for the in-game shop.
 * It provides functionality to display items, handle purchases, switch between item categories,
 * and manage shopkeeper dialogues.
 */
public class ShopController {
    /**
     * The currently selected item in the shop.
     */
    private Item selectedItem;

    /**
     * The price of the selected item.
     */
    private int price;

    /**
     * The player's current balance.
     */
    private int balance;

    /**
     * Indicates whether an item has been selected.
     */
    private boolean itemSelected;

    /**
     * The current page of items being displayed in the shop.
     */
    private int page = 0;

    /**
     * The maximum number of pages of items.
     */
    private int max_pages = 0;

    /**
     * The list of food items available in the shop.
     */
    private final List<Item> foodItems = getList("Food");

    /**
     * The list of toy items available in the shop.
     */
    private final List<Item> toyItems = getList("Toy");

    /**
     * The list of bedding items available in the shop.
     */
    private final List<Item> beddingItems = getList("Bedding");

    /**
     * The list of items currently being displayed.
     */
    public List<Item> currentList;

    /**
     * List of shopkeeper dialogues to display randomly.
     */
    private final List<String> shopkeeperDialogues = new ArrayList<>();

    /**
     * Random number generator for selecting dialogues.
     */
    private final Random random = new Random();

    /**
     * The {@code GridPane} that currently displays the list of items.
     */
    private GridPane currentListContainer;


    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button purchaseButton;
    @FXML
    private TabPane shopTabPane;
    @FXML
    private ImageView inventoryIcon;
    @FXML
    private ImageView rightPageIcon;
    @FXML
    private ImageView leftPageIcon;
    @FXML
    private ImageView exitShopIcon;

    @FXML
    private Label selectedItemPrice;
    @FXML
    private Label selectedItemLabel;
    @FXML
    private Label selectOrPurchaseLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private GridPane foodItemsContainer;
    @FXML
    private GridPane toyItemsContainer;
    @FXML
    private GridPane beddingItemsContainer;
    @FXML
    private Label shopkeeperDialogue;

    /**
     * The {@code Shop} object for managing item data.
     */
    private static Shop shop = new Shop("src/main/java/com/example/data/shopItems.json");


    /**
     * Initializes the shop UI and its components.
     */
    public void initialize() {
        rootPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        currentList = foodItems;
        currentListContainer = foodItemsContainer;
        populateShopItems(currentListContainer, currentList);
        max_pages = (int) Math.floor(currentList.size() / 9.0);

        getBalance();
        setupTabPaneStyling();
        loadDialogues();
        setRandomDialogue();

        if (inventoryIcon != null) {
            setupImageIconInteractions(inventoryIcon);
        }
        if (rightPageIcon != null) {
            setupImageIconInteractions(rightPageIcon);
        }
        if (leftPageIcon != null) {
            setupImageIconInteractions(leftPageIcon);
        }
        setupImageIconInteractions(exitShopIcon);

        shopTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                handleTabChange(newTab);
            }
        });
    }

    /**
     * Sets up animations and styles for the purchase button to enhance user interaction.
     */
    private void setupPurchaseButtonAnimations() {
        purchaseButton.setStyle("-fx-background-color: #98FB98;");
        purchaseButton.setOnMouseEntered(event -> {
            purchaseButton.setStyle("-fx-background-color: #90EE90;");
            ScaleTransition st = new ScaleTransition(Duration.millis(200), purchaseButton);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });

        purchaseButton.setOnMouseExited(event -> {
            purchaseButton.setStyle("-fx-background-color: #98FB98;");
            ScaleTransition st = new ScaleTransition(Duration.millis(200), purchaseButton);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        purchaseButton.setOnMousePressed(event -> {
            purchaseButton.setStyle("-fx-background-color: #32CD32;");
        });

        purchaseButton.setOnMouseReleased(event -> {
            purchaseButton.setStyle("-fx-background-color: #90EE90;");
        });
    }

    /**
     * Configures the visual styling and behaviour of the tabs in the shop tab pane.
     */
    private void setupTabPaneStyling() {
        shopTabPane.getTabs().forEach(tab -> {
            tab.setStyle("-fx-background-color: #FFDAB9");
            tab.setOnSelectionChanged(event -> {
                if (tab.isSelected()) {
                    tab.setStyle("-fx-background-color: #F0E68C");
                } else {
                    tab.setStyle("-fx-background-color: #FFDAB9");
                }
            });
        });
    }


    /**
     * Configures interaction behaviours for the provided {@code ImageView}.
     * Different behaviours are applied based on the specific icon provided.
     *
     * @param icon the {@code ImageView} to set up interactions for
     */
    private void setupImageIconInteractions(ImageView icon) {
        if (icon == inventoryIcon) {
            icon.setOnMouseClicked(event -> {
                App.loadInventory();
            });

            icon.setOnMouseEntered(event -> {
                icon.setStyle("-fx-effect: dropshadow(gaussian, #90EE90, 10, 0.5, 0, 0);");
            });

            icon.setOnMouseExited(event -> {
                icon.setStyle("-fx-effect: null;");
            });
        } else if (icon == rightPageIcon) {
            icon.setOnMouseEntered(event -> {
                if (page < max_pages) {
                    icon.setStyle("-fx-effect: dropshadow(gaussian, #90EE90, 10, 0.5, 0, 0);");
                }
            });

            icon.setOnMouseExited(event -> {
                icon.setStyle("-fx-effect: null;");
            });

            icon.setOnMouseClicked(event -> {
                if (page < max_pages) {
                    page++;
                    populateShopItems(currentListContainer, currentList);
                }
            });
        } else if (icon == leftPageIcon) {
            icon.setOnMouseEntered(event -> {
                if (page > 0) {
                    icon.setStyle("-fx-effect: dropshadow(gaussian, #90EE90, 10, 0.5, 0, 0);");
                }
            });

            icon.setOnMouseExited(event -> {
                icon.setStyle("-fx-effect: null;");
            });

            icon.setOnMouseClicked(event -> {
                if (page > 0) {
                    page--;
                    populateShopItems(currentListContainer, currentList);
                }
            });
        } else if (icon == exitShopIcon) {
            icon.setOnMouseClicked(event -> {
                App.loadGameplayScreen();
            });
            icon.setOnMouseEntered(event -> {
                icon.setStyle("-fx-effect: dropshadow(gaussian, #90EE90, 10, 0.5, 0, 0);");
            });

            icon.setOnMouseExited(event -> {
                icon.setStyle("-fx-effect: null;");
            });
        }
    }

    /**
     * Handles the change of tabs in the shop tab pane.
     * Updates the currently displayed list of items and resets pagination.
     *
     * @param tab the newly selected tab
     */
    private void handleTabChange(Tab tab) {
        switch (tab.getText()) {
            case "Food":
                currentList = foodItems;
                currentListContainer = foodItemsContainer;
                break;
            case "Toys":
                currentList = toyItems;
                currentListContainer = toyItemsContainer;
                break;
            case "Bedding":
                currentList = beddingItems;
                currentListContainer = beddingItemsContainer;
                break;
            default:
                break;
        }
        page = 0;
        max_pages = (int) Math.floor(currentList.size() / 9.0);
        populateShopItems(currentListContainer, currentList);
    }

    /**
     * Selects an item in the shop and displays its details, including price and stats.
     * Also prepares the purchase button for interaction if an item has been selected.
     *
     * @param item the item to select
     */
    @FXML
    void selectItem(Item item) {
        getBalance();
        selectedItem = item;
        price = item.getPrice(); // Example price, can be dynamic later
        if (!itemSelected) {
            setupPurchaseButtonAnimations();
            itemSelected = true;
        }
        selectOrPurchaseLabel.setText("Selected Item: \n" + selectedItem.getName());
        selectedItemLabel.setText("\nHappiness: " + selectedItem.getHappinessMod() + " Pts\nHunger: " + selectedItem.getHungerMod() + " Pts\nSleep: " + selectedItem.getSleepMod() + " Pts");
        selectedItemPrice.setText("Price: $" + price);
    }

    /**
     * Populates the shop UI with items in the current list, displaying them in a grid layout.
     *
     * @param container the {@code GridPane} where items will be displayed
     * @param items the list of items to display
     */
    public void populateShopItems(GridPane container, List<Item> items) {
        // Clear the container first to avoid duplicates
        container.getChildren().clear();

        int row = 0;
        int col = 0;

        for (int i = 9 * page; i < 9 * page + 9; i++) {
            if (i >= items.size()) {
                break;
            }
            Item item = items.get(i);
            if (row >= 3) {
                break;
            }
            // Create a label for the item
            Label itemLabel = new Label(item.getName());
            itemLabel.getStyleClass().add("shop-item-label");

            // Add animation on hover
            itemLabel.setOnMouseEntered(event -> {
                itemLabel.setStyle("-fx-background-color: #F0E68C;");
                ScaleTransition st = new ScaleTransition(Duration.millis(200), itemLabel);
                st.setToX(1.1);
                st.setToY(1.1);
                st.play();
            });

            itemLabel.setOnMouseExited(event -> {
                itemLabel.setStyle("-fx-background-color: #FFDAB9;");
                ScaleTransition st = new ScaleTransition(Duration.millis(200), itemLabel);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();
            });

            itemLabel.setOnMouseClicked(event -> selectItem(item));

            // Add the label to the grid
            container.add(itemLabel, col, row);

            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }
        setupGridPane(container);
    }

    /**
     * Retrieves the player's current balance from the shop and updates the balance label.
     */
    public void getBalance() {
        balance = shop.getBalance();
        balanceLabel.setText("Balance: $" + balance);
    }

    /**
     * Handles the purchase of the selected item. Checks if the player has enough balance
     * and updates the UI accordingly. If the purchase is successful, deducts the price
     * and updates the balance.
     *
     * @param event the {@code MouseEvent} triggered by clicking the purchase button
     */
    public void handlePurchaseItem(MouseEvent event) {
        if (itemSelected) {
            getBalance();
            if (balance >= price) {
                shop.buy(selectedItem);
                selectOrPurchaseLabel.setText("Item purchased: ");
                balanceLabel.setText("Balance: $" + balance);
                // Add logic to handle the purchase, e.g., update inventory, etc.
            } else {
                selectOrPurchaseLabel.setText("Insufficient balance!");
                selectedItemLabel.setText("");
                selectedItemPrice.setText("");
            }
        }
    }

    /**
     * Returns the root {@code AnchorPane} of the shop UI.
     *
     * @return the root {@code AnchorPane}
     */
    public AnchorPane getRoot() {
        return this.rootPane;
    }

    /**
     * Loads shopkeeper dialogues from a file. The dialogues are stored in the
     * {@code shopkeeperDialogues} list for random display.
     */
    private void loadDialogues() {
        try (InputStream inputStream = getClass().getResourceAsStream("/shopkeeper_dialogues.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                shopkeeperDialogues.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a random dialogue from the loaded shopkeeper dialogues to be displayed.
     */
    private void setRandomDialogue() {
        if (!shopkeeperDialogues.isEmpty()) {
            String dialogue = shopkeeperDialogues.get(random.nextInt(shopkeeperDialogues.size()));
            shopkeeperDialogue.setText(dialogue);
        }
    }

    /**
     * Configures the grid pane layout for displaying shop items.
     * Ensures uniform column widths and row heights for a consistent layout.
     *
     * @param gridPane the {@code GridPane} to configure
     */
    private void setupGridPane(GridPane gridPane) {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        // Set uniform column width for 3 columns
        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(33.33);
            gridPane.getColumnConstraints().add(column);
        }

        // Set row constraints to ensure consistent spacing
        for (int i = 0; i < 3; i++) { // Assuming a maximum of 5 rows for consistent spacing
            RowConstraints row = new RowConstraints();
            row.setMinHeight(50); // Minimum height to ensure consistent spacing
            gridPane.getRowConstraints().add(row);
        }
    }

    /**
     * Retrieves a list of items by category from the shop.
     *
     * @param listName the category name (e.g., "Food", "Toy", "Bedding")
     * @return the list of items in the specified category
     */
    private List<Item> getList(String listName) {
        return shop.getList(listName);
    }
}
