# Alien Pal: Save and Care

### Overview
**Alien Pal: Save and Care** is an engaging desktop game where players care for an alien pet. You’ll be responsible for managing your pet’s happiness, hunger, sleep, and health while balancing your resources like in-game money. Feed, play, exercise, and put your alien to sleep to ensure its well-being. The game also features an inventory and shop system where you can buy items to support your pet.

This project is developed using Java, JavaFX for an interactive graphical user interface (GUI), and Gson for saving and loading game progress. Alien Pal: Save and Care offers a fun and interactive way to strategize resource management while enjoying delightful alien companionship.

---

### Features
1. **Alien Pet Management**
   - Monitor and manage your alien’s happiness, hunger, health, and sleep attributes.
   - Engage in various activities like feeding, exercising, and playing to keep your alien happy.

2. **Shop System**
   - Purchase items like food, toys, and bedding using in-game currency.
   - Manage your inventory for efficient resource utilization.

3. **Save and Load System**
   - Save your game progress to a JSON file.
   - Load saved games from one of the four available save slots.

4. **Interactive User Interface**
   - Built with JavaFX for smooth interactions and animations.
   - Hover and click effects for enhanced gameplay experience.

5. **Real-Time Gameplay Mechanics**
   - Attributes like hunger and happiness change dynamically over time, requiring continuous management.

---

### How to Play

#### Starting the Game
1. Launch the application and select one of the four save slots.
2. Choose "New Game" to start fresh or load an existing save file to continue your progress.

#### Caring for Your Alien
- **Happiness**: Play with your alien to boost its happiness level.
- **Hunger**: Feed your alien with purchased or earned food items to keep it nourished.
- **Sleep**: Ensure your alien gets adequate rest by putting it to sleep.
- **Health**: Balance all aspects of care to maintain your alien's overall health and well-being.

#### Shopping
- Use the in-game shop to purchase items like:
  - Food
  - Toys
  - Beds
- Manage your in-game currency effectively to provide for your alien.

#### Saving and Loading
- **Save Progress**: Save your game to any available slot to preserve your progress.
- **Load Progress**: Resume from any saved game slot during subsequent sessions.

#### Parental Controls
The parental controls do not use any accounts or pins to use the software, however it does require the user to use a password.
- **Create Password**: When first booting up the game, you are required to create your own password to access the parental controls.
- **Enter Password**: Upon creating new password, going forward this password will be used to access parental controls.
- **Revive Pet**: Allows the user to bring their pet back to life, upon the pet's death. This setting is password protected.
- **Time Limit**: Allows the parent to set a time limit their child is allowed to play for. This setting is password protected.

---

 ### Technologies Used
- **Programming Language**: Java 23
- **Frameworks**:  
  - JavaFX (GUI Development)  
  - Gson (JSON Serialization/Deserialization)
- **Build Tool**: Apache Maven
- **Testing Framework**: JUnit 5
- **IDE**: Compatible with IntelliJ IDEA, VS Code, or Eclipse

---

### Prerequisites
1. **Java Development Kit (JDK)**: Version 17 or later.  
2. **Apache Maven**: Installed and configured.  
3. **JavaFX SDK**: Version 23.0.1 (already included in the project setup).  

---

### Installation & Setup
1. **Clone the Repository**
  Clone the project from the repository to your local machine:
  ```bash
  git clone https://gitlab.sci.uwo.ca/courses/2024/09/COMPSCI2212/group21.git
  cd group21
  ```

2. **Install Dependencies**
  Ensure Maven dependencies are downloaded:
  ```bash
  mvn clean install
  ```

3. **Run the Game**
  Use the following Maven command to run the application:
  ```bash
  mvn clean javafx:run
  ```

---

### Running Using .jar File
1. **Going into .jar Directory**
  Make sure your're in the same directory as the .jar file
  Example:
   ```bash
   cd group21
   ```

2. **Run The Following Command**
   ```bash 
   java --module-path "C:\Program Files\javafx-sdk-23.0.1\lib" --add-modules javafx.controls,javafx.fxml -jar demo-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

---

### Troubleshooting

#### Common Issues
1. **Maven Build Errors**:
   - Verify Maven is installed and accessible:
     ```bash
     mvn -v
     ```
   - If Maven is missing, install it by following the [official guide](https://maven.apache.org/install.html).

2. **Save File Issues**:
   - Confirm the `data` directory is present in your project folder.
   - Check for valid JSON syntax in existing save files if loading errors occur.

---

### Future Improvements
1. Introduce additional alien species with unique traits and behaviors.
2. Implement interactive achievements and milestones.
3. Expand the in-game shop with more items and customization options.
4. Add daily challenges and special events for enhanced gameplay.

---

### Credits
- **Developers**: Group 21 (Piercen Di Berardo, Keefe Feng, Mike Tran, Devan Sodhi, James Song)
- **Graphics and Assets**: A mix of custom and royalty-free resources.

---