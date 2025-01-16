package com.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Shop class manages the in-game shop functionality, including loading items from a JSON file,
 * organizing items into categories, and handling purchasing and selling operations.
 */
public class Shop {

    /**
     * A map that organizes items into categories based on their type.
     * The key represents the item type, and the value is a list of items of that type.
     */
    private Map<String, List<Item>> categorizedItems;

    /**
     * The current balance of the player.
     */
    private static int balance;

    /**
     * Reference to the save file that holds player data, including the balance.
     */
    private SaveFile savefile;

    /**
     * Constructs a {@code Shop} object and initializes items from a specified JSON file.
     *
     * @param filePath the file path of the JSON file containing item data
     */
    public Shop(String filePath) {
        loadItemsFromJson(filePath);
        setSaveFile();
    }

    /**
     * Loads items from a JSON file and stores them in the {@code categorizedItems} map.
     *
     * @param filePath the file path of the JSON file containing item data
     */
    private void loadItemsFromJson(String filePath) {
        System.out.println("Loading Items from Json");
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, List<Item>>>() {}.getType();
            categorizedItems = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            categorizedItems = null;
        }
    }

    /**
     * Retrieves a list of items of a specific type.
     *
     * @param type the type of items to retrieve
     * @return a list of items of the specified type, or an empty list if no items of that type are found
     */
    public List<Item> getList(String type) {
        if (categorizedItems == null || !categorizedItems.containsKey(type)) {
            return new ArrayList<>(); // Return empty list if type is not found
        }
        return categorizedItems.get(type);
    }

    /**
     * Processes the purchase of an item. Updates the player's balance in the save file.
     *
     * @param item the item to purchase
     */
    public void buy(Item item) {
        System.out.println("Buying item: " + item.getName());
        if (savefile == null) {
            setSaveFile();
        }
        savefile.setMoney(balance - item.getPrice());
        balance -= item.getPrice();
    }

    /**
     * Processes the sale of an item.
     *
     * @param item the item to sell
     */
    public void sell(Item item) {
        System.out.println("Selling item: " + item.getName());
        // Selling logic can be implemented here
    }

    /**
     * Retrieves the current balance of the player.
     *
     * @return the player's current balance
     */
    public int getBalance() {
        if (savefile == null) {
            setSaveFile();
        }
        return balance;
    }

    /**
     * Sets the save file reference and updates the current balance from the save file.
     */
    public void setSaveFile() {
        if (App.getSaveFile() != null) {
            savefile = App.getSaveFile();
            balance = savefile.getMoney();
        }
    }
}
