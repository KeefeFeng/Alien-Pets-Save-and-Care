package com.example;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * The SaveFile class represents the structure of a game save file.
 * It contains information about the pet, gameplay stats, inventory, transaction logs, and save file metadata.
 */
public class SaveFile {

    /**
     * The pet associated with this save file.
     * Mapped to the JSON key "Pet".
     */
    @SerializedName("Pet")
    private Pet pet;

    /**
     * The total time played, in minutes or another unit, depending on implementation.
     */
    private int timePlayed;

    /**
     * The total amount of money the player has in the game.
     */
    private int money;

    /**
     * The player's inventory, stored as a list of item names.
     */
    private List<String> inventory;

    /**
     * The log of transactions made in the game.
     */
    private List<String> transactionLog;

    /**
     * The file name for the save file.
     */
    private String saveFileName;

    /**
     * The save file number, useful for identifying or managing multiple save files.
     */
    private int saveFileNum;

    // Getters and setters

    /**
     * Gets the pet associated with this save file.
     *
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Sets the pet associated with this save file.
     *
     * @param pet the pet to set
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Gets the total time played.
     *
     * @return the total time played
     */
    public int getTimePlayed() {
        return timePlayed;
    }

    /**
     * Sets the total time played.
     *
     * @param timePlayed the total time played to set
     */
    public void setTimePlayed(int timePlayed) {
        this.timePlayed = timePlayed;
    }

    /**
     * Gets the total money the player has.
     *
     * @return the total money
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * Sets the total money the player has.
     *
     * @param money the total money to set
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Gets the player's inventory.
     *
     * @return the inventory as a list of item names
     */
    public List<String> getInventory() {
        return inventory;
    }

    /**
     * Sets the player's inventory.
     *
     * @param inventory the inventory to set
     */
    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets the transaction log for the game.
     *
     * @return the transaction log as a list of strings
     */
    public List<String> getTransactionLog() {
        return transactionLog;
    }

    /**
     * Sets the transaction log for the game.
     *
     * @param transactionLog the transaction log to set
     */
    public void setTransactionLog(List<String> transactionLog) {
        this.transactionLog = transactionLog;
    }

    /**
     * Gets the file path or name of the save file.
     *
     * @return the save file name or path
     */
    public String getSaveFilePath() {
        return this.saveFileName;
    }

    /**
     * Sets the file path or name of the save file.
     *
     * @param saveFilePath the save file name or path to set
     */
    public void setSaveFilePath(String saveFilePath) {
        this.saveFileName = saveFilePath;
    }

    /**
     * Gets the save file number.
     *
     * @return the save file number
     */
    public int getSaveFileNum() {
        return this.saveFileNum;
    }

    /**
     * Sets the save file number.
     *
     * @param saveFileNum the save file number to set
     */
    public void setSaveFileNum(int saveFileNum) {
        this.saveFileNum = saveFileNum;
    }
}

