package com.example;

public class Pet { 
  private String name;
  private String type;
  private boolean isDepressed, isSleeping, isStarving, isSick, isObey, isDead;
  private int health, sleep, hunger, happiness;
  private GameLoop gameLoop;

  private transient final int HIGH = 100, MID = 50, LOW = 25; // Excluded from serialization

  private transient GameplayScene gameplayScene; // For updating stat bars
  private transient boolean doingAction;
  
  // Default constructor for Gson (required for deserialization)
  public Pet() {
    // Load in static variable for gameplay scene (not best method, but easiest)
    this.gameplayScene = App.getGameplayScene();
  }

  // Parameterized constructor for manual initialization (if needed)
  public Pet(String name, String type, int happiness, int health, int sleep, int hunger, boolean isSick, 
  boolean isDepressed, boolean isDead, boolean isObey, boolean isStarving, boolean isSleeping) {
    this.name = name;
    this.type = type;
    this.health = health;
    this.happiness = happiness;
    this.sleep = sleep;
    this.hunger = hunger;
    this.isSick = isSick;
    this.isDepressed = isDepressed;
    this.isDead = isDead;
    this.isObey = isObey;
    this.isSleeping = isSleeping;
    this.isStarving = isStarving; 

    // Load in static variable for gameplay scene (not best method, but easiest)
    this.gameplayScene = App.getGameplayScene();
  }

    // Getters and setters (used by Gson)

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getHappiness() {
    return happiness;
  }

  public void setHappiness(int happiness) {
    this.happiness = happiness;
  }

  public int getSleep() {
    return sleep;
  }

  public void setSleep(int sleep) {
    this.sleep = sleep;
  }

  public int getHunger() {
    return hunger;
  }

  public void setHunger(int hunger) {
      this.hunger = hunger;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public boolean getIsStarving() {
    return isStarving;
  }

  public void setIsStarving(boolean isStarving) {
    this.isStarving = isStarving;
  }

  public boolean getIsSleeping() {
    return isSleeping;
  }

  public void setIsSleeping(boolean isSleeping) {
    this.isSleeping = isSleeping;
  }

  public boolean getIsDepressed() {
    return isDepressed;
  }

  public void setIsDepressed(boolean isDepressed) {
    this.isDepressed = isDepressed;
  }

  public boolean getIsObey() {
    return isObey;
  }

  public void setIsObey(boolean isObey) {
    this.isObey = isObey;
  }

  public boolean getIsSick() {
    return isSick;
  }

  public void setIsSick(boolean isSick) {
    this.isSick = isSick;

    if (this.isSick) 
    {
        gameplayScene.showVetButton();
    }
  }

  public boolean getIsDead() {
    return isDead;
  }

  public void setIsDead(boolean isDead) {
    this.isDead = isDead;

    // Open game over screen
  }

  // pet getting fed method
  public void feed(Item item) {
    doingAction = true;
    if (item.getType().equals("food") && this.isObey) {
      if (this.happiness + item.getHappinessMod() >= HIGH) 
        {
          this.happiness = HIGH;
        }
        else 
        {
          this.happiness += item.getHappinessMod();
        }

        if (this.sleep + item.getSleepMod() >= HIGH) 
        {
          this.sleep = HIGH;
        }
        else 
        {
          this.sleep += item.getSleepMod();
        }

        if (this.hunger + item.getHungerMod() >= HIGH) 
        {
          this.hunger = HIGH;
        }
        else 
        {
          this.hunger += item.getHungerMod();
        }
      updateMood();
    }
    if (!this.isObey) warningMsg();
  }

  
  // pet play method
  public void play(Item item) {
    doingAction = true;
    if (item.getType().equals("toy")) {
      if (this.happiness + item.getHappinessMod() >= HIGH) 
      {
        this.happiness = HIGH;
      }
      else 
      {
        this.happiness += item.getHappinessMod();
      }

      if (this.sleep - item.getSleepMod() <= 0) 
      {
        this.sleep = 0;
      }
      else 
      {
        this.sleep -= item.getSleepMod();
      }

      if (this.hunger - item.getHungerMod() <= 0) 
      {
        this.hunger = 0;
      }
      else 
      {
        this.hunger -= item.getHungerMod();
      }
      updateMood();
    }
  }

  // pet sleep method
  public void sleep(Item item) {
    doingAction = true;
    if (item.getType().equals("bed") && this.isObey) {
      sleepMsg();
        if (this.happiness + item.getHappinessMod() >= HIGH) 
        {
          this.happiness = HIGH;
        }
        else 
        {
          this.happiness += item.getHappinessMod();
        }

        if (this.sleep + item.getSleepMod() >= HIGH) 
        {
          this.sleep = HIGH;
        }
        else 
        {
          System.out.println(item.getSleepMod());
          this.sleep += item.getSleepMod();
        }

        if (this.hunger + item.getHungerMod() >= HIGH) 
        {
          this.hunger = HIGH;
        }
        else 
        {
          this.hunger += item.getHungerMod();
        }
      this.isSick = false;  //sleep removes sick debuff
      updateMood();
    }
    if (!this.isObey) warningMsg();
  }

  public void goToVet() {
    doingAction = true;
    this.isSick = false;
    if (this.health + 30 >= HIGH) 
    {
      this.health = HIGH;
    }
    else
    {
      this.health += 30;
    }  
    this.hunger += 20;
    this.sleep += 20;
    updateMood();
  }

  public void revivePet() {
    if (getIsDead()) {
      health = HIGH;
      happiness = HIGH - LOW;
      sleep = HIGH - LOW;
      hunger = HIGH - LOW;
      isSick = false;
      isDepressed = false;
      isDead = false;
      isObey = true;
      isSleeping = false;
      isStarving = false; 

      updateMood();
    }
  }

  // This should be sufficient for now, need to know how this is gonna work in the
  // GUI
  public void exercise() {
    doingAction = true;
    if (this.isObey) {
      if (this.health >= 85) this.health = HIGH;
      else this.health += 15;
      if (this.sleep <= 10) this.sleep = 0;
      else this.sleep -= 10;
      if (this.hunger <= 10) this.hunger = 0;
      else this.hunger -= 10;
      
      updateMood();
    } else warningMsg();
  }

  public void stopDoingAction()
  {
    doingAction = false;
  }

  public boolean isDoingAction()
  {
    return doingAction;
  }

  // Updates stats over time
  // if isSick = true then sleep goes down x2
  public void updateStats() {
    if (doingAction || isDead) 
    {
      return;
    }  

    this.hunger -= 5;
    if (this.hunger < 0) 
    {
        this.hunger = 0;
    }

    if (isSick){
      this.sleep -= 10;
    } 
    else if (this.sleep >= 5) 
    {
      this.sleep -= 5;
    } 
    if (this.sleep < 0) 
    {
      this.sleep = 0;
    }

    this.happiness -= 5;
    if (this.happiness < 0) 
    {
      this.happiness = 0;
    }
    
    System.out.println("hunger: "+hunger);
    System.out.println("sleep: "+sleep);
    System.out.println("happiness: "+happiness);
    updateMood();
  }

    // Updates mood based on stats
  public void updateMood() {
      // Happiness update
    String mood = "";
    if (this.happiness <= LOW) {
      this.isDepressed = true;
      this.isObey = false;
      //depressingState();
      mood = "depressed";
    } else {
      if (this.happiness >= MID)
        this.isObey = true;
      this.isDepressed = false;
      mood = "normal";
    }

    // Fullness update
    if (this.hunger <= LOW) {
      this.isStarving = true;
      starvingState();
      mood = "hungry";
    } else this.isStarving = false;

    // Sleepiness update
    if (this.sleep == 0) {
      this.isSleeping = true;
      mood = "sleepy";
      //sleepingState();
    } else this.isSleeping = false;

    // Bro is sick update
    if (this.sleep < MID && this.hunger < MID) {
      this.isSick = true;
      mood = "sick";
      gameplayScene.showVetButton();
    }

    // Update to game ui
    gameplayScene.updateHealth(this.health);
    gameplayScene.updateHunger(this.hunger);
    gameplayScene.updateHappiness(this.happiness);
    gameplayScene.updateSleep(this.sleep);
    if (isDead) 
    {
      gameplayScene.changePetSprite("dead");
    }
    else
    {
      gameplayScene.changePetSprite(mood);
    }
  }

  // Pet leave without care when is depressed (Depression stat slowly goes down to
  // 0, if 0 then pet leaves) kinda method
  public void depressingState() {
    if (happiness == 0) gameplayScene.stopGameLoop();
  }

  // Dearest pet will slowly die in a painful way until you feed it
  // 5s delay
  public void starvingState() {
      this.health -= 5;
      if (this.health < 0) {
          this.health = 0;
      }
      if (this.health <= 0) {
        deadMsg();
      }
  }

  //2s delay cuz 1 would make it loop immediately
  public void sleepingState() {
    this.health -= LOW;
    if (this.health <= 0) {
      deadMsg();
    }
    sleepMsg();
    while (this.sleep < 20) {
      if (GamePlay.updateCount%2 == 0) this.sleep+= 2;
    }
    this.isSick = false;  //sleep removes sick debuff
  }

  public void sickState() {
    this.health -= 10;
    if (this.health < 0) 
    {
      this.health = 0;
    }
    this.hunger -= 10;
    if (this.hunger < 0) 
    {
      this.hunger = 0;
    }
    this.happiness -= 10;
    if (this.happiness < 0) 
    {
      this.happiness = 0;
    }
    warningMsg();
  }

  public void setGameLoop(GameLoop gameLoop) {
    this.gameLoop = gameLoop;
    if (this.isDead) 
    {
      gameplayScene.changePetSprite("dead");
      gameplayScene.stopGameLoop();
    }
  } 

  public void deadMsg() {
    this.isDead = true;
    System.out.println("Your pet is DEAD");
    gameplayScene.stopGameLoop();
  }

  public void sleepMsg() {
    System.out.println("Your pet is SLEEPING. DO NOT DISTURB!!!. Did you know: a good sleep, can make you feel better?");
  }

  public void warningMsg() {
    //Depressing state
    if (this.isDepressed) System.out.println("Warning msg: your pet is depressed, pls cure it by increase happiness before too late...");
    else if (!this.isDepressed && !this.isObey) System.out.println("Warning msg: your pet is still low on happiness after depression");

    //Sick state
    if (this.isSick) System.out.println("Oh no.. Your pet is sick af. Decrease in sleepiness will now x2. Good luck ig");


  }
}
