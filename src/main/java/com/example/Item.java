package com.example;

/**
 * Represents an item with various attributes such as type, price, and modifiers for happiness, hunger, and sleep.
 */
public class Item {
    private int id; // Unique identifier for the item
    private String name; // Name of the item
    private String description; // Description of the item
    private String type; // Type of the item (e.g., food, toy, bedding)
    private int price; // Price of the item
    private int happinessMod; // Happiness modifier provided by the item
    private int hungerMod; // Hunger modifier provided by the item
    private int sleepMod; // Sleep modifier provided by the item

    /**
     * Constructs an {@code Item} with the specified attributes.
     *
     * @param id            the unique identifier of the item
     * @param name          the name of the item
     * @param description   a description of the item
     * @param type          the type of the item (e.g., food, toy, bedding)
     * @param price         the price of the item
     * @param happinessMod  the happiness modifier of the item
     * @param hungerMod     the hunger modifier of the item
     * @param sleepMod      the sleep modifier of the item
     */
    public Item(int id, String name, String description, String type, int price, int happinessMod, int hungerMod, int sleepMod) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.happinessMod = happinessMod;
        this.hungerMod = hungerMod;
        this.sleepMod = sleepMod;
    }

    /**
     * Retrieves the unique identifier of the item.
     *
     * @return the item ID
     */
    public int getId() { return id; }

    /**
     * Retrieves the name of the item.
     *
     * @return the item name
     */
    public String getName() { return name; }

    /**
     * Retrieves the description of the item.
     *
     * @return the item description
     */
    public String getDescription() { return description; }

    /**
     * Retrieves the type of the item.
     *
     * @return the item type
     */
    public String getType() { return type; }

    /**
     * Retrieves the price of the item.
     *
     * @return the item price
     */
    public int getPrice() { return price; }

    /**
     * Retrieves the happiness modifier of the item.
     *
     * @return the happiness modifier
     */
    public int getHappinessMod() { return happinessMod; }

    /**
     * Retrieves the hunger modifier of the item.
     *
     * @return the hunger modifier
     */
    public int getHungerMod() { return hungerMod; }

    /**
     * Retrieves the sleep modifier of the item.
     *
     * @return the sleep modifier
     */
    public int getSleepMod() { return sleepMod; }

    /**
     * Returns a string representation of the {@code Item}.
     *
     * @return a string containing the item's attributes
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", happinessMod=" + happinessMod +
                ", hungerMod=" + hungerMod +
                ", sleepMod=" + sleepMod +
                '}';
    }
}
