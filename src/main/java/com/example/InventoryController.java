package com.example;

import java.util.List;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The {@code InventoryController} class manages the inventory UI and handles interactions
 * with items, including displaying items, selecting items, and updating inventory information.
 */
public class InventoryController {

    private Item selectedItem; // The currently selected item
    private int stats; // Placeholder for item stats
    private int balance; // The user's current balance
    private boolean itemSelected; // Tracks whether an item is selected
    private List<Item> foodItems; // List of food items in the inventory
    private List<Item> toyItems; // List of toy items in the inventory
    private List<Item> beddingItems; // List of bedding items in the inventory

    private static Shop shop; // Shop object for accessing inventory data

    @FXML
    private AnchorPane rootPane; // The root pane of the inventory UI
    @FXML
    private TabPane InventoryPane; // TabPane for organizing inventory categories

    @FXML
    private Label selectedItemStats; // Label displaying stats of the selected item
    @FXML
    private Label selectedItemLabel; // Label displaying the name of the selected item
    @FXML
    private Label selectItemLabel; // Label prompting the user to select an item
    @FXML
    private Label balanceLabel; // Label displaying the user's balance
    @FXML
    private GridPane foodItemsContainer; // GridPane for displaying food items
    @FXML
    private GridPane toyItemsContainer; // GridPane for displaying toy items
    @FXML
    private GridPane beddingItemsContainer; // GridPane for displaying bedding items
    @FXML
    private ImageView exitInventoryIcon; // ImageView for the exit icon

    /**
     * Initializes the inventory controller by setting up styles and interactions.
     */
    public void initialize() {
        rootPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        setupTabPaneStyling();
        setupImageIconInteractions(exitInventoryIcon);
    }

    /**
     * Sets the inventory for the given save file number and populates the inventory UI.
     *
     * @param saveFileNum the save file number
     */
    public void setInventory(int saveFileNum) {
        System.out.println("Setting inventory for save file: " + saveFileNum);
        shop = new Shop("src/main/java/com/example/data/sf" + saveFileNum + "Inventory.json");

        getBalance();

        foodItems = getList("Food");
        toyItems = getList("Toy");
        beddingItems = getList("Bedding");

        populateInventoryItems(foodItemsContainer, foodItems);
        populateInventoryItems(toyItemsContainer, toyItems);
        populateInventoryItems(beddingItemsContainer, beddingItems);
    }

    /**
     * Configures the styling and interaction of the {@code InventoryPane} tabs.
     */
    private void setupTabPaneStyling() {
        InventoryPane.getTabs().forEach(tab -> {
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
     * Sets up hover and click interactions for the given {@code ImageView} icon.
     *
     * @param icon the {@code ImageView} icon to configure
     */
    private void setupImageIconInteractions(ImageView icon) {
        if (icon == exitInventoryIcon) {
            icon.setOnMouseClicked(event -> {
                Stage stage = (Stage) icon.getScene().getWindow();
                stage.close();
            });
        }

        icon.setOnMouseEntered(event -> {
            icon.setStyle("-fx-effect: dropshadow(gaussian, #90EE90, 10, 0.5, 0, 0);");
        });

        icon.setOnMouseExited(event -> {
            icon.setStyle("-fx-effect: null;");
        });
    }

    /**
     * Selects an item and updates the UI with its details.
     *
     * @param item the item to select
     */
    @FXML
    void selectItem(Item item) {
        selectedItem = item;
        int happiness = selectedItem.getHappinessMod();
        int hunger = selectedItem.getHungerMod();
        int sleep = selectedItem.getSleepMod();

        if (!itemSelected) {
            itemSelected = true;
        }
        selectItemLabel.setText("Selected Item: ");
        selectedItemLabel.setText(selectedItem.getName());
        selectedItemStats.setText("Happiness: " + happiness + " Pts\nHunger: " + hunger + " Pts\nSleep: " + sleep + " Pts");
    }

    /**
     * Populates the given {@code GridPane} with items from the provided list.
     *
     * @param container the {@code GridPane} to populate
     * @param items     the list of items to display
     */
    public void populateInventoryItems(GridPane container, List<Item> items) {
        container.getChildren().clear();

        int row = 0;
        int col = 0;

        for (Item item : items) {
            Label itemLabel = new Label(item.getName());
            itemLabel.getStyleClass().add("shop-item-label");

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
     * Retrieves the root pane of the inventory UI.
     *
     * @return the root pane
     */
    public AnchorPane getRoot() {
        return this.rootPane;
    }

    /**
     * Configures the layout of a {@code GridPane} for consistent item spacing.
     *
     * @param gridPane the {@code GridPane} to configure
     */
    private void setupGridPane(GridPane gridPane) {
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < 3; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(33.33);
            gridPane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < 5; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(50);
            gridPane.getRowConstraints().add(row);
        }
    }

    /**
     * Retrieves and displays the user's current balance.
     */
    public void getBalance() {
        balance = 10; // Placeholder value
        balanceLabel.setText("Balance: $" + balance);
    }

    /**
     * Retrieves a list of items from the shop based on the list name.
     *
     * @param listName the name of the item list to retrieve
     * @return a list of items
     */
    private List<Item> getList(String listName) {
        return shop.getList(listName);
    }
}
