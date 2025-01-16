package com.example;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class NewGameController {
    private MediaPlayer mediaPlayer;
    private String catSprite = "../../sprites/Cat Sprites/catv2Neutral.png";
    private String foxSprite = "../../sprites/Fox Sprites/foxNeutral.png";
    private String otterSprite = "../../sprites/SealAlien_neutral.png";

    @FXML
    private Button selectCatButton;
    @FXML
    private Button selectFoxButton;
    @FXML
    private Button selectOtterButton;
    @FXML
    private ImageView petImageView;
    @FXML
    private ImageView catImageView;
    @FXML
    private ImageView foxImageView;
    @FXML
    private ImageView otterImageView;

    @FXML
    private AnchorPane namePetPane;
    @FXML
    private Button exitPetPaneButton;
    @FXML
    private Button createNewGame;

    @FXML
    private TextField petNameField;
    @FXML
    private Label petNameLabel;
    @FXML
    private Button setPetName;

    private String petType;
    public int saveFileNum;

    public void initialize() {
        // Add hover and click animations for the play button
        addButtonAnimations(selectCatButton);
        addButtonAnimations(selectFoxButton);
        addButtonAnimations(selectOtterButton);
        addButtonAnimations(exitPetPaneButton);
        addButtonAnimations(createNewGame);
    }

    public void setSaveFileNum(int num) {
        saveFileNum = num;
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
    private void setPetName() {
        petNameLabel.setText(petNameField.getText());
        createNewGame.setVisible(true);
        // Create new save file with selected character
    }

    private void selectPet() {
        selectCatButton.setVisible(false);
        selectFoxButton.setVisible(false);
        selectOtterButton.setVisible(false);
        petNameLabel.setText("Please name your pet");
        petNameField.clear();

        namePetPane.setVisible(true);
        catImageView.setVisible(false);
        foxImageView.setVisible(false);
        otterImageView.setVisible(false);
        // Create new save file with selected character
    }

    @FXML
    private void exitNamePetPane() {
        namePetPane.setVisible(false);
        selectCatButton.setVisible(true);
        selectFoxButton.setVisible(true);
        selectOtterButton.setVisible(true);
        catImageView.setVisible(true);
        foxImageView.setVisible(true);
        otterImageView.setVisible(true);
        createNewGame.setVisible(false);
    }

    @FXML
    private void selectCat() {
        petImageView.setImage(new Image(getClass().getResourceAsStream(catSprite)));
        petType = "Cat";
        selectPet();
        // Create new save file with cat character
    }
    @FXML
    private void selectFox() {
        petImageView.setImage(new Image(getClass().getResourceAsStream(foxSprite)));
        petType = "Fox";
        selectPet();
        // Create new save file with fox character
    }
    @FXML
    private void selectOtter() {
        petImageView.setImage(new Image(getClass().getResourceAsStream(otterSprite)));
        petType = "Seal";
        selectPet();
        // Create new save file with otter character
    }

    @FXML
    private void createNewGame() {
        GameManager gameManager = new GameManager();
        gameManager.newGame(saveFileNum, petNameField.getText(), petType);
        SaveFile saveFile = gameManager.getSaveFile();

        App.loadGameplay(saveFile, saveFileNum);
    }
}
