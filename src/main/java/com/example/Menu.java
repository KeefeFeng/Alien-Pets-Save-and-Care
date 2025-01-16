package com.example;

/**
 * Represents a menu for navigating various actions in the application,
 * such as starting the game, changing settings, or exiting the program.
 */
public class Menu {

    /**
     * Constructs a new {@code Menu} instance.
     */
    public Menu() {
        // Default constructor
    }

    /**
     * Starts the game by creating a new {@code GameManager} instance.
     */
    public void playGame() {
        new GameManager();
    }

    /**
     * Opens the settings menu by creating a new {@code Settings} instance.
     */
    public void changeSetting() {
        new Settings();
    }

    /**
     * Exits the application with a farewell message.
     */
    public static void exit() {
        System.out.println("Goodbye world...");
        System.exit(0);
    }
}
