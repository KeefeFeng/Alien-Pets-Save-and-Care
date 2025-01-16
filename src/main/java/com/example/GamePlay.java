package com.example;

import java.util.List;


public class GamePlay {
    private List<String> inventory; // Inventory for gameplay (converted from SaveFile)
    private Pet pet;              // Pet instance for gameplay
    public static int updateCount = 0;  // Tracks updates



    public GamePlay(SaveFile saveFile) {
        // Fetch the pet and inventory directly from SaveFile
        pet = saveFile.getPet();

        // Convert SaveFile's string inventory to Item objects if necessary
        inventory = saveFile.getInventory();
    }

    // Method to handle button clicks
    public void onButtonClick(int actionId) {
        switch (actionId) {
            case 1:
                feedPet();
                break;
            case 2:
                playWithPet();
                break;
            case 3:
                putPetToSleep();
                break;
            case 4:
                giveGiftToPet();
                break;
            default:
                System.out.println("Invalid action!");
        }
    }

    // Method to update the game state
    public void update() {
        updateCount += 1;
        System.out.println("Game state updated: "+updateCount);
        if (updateCount%3 == 0) pet.updateStats(); //update 1 per 1 mins  
    }

    // Inventory management
    public void addItemToInventory(Item item) {
        inventory.add(item.toString());
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item.toString());
    }

    // Methods triggered by buttons
    private void feedPet() {
        if (!inventory.isEmpty()) {
            //Item food = inventory.get(0); // Use the first item as food (placeholder)
            //pet.feed(food);
            //removeItemFromInventory(food);
            System.out.println("Fed the pet.");
        } else {
            System.out.println("No food available to feed the pet.");
        }
    }

    private void playWithPet() {
        if (!inventory.isEmpty()) {
            //Item toy = inventory.get(0); // Use the first item as a toy (placeholder)
            //pet.play(toy);
            System.out.println("Played with the pet.");
        } else {
            System.out.println("No toys available to play with the pet.");
        }
    }

    private void putPetToSleep() {
        if (!inventory.isEmpty()) {
            //Item bedding = inventory.get(0); // Use the first item as bedding (placeholder)
            //pet.sleep(bedding);
            System.out.println("Pet is now sleeping.");
        } else {
            System.out.println("No bedding available for the pet.");
        }
    }

    private void giveGiftToPet() {
        if (!inventory.isEmpty()) {
            //Item gift = inventory.get(0); // Giving the first item as a gift
            //pet.play(gift); // Assuming playing with a gift increases happiness
            //removeItemFromInventory(gift);
            System.out.println("Gave a gift to the pet.");
        } else {
            System.out.println("No items available to give as a gift.");
        }
    }

}
