package com.example;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainMenuController {
    private MediaPlayer mediaPlayer;
    private GameManager gameManager = new GameManager();
    private GameLoop looping;
    private SaveFile saveFile;

    @FXML
    private Button playButton;
    @FXML
    private Button saveFileOne;
    @FXML
    private Button saveFileTwo;
    @FXML
    private Button saveFileThree;
    @FXML
    private Button saveFileFour;
    @FXML
    private Button exitButton;

    @FXML
    private static AnchorPane rootPane;

    private static Stage newGameStage;
    

    public void initialize() {
        // Set initial visibility of save files to hidden
        saveFileOne.setVisible(false);
        saveFileTwo.setVisible(false);
        saveFileThree.setVisible(false);
        saveFileFour.setVisible(false);

        // Add hover and click animations for the play button
        addButtonAnimations(playButton);
        addButtonAnimations(saveFileOne);
        addButtonAnimations(saveFileTwo);
        addButtonAnimations(saveFileThree);
        addButtonAnimations(saveFileFour);
        addButtonAnimations(exitButton);
    }

    @FXML
    private void loadSaveFiles() {
        // Hide play button and exit button when clicked
        playButton.setVisible(false);
        exitButton.setVisible(false);


        // Load save files and update the button texts individually
        saveFileExist(saveFileOne, 1);
        saveFileExist(saveFileTwo, 2);
        saveFileExist(saveFileThree, 3);
        saveFileExist(saveFileFour, 4);

        if (saveFileOne.getText() == "New Game") {
            saveFileOne.setOnMouseClicked(event -> {loadFile(1, "new");;});
        }
        else {
            saveFileOne.setOnMouseClicked(event -> {loadFile(1, "load");});
        }
        if (saveFileTwo.getText() == "New Game") {
            saveFileTwo.setOnMouseClicked(event -> {loadFile(2, "new");});
        }
        else {
            saveFileTwo.setOnMouseClicked(event -> {loadFile(2, "load");});
        }
        if (saveFileThree.getText() == "New Game") {
            saveFileThree.setOnMouseClicked(event -> {loadFile(3, "new");});
        }
        else {
            saveFileThree.setOnMouseClicked(event -> {loadFile(3, "load");});
        }
        if (saveFileFour.getText() == "New Game") {
            saveFileFour.setOnMouseClicked(event -> {loadFile(4, "new");});
        }
        else {
            saveFileFour.setOnMouseClicked(event -> {loadFile(4, "load");});
        }

        // Show save files buttons
        saveFileOne.setVisible(true);
        saveFileTwo.setVisible(true);
        saveFileThree.setVisible(true);
        saveFileFour.setVisible(true);
    }

    private void saveFileExist(Button saveFileButton, int fileNumber) {
        try {
            String result = GameManager.saveFileExists(fileNumber);
            saveFileButton.setText(result);
        } catch (Exception e) {
            e.printStackTrace(); // Log any exception for debugging
            saveFileButton.setText("Error loading save file " + fileNumber);
        }
    }

    @FXML
    private void loadFile(int saveFileNum, String action) {
        // Call fxml for game screen
        // Create a new stage for the new game window
        saveFileOne.setVisible(false);
        saveFileTwo.setVisible(false);
        saveFileThree.setVisible(false);
        saveFileFour.setVisible(false);
        playButton.setVisible(true);
        exitButton.setVisible(true);

       
        if (action == "load") {
            App.loadGameplay(saveFile, saveFileNum);

        } else App.loadNewGame(saveFileNum);   
        
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

    @FXML
    private void exitGame() {
        // Close the application
        System.exit(0);
    }

    


    public static void loadMenu() {         
        // hide game menu
        newGameStage.close();

        // open the main menu stage
        Stage mainMenuStage = (Stage) rootPane.getScene().getWindow();
        mainMenuStage.show();
    }
}


