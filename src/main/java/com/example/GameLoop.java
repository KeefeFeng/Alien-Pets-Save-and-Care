package com.example;

public class GameLoop {
    private transient boolean running = false;
    private GamePlay play;
    private transient final int TARGET_TIME_BETWEEN_UPDATES = 1000; // Milliseconds
    private GameManager gameManager;  // Added by JAMES
    
    

    public GameLoop(SaveFile saveFile) {
        start(saveFile);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public GamePlay getPlay() {
        return play;
    }

    public void setPlay(GamePlay play) {
        this.play = play;
    }

    public void start(SaveFile saveFile) {
        running = true;
        // Ensure the save file loaded correctly
        if (saveFile != null) {
            //saveFile.getPet().setGameLoop(this);
            play = new GamePlay(saveFile); // Pass the SaveFile to GamePlay
        } else {
            System.err.println("Failed to load save data. Exiting game loop.");
            running = false;
            return;
        }

        // Start the game loop in a new thread
        Thread gameThread = new Thread(this::run); // Lambda for the run method
        gameThread.start();

        System.out.println("Game loop started on a separate thread.");

    }

    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0.0;
        final double nsPerUpdate = 1_000_000_000.0 / (1000 / TARGET_TIME_BETWEEN_UPDATES);

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            while (delta >= 1) {
                update(); // Update game logic
                delta--;
            }

        }
    }

    public void update() {
        if (play != null) {
            play.update();
        }
    }

    

    // Added by JAMES
    public GameManager getGameManager()
    {
        return this.gameManager;
    }
}
