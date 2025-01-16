package com.example;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

// *** NOTE: Save when x is pressed (to close application window)
public class GameplayScene implements EventHandler<ActionEvent> {
    private static Stage pStage;

    private static Scene gameplay;

    // PANES (load in from their respective classes)
    private final static Pane layout = new Pane(); // Gameplay
    private static Pane settings = new Pane();
    // END OF PANES

    private boolean canInteract = true; // Cooldown for buttons

    // VARIABLES FROM OTHER CLASSES
    public GameLoop gameLoop;
    private SaveFile saveFile;
    private Pet pet;
    // END OF OTHER VARIABLES

    // UI OBJECTS
    private static SettingsUI settingsUI;
    // END OF UI OBJECTS

    // GAMEPLAY
    // Action buttons
    Button feedButton = new Button();
    Button sleepButton = new Button();
    Button playButton = new Button();
    Button exerciseButton = new Button();
    Button vetButton = new Button();

    // New menu buttons
    Button settingsButton = new Button();
    Button inventoryButton = new Button();
    Button shopButton = new Button();

    // Stat bars
    int barTextSize = 12;
    double barSize = 115.0;

    Text hpText = new Text("Health");
    Rectangle healthBarBase = new Rectangle(120.0, 25.0, Color.BLACK);
    Rectangle healthBar = new Rectangle(barSize, 20.0, Color.GREENYELLOW);

    Text hungerText = new Text("Hunger");
    Rectangle hungerBarBase = new Rectangle(120.0, 25.0, Color.BLACK);
    Rectangle hungerBar = new Rectangle(barSize, 20.0, Color.ORANGERED);

    Text happyText = new Text("Happiness");
    Rectangle happinessBarBase = new Rectangle(120.0, 25.0, Color.BLACK);
    Rectangle happinessBar = new Rectangle(barSize, 20.0, Color.YELLOW);

    Text sleepText = new Text("Sleepiness");
    Rectangle sleepBarBase = new Rectangle(120.0, 25.0, Color.BLACK);
    Rectangle sleepBar = new Rectangle(barSize, 20.0, Color.BLUE);

    // Pet sprite
    Rectangle petWindow = new Rectangle(350.0, 350.0, Color.WHITE);
    Rectangle petSprite = new Rectangle(90, 90, Color.GOLDENROD);

    // Images
    public Image petInitial;
    public Image petSleeping;

    public Text petName = new Text("Loading Pet Name"); // Load in pet name

    Image gameplayBackground = new Image("/sprites/gameplay_background.png");
    Image vetBackground = new Image("/sprites/vet.jpg");

    public void setSaveFile(SaveFile saveFile) {
        this.saveFile = saveFile;
    
        // Populate UI based on save data
        updateUIWithSaveData();
    }
    
    private void updateUIWithSaveData() {
        if (saveFile == null) return;
    
        // Update UI elements (e.g., pet data, stats, etc.)
        pet = saveFile.getPet();
        System.out.println("Pet Type: " + pet.getType());
        if (pet.getType().equals("Cat")) {
            petInitial = new Image("/sprites/Cat Sprites/catv2Neutral.png");
            petSleeping = new Image("/sprites/Cat Sprites/catv2Sleeping.png");
        }
        else if (pet.getType().equals("Seal")) {
            petInitial = new Image("/sprites/Seal Sprites/sealNeutral.png");
            petSleeping = new Image("/sprites/Seal Sprites/sealSleeping.png");
        }
        else {
            petInitial = new Image("/sprites/Fox Sprites/foxNeutral.png");
            petSleeping = new Image("/sprites/Fox Sprites/foxSleeping.png");
        }

        petSprite.setLayoutX(318);
        petSprite.setLayoutY(250);
        petSprite.setFill(new ImagePattern(petInitial));

        petName.setText(pet.getName());
        petName.setTextOrigin(VPos.CENTER);
        petName.setTextAlignment(TextAlignment.CENTER);
        petName.setFont(Font.font("Old English Text MT", FontWeight.BOLD, FontPosture.REGULAR, 30));
        petName.setLayoutX(350 - (petName.getLayoutBounds().getWidth())/2);
        petName.setLayoutY(38);

        if (pet != null) {
            hpText.setText("Health: " + pet.getHealth());
            hungerText.setText("Hunger: " + pet.getHunger());
            happyText.setText("Happiness: " + pet.getHappiness());
            sleepText.setText("Sleepiness: " + pet.getSleep());

            if (!pet.getIsSick()) 
            {
                hideVetButton(); // *** Check if isSick, if not, then keep, else, dont run
            } 
    
            // Update bars
            updateHealth(pet.getHealth());
            updateHunger(pet.getHunger());
            updateHappiness(pet.getHappiness());
            updateSleep(pet.getSleep());
            pet.updateMood();
        }
    
        // Update inventory, money, etc.
        System.out.println("Money: " + saveFile.getMoney());
        // Add more as needed
    }
        
    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }
    
    public GameplayScene(Stage stage)
    {
        pStage = stage;       

        // load in settings
        settingsUI = new SettingsUI();
        settings = settingsUI.getSettings();

        setupGameplayUI();
    }

    private void setupGameplayUI()
    {
        Rectangle bg1 = new Rectangle(700.0, 400.0, Color.WHITE);
        bg1.setStrokeType(StrokeType.INSIDE);
        bg1.setStroke(Color.BLACK);
        bg1.strokeWidthProperty().setValue(2);

        petWindow.setLayoutX(175);
        petWindow.setLayoutY(25);
        petWindow.setStrokeType(StrokeType.OUTSIDE);
        petWindow.setStroke(Color.BLACK);
        petWindow.strokeWidthProperty().setValue(2);
        petWindow.setFill(new ImagePattern(gameplayBackground));

        // Setup bars
        setupBar(healthBarBase, healthBar, hpText, 20);
        setupBar(hungerBarBase, hungerBar, hungerText, 65 + barTextSize);
        setupBar(happinessBarBase, happinessBar, happyText, 120 + barTextSize);
        setupBar(sleepBarBase, sleepBar, sleepText, 175 + barTextSize);
        // Setup action buttons
        setupActionButton(feedButton, "Feed", 20);
        setupActionButton(sleepButton, "Put to Bed", 190);
        setupActionButton(playButton, "Play", 360);
        setupActionButton(exerciseButton, "Exercise", 530);

        addButtonAnimations(feedButton);
        addButtonAnimations(sleepButton);
        addButtonAnimations(playButton);
        addButtonAnimations(exerciseButton);

        vetButton.setText("Take to Vet");
        vetButton.setLayoutX(435);
        vetButton.setLayoutY(345);
        vetButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 12));
        vetButton.setStyle("-fx-background-color: #ffe985;");
        vetButton.setOnAction(this);
        
        // Setup UI buttons
        settingsButton.setPrefSize(30, 30);
        settingsButton.setLayoutX(668 - settingsButton.getWidth());
        settingsButton.setLayoutY(2);
        settingsButton.setStyle("-fx-background-image: url('/sprites/settings_icon.png');" +
                "-fx-background-size: cover;" +
                "-fx-border-color: transparent;" +
                "-fx-background-color: transparent;");
        settingsButton.setOnAction(this);
        settingsButton.setOnMouseEntered(event -> {
            settingsButton.setStyle("-fx-background-image: url('/sprites/settings_icon.png');" +
                    "-fx-background-size: cover;" +
                    "-fx-border-color: transparent;" +
                    "-fx-background-color: transparent;" +
                    "-fx-effect: dropshadow(gaussian, #90EE90, 10, 0.5, 0, 0);");
        });
        settingsButton.setOnMouseExited(event -> {
            settingsButton.setStyle("-fx-background-image: url('/sprites/settings_icon.png');" +
                    "-fx-background-size: cover;" +
                    "-fx-border-color: transparent;" +
                    "-fx-background-color: transparent;");
        });

        // Set size and layout for inventoryButton
        inventoryButton.setPrefSize(60, 60); // Fixed size
        inventoryButton.setLayoutX(690 - 60); // Adjusted for prefWidth
        inventoryButton.setLayoutY(395 - 60); // Adjusted for prefHeight
        inventoryButton.setStyle("-fx-background-image: url('/sprites/inventory_icon.png');" +
                "-fx-background-size: cover;" +
                "-fx-border-color: transparent;" +
                "-fx-background-color: transparent;");
        inventoryButton.setOnAction(this);
        inventoryButton.setOnMouseEntered(event -> {
            inventoryButton.setStyle("-fx-background-image: url('/sprites/inventory_icon.png');" +
                    "-fx-background-size: cover;" +
                    "-fx-border-color: transparent;" +
                    "-fx-background-color: transparent;" +
                    "-fx-effect: dropshadow(gaussian, #90EE90, 10, 0.5, 0, 0);");
        });
        inventoryButton.setOnMouseExited(event -> {
            inventoryButton.setStyle("-fx-background-image: url('/sprites/inventory_icon.png');" +
                    "-fx-background-size: cover;" +
                    "-fx-border-color: transparent;" +
                    "-fx-background-color: transparent;");
        });

        // Set size and layout for shopButton
        shopButton.setPrefSize(60, 60); // Fixed size
        shopButton.setLayoutX(10); // Positioned at 2px from the left
        shopButton.setLayoutY(395 - 60); // Adjusted for prefHeight
        shopButton.setStyle("-fx-background-image: url('/sprites/shop_icon.png');" +
                "-fx-background-size: cover;" +
                "-fx-border-color: transparent;" +
                "-fx-background-color: transparent;");
        shopButton.setOnAction(this);
        shopButton.setOnMouseEntered(event -> {
            shopButton.setStyle("-fx-background-image: url('/sprites/shop_icon.png');" +
                    "-fx-background-size: cover;" +
                    "-fx-border-color: transparent;" +
                    "-fx-background-color: transparent;" +
                    "-fx-effect: dropshadow(gaussian, #90EE90, 10, 0.5, 0, 0);");
        });
        shopButton.setOnMouseExited(event -> {
            shopButton.setStyle("-fx-background-image: url('/sprites/shop_icon.png');" +
                    "-fx-background-size: cover;" +
                    "-fx-border-color: transparent;" +
                    "-fx-background-color: transparent;");
        });
        
        // Pet window
        layout.getChildren().add(bg1);
        layout.getChildren().add(petWindow);
        layout.getChildren().add(petName);
        layout.getChildren().add(petSprite);
        // UI Buttons
        layout.getChildren().add(settingsButton);
        layout.getChildren().add(inventoryButton);
        layout.getChildren().add(shopButton);
        // Add bars
        // Health
        layout.getChildren().add(hpText);
        layout.getChildren().add(healthBarBase);
        layout.getChildren().add(healthBar);
        //Hunger
        layout.getChildren().add(hungerText);
        layout.getChildren().add(hungerBarBase);
        layout.getChildren().add(hungerBar);
        //Happiness
        layout.getChildren().add(happyText);
        layout.getChildren().add(happinessBarBase);
        layout.getChildren().add(happinessBar);
        // Sleep
        layout.getChildren().add(sleepText);
        layout.getChildren().add(sleepBarBase);
        layout.getChildren().add(sleepBar);
        // Add buttons to pane
        layout.getChildren().add(feedButton);
        layout.getChildren().add(sleepButton);
        layout.getChildren().add(playButton);
        layout.getChildren().add(exerciseButton);
        layout.getChildren().add(vetButton);
        // Add panes
        layout.getChildren().add(settings);
        layout.getChildren().add(settingsUI.tutorialUI.getTutorial());
        layout.getChildren().add(settingsUI.pControlsUI.getParentalControls());
        
        gameplay = new Scene(layout, 700, 500);
    }
    // END OF GAMEPLAY

    public static Scene getGameplay()
    {
        return gameplay;
    }

    private void addButtonAnimations(Button button) {
        // Hover animation
        button.setOnMouseEntered(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });
        button.setOnMouseExited(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Click animation
        button.setOnMousePressed(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(0.9);
            st.setToY(0.9);
            st.play();
        });
        button.setOnMouseReleased(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });
    }

    private void setupActionButton(Button btn, String txt, int xPos)
    {
        btn.setPrefSize(150, 50);
        btn.setLayoutX(xPos);
        btn.setLayoutY(430);
        btn.setText(txt);
        btn.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btn.setStyle("-fx-background-color: #ffe985;");
        btn.setOnAction(this);
    }

    private void setupBar(Rectangle base, Rectangle bar, Text textObj, int yPos)
    {
        textObj.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, barTextSize));
        textObj.setLayoutX(15); 
        textObj.setLayoutY(yPos);

        base.setLayoutX(15);
        base.setLayoutY(yPos + barTextSize);

        bar.setLayoutX(15 + (base.getWidth() - bar.getWidth())/2);
        bar.setLayoutY(yPos + barTextSize + (base.getHeight() - bar.getHeight())/2);
    }

    private double roundToPositive(double num)
    {
        if (num < 0) 
        {
            return 0;
        }

        return num;
    }

    // *** currentHealth The current health of pet, sent over by pet class when health updates
    public void updateHealth(double currentHealth)
    {
        double percentage = currentHealth / 100;
        healthBar.setWidth(barSize * roundToPositive(percentage));
        hpText.setText("Health: " + currentHealth);
    }

    // *** updateHunger The current hunger of pet, sent over by pet class when hunger updates
    public void updateHunger(double currentHunger)
    {
        double percentage = currentHunger / 100;
        hungerBar.setWidth(barSize * roundToPositive(percentage));
        hungerText.setText("Hunger: " + currentHunger);
    }

    // *** currentHealth The current happiness of pet, sent over by pet class when happiness updates
    public void updateHappiness(double currentHappiness)
    {
        double percentage = currentHappiness / 100;
        happinessBar.setWidth(barSize * roundToPositive(percentage));
        happyText.setText("Happiness: " + currentHappiness);
    }

    // *** currentHealth The current sleep of pet, sent over by pet class when sleep updates
    public void updateSleep(double currentSleep)
    {
        double percentage = currentSleep / 100;
        sleepBar.setWidth(barSize * roundToPositive(percentage));
        sleepText.setText("Sleepiness: " + currentSleep);
    }

    // Animations for pet actions (test for now)
    private void feedAnim() // add parameter of actual food item
    {     
        Rectangle item = new Rectangle(64, 64);
        Image foodImg = new Image("/sprites/banana.png"); // replace with actual food item selected "/sprites/" + foodItemName + ".png"
        item.setLayoutX(328);
        item.setLayoutY(290);
        item.setFill(new ImagePattern(foodImg));
        layout.getChildren().add(item);

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(item);
        rotate.setDuration(Duration.seconds(0.2));
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setByAngle(10);
        rotate.setAutoReverse(true);

        App.playEatSound();
        PauseTransition pause = new PauseTransition(
            Duration.seconds(1.5)
        );
        pause.setOnFinished(event -> {
            layout.getChildren().remove(item);
            canInteract = true;
            rotate.stop();
            //gameplayManager.onButtonClick(1);
            // Play DING 
            pet.stopDoingAction();
            App.playFinishedActionSound();
        });    
        pause.play();   
        rotate.play();
    }

    private void playAnim()
    {
        Rectangle playItem = new Rectangle(64, 64);
        Image toyImg = new Image("/sprites/banana.png"); // replace with actual food item selected "/sprites/" + foodItemName + ".png"
        playItem.setLayoutX(225);
        playItem.setLayoutY(150);
        playItem.setFill(new ImagePattern(toyImg));

        layout.getChildren().add(playItem);

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(playItem);
        rotate.setDuration(Duration.seconds(0.5));
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setByAngle(720);

        TranslateTransition translation = new TranslateTransition();
        translation.setNode(playItem);
        translation.setDuration(Duration.seconds(0.6));
        translation.setCycleCount(5);
        translation.setByX(200);
        translation.setAutoReverse(true);
        translation.setOnFinished(event -> {
            layout.getChildren().remove(playItem);
            canInteract = true;
            rotate.stop();
            //gameplayManager.onButtonClick(2);
            // Play DING 
            pet.stopDoingAction();
            App.playFinishedActionSound();
        });

        App.playPlayingSound();
        translation.play();
        rotate.play();
    }

    private void sleepAnim()
    {
        petSprite.setFill(new ImagePattern(petSleeping));
        App.playSleepSound();

        PauseTransition pause = new PauseTransition(
            Duration.seconds(4.6)
        );
        pause.setOnFinished(event -> {
            canInteract = true;
            petSprite.setFill(new ImagePattern(petInitial)); // Check states when savefile is good
            //gameplayManager.onButtonClick(3);
            // Play DING 
            pet.stopDoingAction();
            App.playFinishedActionSound();
        }); 
        pause.play();
    }

    private void vetAnim()
    {
        petWindow.setFill(new ImagePattern(vetBackground));

        PauseTransition pause = new PauseTransition(
            Duration.seconds(3)
        );
        pause.setOnFinished(event -> {
            canInteract = true;
            petWindow.setFill(new ImagePattern(gameplayBackground)); // Check states when savefile is good
            pet.setIsSick(false);
            pet.setIsSick(false);
            hideVetButton();
            // Play DING 
            pet.stopDoingAction();
            App.playFinishedActionSound();
        }); 
        pause.play();
    }

    private void exerciseAnim()
    {
        TranslateTransition translation = new TranslateTransition();
        translation.setNode(petSprite);
        translation.setDuration(Duration.seconds(0.6));
        translation.setCycleCount(4);
        translation.setByY(-100);
        translation.setAutoReverse(true);
        translation.setOnFinished(event -> {
            canInteract = true;
            //gameplayManager.onButtonClick(5);
            // Play DING 
            pet.stopDoingAction();
            App.playFinishedActionSound();
        });

        App.playPlayingSound();
        translation.play();
    }

    private void doPetAction(String action)
    {
        canInteract = false;
        if (action == "feed") 
        {
            // *** PNG of food will appear, food will "enter" pet and disappear
            // *** Invoke play method (update stats) in gameplay/pet class
            feedAnim();
            Item item = new Item(1, "apple", "feed", "food", 10, 5, 15, 0);
            pet.feed(item);
        }
        else if (action == "sleep") 
        {
            // *** Pet switches to "sleep" sprite, will sleep for 10 seconds
            // *** Invoke sleep method (update stats) in gameplay/pet class
            sleepAnim();
            Item item = new Item(2, "bed", "sleep", "bed", 20, 0, 0, 20);
            pet.sleep(item);
        }
        else if (action == "play") 
        {
            // *** PNG of toy will appear, pet will go up and down on the toy 3 times and then animation will end
            // *** Invoke play method (update stats) in gameplay/pet class
            playAnim();
            Item item = new Item(3, "ball", "play", "toy", 5, 15, 5, 0);
            pet.play(item);
        }
        else if (action == "exercise") 
        {
            // *** Pet zooms across the screen once
            // *** Invoke play method (update stats) in gameplay/pet class
            exerciseAnim();
            pet.exercise();
        }
        else if (action == "vet")
        {
            // *** Taking to vet will change background and a needle will appear and then your pet will be cured
            // *** Return back to house, lose some happiness however (pets hate vets!)
            vetAnim();
            pet.goToVet();
        }
    }

    // *** Will run when isSick is set to true
    public void showVetButton()
    {
        if (!pet.getIsSick()) 
        {
            hideVetButton();
            return;
        }
        vetButton.setLayoutX(435);
    }

    public void stopGameLoop()
    {
        if (this.gameLoop != null) 
        {
            System.out.println("Stopped game loop");
            this.gameLoop.setRunning(false);
        }
    }

    // For use after revival only
    private void startGameLoop()
    {
        System.out.println("Revived game loop");
        this.gameLoop.start(saveFile);
        hideVetButton();
    }

    // For sure add, sent from Pet.java
    public void changePetSprite(String mood)
    {
        if (this.pet == null || this.pet.isDoingAction()) 
        {
            return;
        }

        String petSpritePath;
        
        if (this.pet.getType().equals("Cat")) 
        {
            petSpritePath = "/sprites/Cat Sprites/catv2";
        }
        else if (this.pet.getType().equals("Seal")) 
        {
            petSpritePath = "/sprites/Seal Sprites/seal";
        }
        else
        {
            petSpritePath = "/sprites/Fox Sprites/fox";
        }

        if (mood == "dead") 
        {
            petSpritePath += "Dead.png";
        }
        else if (mood == "depressed") 
        {
            petSpritePath += "Depressed.png";
        }
        else if (mood == "sick") 
        {
            petSpritePath += "Sick.png";
        }
        else if (mood == "normal") 
        {
            petSpritePath += "Neutral.png";
        }
        else if (mood == "hungry") 
        {
            petSpritePath += "Starving.png";
        }
        else if (mood == "sleepy") 
        {
            petSpritePath += "Sleeping.png";
        }
        else 
        {
            return;
        }

        System.out.println(petSpritePath);
        petSprite.setFill(new ImagePattern(new Image(petSpritePath)));
    }

    public void revivePet()
    {
        if (this.pet.getIsDead()) 
        {
            this.pet.revivePet();
            startGameLoop();
        }
    }

    private void hideVetButton()
    {
        vetButton.setLayoutX(-1000);
    }

    // Show/hide time left
    private void toggleTimeLimitDisplay()
    {
        int timeLeft = 10; // *** Get time limit data 
        // Display time left
    }

    private void openShop()
    {
        // *** Load shop scene
        App.loadShop();
    }

    private void openInventory()
    {
        // *** Load inventory
        App.loadInventory();
    }

    @Override
    public void handle(ActionEvent event)
    {
        if (!canInteract) 
        {
            return;
        }

        App.playClickSound();
        // UI button events
        if (event.getSource() == settingsButton) {
            settingsUI.openSettings();
        }
        else if (event.getSource() == inventoryButton) {
            openInventory();
        }
        else if (event.getSource() == shopButton) {
            openShop();
        }

        if (this.pet.getIsDead())
        {
            return;
        }

        // Pet action events
        if (event.getSource() == feedButton && this.pet.getSleep() > 0) 
        {
            doPetAction("feed");
        }
        else if (event.getSource() == sleepButton) {
            doPetAction("sleep");
        }
        else if (event.getSource() == playButton && this.pet.getSleep() > 0) {
            doPetAction("play");
        }
        else if (event.getSource() == exerciseButton && this.pet.getSleep() > 0) {
            doPetAction("exercise");
        }
        else if (event.getSource() == vetButton) 
        {
            doPetAction("vet");
        }
    }
}
