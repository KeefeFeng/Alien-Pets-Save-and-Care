package com.example;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The Settings class represents the settings configuration for the game.
 * It includes options for background music (BGM) and sound effects (SFX) volumes,
 * parental controls, time limits, and other settings. It also provides functionality
 * to load settings or items from a JSON file.
 */
public class Settings {

    /**
     * Background music (BGM) volume.
     */
    private transient int BGM;

    /**
     * Sound effects (SFX) volume.
     */
    private transient int SFX;

    /**
     * Indicates whether parental controls are enabled.
     */
    private transient boolean parentControl;

    /**
     * Indicates whether the pet revival feature is enabled.
     */
    private transient boolean revivePet;

    /**
     * The time limit for gameplay, represented as a string in the format "hh:mm".
     */
    private String timeLimit;

    /**
     * The password used for parental controls.
     */
    private String parentPassword;

    /**
     * Default constructor for Gson (required for deserialization).
     */
    public Settings() {}

    /**
     * Constructs a {@code Settings} object with the specified values.
     *
     * @param BGM           the background music volume
     * @param SFX           the sound effects volume
     * @param parentControl whether parental controls are enabled
     * @param revivePet     whether the pet revival feature is enabled
     * @param timeLimit     the time limit for gameplay
     */
    public Settings(int BGM, int SFX, boolean parentControl, boolean revivePet, String timeLimit) {
        this.BGM = BGM;
        this.SFX = SFX;
        this.parentControl = parentControl;
        this.revivePet = revivePet;
        this.timeLimit = timeLimit;
    }

    /**
     * Loads a list of {@code Item} objects from a JSON file named "settingsSaves.json".
     *
     * @return a list of {@code Item} objects, or {@code null} if an error occurs
     */
    public static List<Item> loadItemsFromJson() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("settingsSaves.json")) {
            Type itemListType = new TypeToken<List<Item>>() {}.getType();
            return gson.fromJson(reader, itemListType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the background music (BGM) volume.
     *
     * @return the BGM volume
     */
    public int getBGM() {
        return BGM;
    }

    /**
     * Sets the background music (BGM) volume.
     *
     * @param volume the BGM volume to set
     */
    public void setBGM(int volume) {
        BGM = volume;
    }

    /**
     * Gets the sound effects (SFX) volume.
     *
     * @return the SFX volume
     */
    public int getSFX() {
        return SFX;
    }

    /**
     * Sets the sound effects (SFX) volume.
     *
     * @param volume the SFX volume to set
     */
    public void setSFX(int volume) {
        SFX = volume;
    }

    /**
     * Gets whether parental controls are enabled.
     *
     * @return {@code true} if parental controls are enabled, {@code false} otherwise
     */
    public boolean getParentControl() {
        return parentControl;
    }

    /**
     * Enables parental controls.
     */
    public void setParentalControl() {
        parentControl = true;
    }

    /**
     * Gets the parental control password.
     *
     * @return the parental control password
     */
    public String getParentPassword() {
        return this.parentPassword;
    }

    /**
     * Sets the parental control password.
     *
     * @param password the password to set
     */
    public void setParentPassword(String password) {
        this.parentPassword = password;
    }

    /**
     * Checks if the entered parental control password matches the stored password.
     *
     * @param enteredPassword the password entered by the user
     * @return {@code true} if the password matches, {@code false} otherwise
     */
    public boolean checkParentPassword(String enteredPassword) {
        return parentPassword != null && parentPassword.equals(enteredPassword);
    }

    /**
     * Gets the gameplay time limit.
     *
     * @return the time limit in the format "hh:mm"
     */
    public String getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the gameplay time limit. Validates the input format ("hh:mm") and checks
     * that the hours are between 0-23 and the minutes are between 0-59.
     *
     * @param time the time limit to set, in the format "hh:mm"
     * @throws IllegalArgumentException if the time format is invalid or out of range
     */
    public void setTimeLimit(String time) {
        if (time == null || !time.matches("\\d{1,2}:\\d{2}")) {
            throw new IllegalArgumentException("Invalid time format. Use hh:mm.");
        }

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Hours must be 0-23 and minutes must be 0-59.");
        }

        System.out.println("Time limit set to " + hours + " hours and " + minutes + " minutes.");
        this.timeLimit = time;
    }

    /**
     * Gets whether the pet revival feature is enabled.
     *
     * @return {@code true} if the pet revival feature is enabled, {@code false} otherwise
     */
    public boolean isRevivePet() {
        return revivePet;
    }

    /**
     * Revives a pet by creating a new {@code Pet} instance.
     */
    public void revivePet() {
        new Pet();
    }
}
