package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Scene shop;
    private static Scene inventory;
    private static Scene newGame;

    private static Media mainMenuMusic;
    private static Media shopMusic;
    private static Media gameplayMusic;
    
    private static Media clickSound;
    private static Media eatSound;
    private static Media playSound;
    private static Media sleepSound;
    private static Media finishedSound;

    private static MediaPlayer mediaPlayer;

    private static MediaPlayer clickSoundPlayer;
    private static MediaPlayer eatSoundPlayer;
    private static MediaPlayer playSoundPlayer;
    private static MediaPlayer sleepSoundPlayer;
    private static MediaPlayer finishedSoundPlayer;

    private static Stage gameStage;
    private static GameplayScene gameplayScene;
    private static GameManager gameManager;
    private static SaveFile saveFile;
    private static String currentFilePath;

    private static double volume;
    private static double SFXVolume;
    private static Settings settings;

    private static NewGameController newGameController;

    @Override
    public void start(Stage stage) throws IOException {
        settings = new Settings();

        scene = new Scene(loadFXML("mainmenu"));
        gameStage = stage;
        gameStage.setTitle("Alien Pet: Save and Care");
        gameStage.resizableProperty().setValue(Boolean.FALSE);
        // Save upon closing window
        gameStage.setOnHiding( event -> 
        {
            if (gameplayScene != null && gameManager != null)
            {
                loadMenu();
            }
        } );
        gameStage.setScene(scene);
        gameStage.show();

        // Load the shop, inventory, and new game FXML and scenes
        shop = new Scene(loadFXML("shop"));
        inventory = new Scene(loadFXML("inventory"));

        FXMLLoader newGameLoader = new FXMLLoader(App.class.getResource("newgame.fxml"));
        Parent newGameRoot = newGameLoader.load();
        newGame = new Scene(newGameRoot);
        newGameController = newGameLoader.getController();

        gameplayScene = new GameplayScene(gameStage);

        try {
            // Add background music
            mainMenuMusic = new Media(getClass().getResource("/com/example/music/main_menu_music.mp3").toExternalForm());
            shopMusic = new Media(getClass().getResource("/com/example/music/shop_music.mp3").toExternalForm());
            gameplayMusic = new Media(getClass().getResource("/com/example/music/gameplay_music.mp3").toExternalForm());

            // Add SFX
            clickSound = new Media(getClass().getResource("/com/example/music/click.mp3").toExternalForm());
            eatSound = new Media(getClass().getResource("/com/example/music/eating.mp3").toExternalForm());
            playSound = new Media(getClass().getResource("/com/example/music/woohoo.mp3").toExternalForm());
            sleepSound = new Media(getClass().getResource("/com/example/music/sleeping.mp3").toExternalForm());
            finishedSound = new Media(getClass().getResource("/com/example/music/finished_action.mp3").toExternalForm());

            clickSoundPlayer = new MediaPlayer(clickSound);
            eatSoundPlayer = new MediaPlayer(eatSound);
            playSoundPlayer = new MediaPlayer(playSound);
            sleepSoundPlayer = new MediaPlayer(sleepSound);
            finishedSoundPlayer = new MediaPlayer(finishedSound);

            volume = 50;
            SFXVolume = 50;

            mediaPlayer = new MediaPlayer(mainMenuMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();

            setVolume(volume);
            setSFXVolume(SFXVolume);
        } catch (MediaException e) {
            e.printStackTrace();
        }

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void loadMenu()
    {
        try {
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(mainMenuMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }

        gameplayScene.stopGameLoop();
        gameManager.saveGame(currentFilePath);
        gameStage.setScene(scene);
    }

    public static void loadNewGame(int saveFileNum) {
        newGameController.setSaveFileNum(saveFileNum);
        gameStage.setScene(newGame);
    }    

    public static void loadGameplay(SaveFile newSaveFile, int saveFileNum) 
    {
        try {
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(gameplayMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
            
        } catch (MediaException e) {
            e.printStackTrace();
        }

        gameManager = new GameManager();
        currentFilePath = "src\\main\\java\\com\\example\\data\\sf" + saveFileNum + ".json";
        gameManager.loadGame(currentFilePath);
        //gameManager.getSaveFile().getPet().setGameLoop(this);
        // Get the SaveFile object from GameManager
        newSaveFile = gameManager.getSaveFile();

        GameLoop looping = new GameLoop(newSaveFile);  

        // Set the save file to the GameplayScene and switch to it
        saveFile = newSaveFile;
        gameplayScene.setSaveFile(newSaveFile);
        gameplayScene.setGameLoop(looping);
        gameStage.setScene(GameplayScene.getGameplay());
    }

    public static void loadGameplayScreen() {
        try {
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(gameplayMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
            
        } catch (MediaException e) {
            e.printStackTrace();
        }
        gameStage.setScene(GameplayScene.getGameplay());
        
    }

    public static void loadShop()
    {
        try {
            mediaPlayer.dispose();
            mediaPlayer = new MediaPlayer(shopMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }

        gameStage.setScene(shop);
    }

    public static void loadInventory()
    {
        try {
            // Load the inventory FXML
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/inventory.fxml"));
            Parent inventoryRoot = loader.load();

            // Create a new stage for the inventory
            Stage inventoryStage = new Stage();
            inventoryStage.initModality(Modality.APPLICATION_MODAL);
            inventoryStage.setTitle("Inventory");
            inventoryStage.setScene(new Scene(inventoryRoot));

            // Apply a blur effect to the current stage
            Scene currentScene = gameStage.getScene();
            if (currentScene != null) {
                currentScene.getRoot().setEffect(new GaussianBlur(10));
            }

            // Remove the blur effect when the inventory window is closed
            inventoryStage.setOnHidden(event -> {
                if (currentScene != null) {
                    currentScene.getRoot().setEffect(null);
                }
            });

            // Show the inventory stage
            inventoryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reviveCurrentPet()
    {
        gameplayScene.revivePet();
    }

    public static Stage getGameStage()
    {
        return gameStage;
    }

    public static GameplayScene getGameplayScene()
    {
        return gameplayScene;
    }

    public static double getVol()
    {
        return mediaPlayer.getVolume();
    }

    public static double getSFXVol()
    {
        return clickSoundPlayer.getVolume();
    }

    // Invoked after volume has changed or loading in game for the first time
    public static void setVolume(double vol)
    {
        settings.setBGM((int)vol);
        volume = vol / 100;
        mediaPlayer.setVolume(volume);
    }

    // Invoked after volume has changed or loading in game for the first time
    public static void setSFXVolume(double vol)
    {
        settings.setSFX((int)vol);
        SFXVolume = vol / 100;

        clickSoundPlayer.setVolume(SFXVolume);
        eatSoundPlayer.setVolume(SFXVolume);
        playSoundPlayer.setVolume(SFXVolume);
        sleepSoundPlayer.setVolume(SFXVolume);
        finishedSoundPlayer.setVolume(SFXVolume);
    }

    // get settings
    public static Settings getSettings() {
        return settings;
    }

    // SFX Sounds
    public static void playClickSound()
    {
        try {
            clickSoundPlayer.dispose();
            clickSoundPlayer = new MediaPlayer(clickSound);
            clickSoundPlayer.setVolume(SFXVolume);
            clickSoundPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
        clickSoundPlayer.play();
    }

    public static void playEatSound()
    {
        try {
            eatSoundPlayer.dispose();
            eatSoundPlayer = new MediaPlayer(eatSound);
            eatSoundPlayer.setVolume(SFXVolume);
            eatSoundPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
        eatSoundPlayer.play();
    }

    public static void playPlayingSound()
    {
        try {
            playSoundPlayer.dispose();
            playSoundPlayer = new MediaPlayer(playSound);
            playSoundPlayer.setVolume(SFXVolume);
            playSoundPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
        playSoundPlayer.play();
    }

    public static void playSleepSound()
    {
        try {
            sleepSoundPlayer.dispose();
            sleepSoundPlayer = new MediaPlayer(sleepSound);
            sleepSoundPlayer.setVolume(SFXVolume);
            sleepSoundPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
        sleepSoundPlayer.play();
    }
    
    public static void playFinishedActionSound()
    {
        try {
            finishedSoundPlayer.dispose();
            finishedSoundPlayer = new MediaPlayer(finishedSound);
            finishedSoundPlayer.setVolume(SFXVolume);
            finishedSoundPlayer.play();
        } catch (MediaException e) {
            e.printStackTrace();
        }
        finishedSoundPlayer.play();
    }

    public static SaveFile getSaveFile()
    {
        return saveFile;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
