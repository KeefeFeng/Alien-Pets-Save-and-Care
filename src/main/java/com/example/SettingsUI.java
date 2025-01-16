package com.example;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SettingsUI implements EventHandler<ActionEvent> 
{
    private final static Pane settings = new Pane();

    public static ParentalControlsUI pControlsUI;
    public static TutorialUI tutorialUI;

    private Settings settingsData;

    int sliderSize = 180;

    Text mVolText = new Text("Music Volume");
    Slider mVolSlider = new Slider(0, 100, 50);

    Text sfxVolText = new Text("SFX Volume");
    Slider sfxVolSlider = new Slider(0, 100, 50);

    Button tutorialButton = new Button();
    Button pControlsButton = new Button();
    Button mainMenuButton = new Button();
    Button closeSettingsButton = new Button();

    public SettingsUI()
    {
        pControlsUI = new ParentalControlsUI();
        tutorialUI = new TutorialUI();

        // Get settingsData from Menu.java

        setupSettingsUI();
    }

    private void setupSettingsUI()
    {
        Rectangle black = new Rectangle(700.0, 550.0, Color.BLACK);
        black.opacityProperty().setValue(0.5);

        Rectangle bg = new Rectangle(200.0, 300.0, Color.WHITE);
        bg.setLayoutX(250);
        bg.setLayoutY(100);
        bg.setStrokeType(StrokeType.OUTSIDE);
        bg.setStroke(Color.BLACK);

        Text title = new Text("Settings");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 18));
        title.setLayoutX(245 + (title.getLayoutBounds().getWidth()));
        title.setLayoutY(120);

        mVolText.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 14));
        mVolText.setLayoutX(260);
        mVolText.setLayoutY(160);

        setupSlider(mVolSlider, 180);
        mVolSlider.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observableValue,  Number oldValue,  Number newValue) { 
                      // *** Change volume in settings data to newValue
                      //settingsData.setBGM((int) newValue);
                      App.setVolume((double) newValue);
                  }
            });

        sfxVolText.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 14));
        sfxVolText.setLayoutX(260);
        sfxVolText.setLayoutY(220);

        setupSlider(sfxVolSlider, 240);
        sfxVolSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue,  Number oldValue,  Number newValue) { 
                  // *** Change volume in settings data to newValue
                  App.setSFXVolume((double) newValue);;
              }
        });

        setupSettingsButton(tutorialButton, "Tutorial", 270);
        setupSettingsButton(pControlsButton, "Parental Controls", 310);
        setupSettingsButton(mainMenuButton, "Save and Quit", 350);

        closeSettingsButton.setText("X");
        closeSettingsButton.setLayoutX(427);
        closeSettingsButton.setLayoutY(100);
        closeSettingsButton.setOnAction(this);

        settings.getChildren().add(black);
        settings.getChildren().add(bg);
        settings.getChildren().add(title);
        settings.getChildren().add(mVolText);
        settings.getChildren().add(mVolSlider);
        settings.getChildren().add(sfxVolText);
        settings.getChildren().add(sfxVolSlider);
        settings.getChildren().add(tutorialButton);
        settings.getChildren().add(pControlsButton);
        settings.getChildren().add(mainMenuButton);
        settings.getChildren().add(closeSettingsButton);

        settings.setLayoutY(-1000);
    }

    private void setupSettingsButton(Button btn, String txt, int yPos)
    {
        int buttonWidth = 140;
        int buttonHeight = 30;

        btn.setText(txt);
        btn.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        btn.setPrefSize(buttonWidth, buttonHeight);
        btn.setLayoutX(280);
        btn.setLayoutY(yPos);
        btn.setOnAction(this);
    }

    private void setupSlider(Slider slider, int yPos)
    {
        slider.setPrefWidth(sliderSize);
        slider.setLayoutX(260);
        slider.setLayoutY(yPos);
    }

    public Pane getSettings()
    {
        return settings;
    }

    public void openSettings()
    {
        settings.setLayoutY(-15);
    }

    private void closeSettings()
    {
        settings.setLayoutY(-1000);
    }

    private void openTutorial()
    {
        closeSettings();
        // Move tutorial pane
        tutorialUI.getTutorial().setLayoutX(0);
    }

    private void openParentalControls()
    {
        closeSettings();
        if (pControlsUI.getPassword() != null && !pControlsUI.getPassword().equals("")) 
        {
            pControlsUI.getLoginScreen().setLayoutX(0);
        }
        pControlsUI.closePassResetMenu();
        pControlsUI.clearPassText();
        pControlsUI.getParentalControls().setLayoutY(0);
    }

    private void loadMainMenu()
    {
        // *** Invoke save function on current save file
        // GameManager.saveGame();
        // *** Load in as FXML
        // Static function that loads in main menu from MainMenuController.java
        App.loadMenu();
        closeSettings();
    }

    @Override
    public void handle(ActionEvent event)
    {
        App.playClickSound();
        // Settings button events
        if (event.getSource() == tutorialButton) 
        {
            openTutorial();
        }
        else if (event.getSource() == pControlsButton) 
        {
            openParentalControls();
        }
        else if (event.getSource() == mainMenuButton) 
        {
            // *** Load in main menu scene
            loadMainMenu();
        }
        else if (event.getSource() == closeSettingsButton) 
        {
            closeSettings();
        }
    }
}
