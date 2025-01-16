package com.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Represents the user interface for the tutorial screen.
 * 
 * Displays instructions on how to play the game, including tips on managing
 * the pet's health, hunger, happiness, and sleep. Includes a close button
 * to exit the tutorial and return to the previous screen.
 */
public class TutorialUI implements EventHandler<ActionEvent> 
{
    // Pane to contain all tutorial UI elements
    private static Pane tutorial = new Pane();

    // Text containing the tutorial instructions
    Text tutorialText = new Text("Yo lemme tell you how to play lil' bro. First \noff, " 
    + "you see health? Yea, your pet can die. \nFeed your pet and take it to the vet when \nneeded. "
    + "The hunger bar will show you when \nthey need to be fed. Play with your pet and \nfeed "
    + "your pet their favourite foods to make \nthem happy. But also be aware, they get \ntired as "
    + "the day passes. Make sure to tuck \nthem into bed so they get enough rest. \nAnyways, good luck!");

    // Button to close the tutorial
    Button closeTutorialButton = new Button();

    // Constructor to initialize the tutorial UI
    public TutorialUI()
    {
        setupTutorialUI();
    }

    // Sets up the layout and design of the tutorial UI
    private void setupTutorialUI()
    {
        // Semi-transparent black background
        Rectangle black = new Rectangle(700.0, 550.0, Color.BLACK);
        black.opacityProperty().setValue(0.1);

        // White background for the tutorial text
        Rectangle bg = new Rectangle(300.0, 250.0, Color.WHITE);
        bg.setLayoutX(200);
        bg.setLayoutY(75);
        bg.setStrokeType(StrokeType.OUTSIDE);
        bg.setStroke(Color.BLACK);

        // Title text for the tutorial
        Text title = new Text("Tutorial");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 18));
        title.setTextOrigin(VPos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLayoutX(350 - (title.getLayoutBounds().getWidth()) / 2);
        title.setLayoutY(90);

        // Setting font and position for the tutorial instructions
        tutorialText.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 14));
        tutorialText.setLayoutX(205);
        tutorialText.setLayoutY(125);

        // Close button to exit the tutorial
        closeTutorialButton.setText("X");
        closeTutorialButton.setLayoutX(477);
        closeTutorialButton.setLayoutY(75);
        closeTutorialButton.setOnAction(this);

        // Adding all elements to the tutorial pane
        tutorial.getChildren().add(black);
        tutorial.getChildren().add(bg);
        tutorial.getChildren().add(title);
        tutorial.getChildren().add(closeTutorialButton);
        tutorial.getChildren().add(tutorialText);

        // Initially setting the tutorial off-screen
        tutorial.setLayoutX(-1000);
    }

    // Returns the tutorial pane
    public Pane getTutorial()
    {
        return tutorial;
    }

    // Closes the tutorial by moving it off-screen
    private void closeTutorial()
    {
        tutorial.setLayoutX(-1000);
    }

    @Override
    public void handle(ActionEvent event)
    {
        // Play click sound for all button actions
        App.playClickSound();

        // Handle close button action
        if (event.getSource() == closeTutorialButton) 
        {
            closeTutorial();
        }
    }
}
