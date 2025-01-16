package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages the game state, including loading, saving, and creating new games.
 * Utilises JSON serialization/deserialization to persist game data.
 */
public class GameManager {
    private static SaveFile saveFile;

    /**
     * Constructs a new {@code GameManager} instance.
     */
    public GameManager() {}

    /**
     * Loads a game from the specified save file path.
     *
     * @param saveFilePath the path to the save file
     */
    public void loadGame(String saveFilePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(saveFilePath)) {
            // Deserialize JSON into SaveFile object
            saveFile = gson.fromJson(reader, SaveFile.class);

            // Debugging logs to confirm the deserialized data
            if (saveFile != null) {
                System.out.println("Game loaded successfully!");
                if (saveFile.getPet() != null) {
                    Pet pet = saveFile.getPet();
                    System.out.println("Pet Name: " + pet.getName());
                    System.out.println("Pet Type: " + pet.getType());
                    System.out.println("Pet Happiness: " + pet.getHappiness());
                    System.out.println("Pet Sleep: " + pet.getSleep());
                    System.out.println("Pet Hunger: " + pet.getHunger());
                } else {
                    System.err.println("Pet object is null in the deserialized SaveFile!");
                }
                System.out.println("Time Played: " + saveFile.getTimePlayed());
                System.out.println("Money: " + saveFile.getMoney());
                System.out.println("Inventory: " + saveFile.getInventory());
            } else {
                System.err.println("Failed to deserialize SaveFile!");
            }
        } catch (IOException e) {
            System.err.println("Error reading save file: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing save file: " + e.getMessage());
        }
    }

    /**
     * Saves the current game state to the specified save file path.
     *
     * @param saveFilePath the path to the save file
     */
    public void saveGame(String saveFilePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Create a Gson instance with pretty printing

        try (FileWriter writer = new FileWriter(saveFilePath)) {
            // Serialize the SaveFile object to JSON and write to file
            gson.toJson(saveFile, writer);
            System.out.println("Game saved successfully to " + saveFilePath);
        } catch (IOException e) {
            System.err.println("Error writing save file: " + e.getMessage());
        }
    }

    /**
     * Creates a new game with the specified parameters.
     *
     * @param num  the save file number
     * @param name the name of the pet
     * @param type the type of the pet
     */
    public void newGame(int num, String name, String type) {
        // Initialize the default data for a new game
        SaveFile newSaveFile = new SaveFile();
        Pet newPet = new Pet(name, type, 100, 100, 100, 100, false, false, false, false, false, false);

        newSaveFile.setPet(newPet);
        newSaveFile.setTimePlayed(0);
        newSaveFile.setMoney(100);
        newSaveFile.setInventory(new ArrayList<>());
        newSaveFile.setTransactionLog(new ArrayList<>());

        // Set the save file object to the new data
        saveFile = newSaveFile;

        // Create the file name
        String path = "src/main/java/com/example/data/sf";
        String fileName = path + num + ".json";
        newSaveFile.setSaveFilePath(fileName);

        // Save the new game data to the JSON file
        saveGame(fileName);
    }

    /**
     * Checks if a save file exists for the given save file number.
     * If the file exists, attempts to load the pet's name from it.
     *
     * @param num the save file number
     * @return the pet's name if found, "New Game" if the file does not exist, or an error message if loading fails
     */
    public static String saveFileExists(int num) {
        // Create the file path
        String filePath = "src/main/java/com/example/data/sf" + num + ".json";
        File file = new File(filePath);

        // Check if the file exists
        if (file.exists()) {
            // If the file exists, try to load the pet name from it
            try (FileReader reader = new FileReader(filePath)) {
                Gson gson = new Gson();
                SaveFile saveFile = gson.fromJson(reader, SaveFile.class);

                if (saveFile != null && saveFile.getPet() != null) {
                    return saveFile.getPet().getName(); // Return the Pet's name
                } else {
                    return "No Pet found in the save file.";
                }
            } catch (IOException | JsonSyntaxException e) {
                return "Error reading or parsing save file: " + e.getMessage();
            }
        } else {
            return "New Game"; // File does not exist
        }
    }

    /**
     * Retrieves the current save file.
     *
     * @return the current save file
     */
    public static SaveFile getSaveFile() {
        return saveFile;
    }

    /**
     * Sets the current save file.
     *
     * @param newSaveFile the new save file to set
     */
    public void setSaveFile(SaveFile newSaveFile) {
        saveFile = newSaveFile;
    }
}
