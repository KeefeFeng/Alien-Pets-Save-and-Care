package com.example;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ParentalControlsUI implements EventHandler<ActionEvent> 
{
    private final static Pane parentalControls = new Pane();
    private final static Pane loginScreen = new Pane();
    private final static Pane passwordReset = new Pane();

    Button closePControlsButton = new Button();
    Button closePControlsButton2 = new Button(); // For login screen, same as above button
    Button closePControlsButton3 = new Button(); // For password reset screen
    Button setPassword = new Button();
    String timeLimitDefaultText = "Time Limit ";
    Button toggleTimeLimit = new Button();
    // In gameplay, will check if current pet is alive. If not, then revive current pet.

    // If in menu, then open save file menu like normal and when player chooses a save
    // file, it checks if that pet is dead. If it is, an extra parameter is sent to the 
    // start function of gameplay that will prompt the game to revive the pet upon entry.
    // Gameplay will start as normal on that save file afterwards.
    Button revivePet = new Button();
    Button loginButton = new Button();
    Button setPassButton = new Button();

    TextField timeLimitInput = new TextField(); // Max of 3 numbers, in minutes
    TextField passwordInput = new TextField();
    TextField oldPassInput = new TextField();
    TextField newPassInput = new TextField();

    Text oldPassTitle = new Text("Old Password:");

    String password = ""; // Change to actual password, default will be blank
    boolean timeLimitOn = false; // Change to what it is set in settings data
    int maxPassLen = 20; // Max length the password can be

    public ParentalControlsUI()
    {
        password = App.getSettings().getParentPassword();

        setupParentalControlsUI();
    }

    private void setupParentalControlsUI()
    {
        Rectangle black = new Rectangle(700.0, 550.0, Color.BLACK);
        black.opacityProperty().setValue(0.5);

        Rectangle bg = new Rectangle(250, 150, Color.WHITE);
        bg.setLayoutX(225);
        bg.setLayoutY(75);
        bg.setStrokeType(StrokeType.OUTSIDE);
        bg.setStroke(Color.BLACK);

        Rectangle loginBg = new Rectangle(250, 120, Color.WHITE);
        loginBg.setLayoutX(225);
        loginBg.setLayoutY(100);

        Rectangle pResetBg = new Rectangle(250, 120, Color.WHITE);
        pResetBg.setLayoutX(225);
        pResetBg.setLayoutY(100); 

        Text title = new Text("Parental Controls");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 18));
        title.setTextOrigin(VPos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setLayoutX(350 - (title.getLayoutBounds().getWidth())/2);
        title.setLayoutY(90);

        Text loginTitle = new Text("Password:");
        loginTitle.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        loginTitle.setTextOrigin(VPos.CENTER);
        loginTitle.setTextAlignment(TextAlignment.CENTER);
        loginTitle.setLayoutX(350 - (loginTitle.getLayoutBounds().getWidth())*2);
        loginTitle.setLayoutY(150);

        oldPassTitle.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        oldPassTitle.setTextOrigin(VPos.CENTER);
        oldPassTitle.setTextAlignment(TextAlignment.CENTER);
        oldPassTitle.setLayoutX(235);
        oldPassTitle.setLayoutY(130);

        Text newPassTitle = new Text("New Password:");
        newPassTitle.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        newPassTitle.setTextOrigin(VPos.CENTER);
        newPassTitle.setTextAlignment(TextAlignment.CENTER);
        newPassTitle.setLayoutX(232);
        newPassTitle.setLayoutY(160);

        Text minsText = new Text ("mins");
        minsText.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        minsText.setLayoutX(425);
        minsText.setLayoutY(165);

        loginButton.setText("Login");
        loginButton.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        loginButton.setPrefSize(60, 25);
        loginButton.setLayoutX(320);
        loginButton.setLayoutY(180);
        loginButton.setOnAction(this);

        setPassButton.setText("Set Password");
        setPassButton.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        setPassButton.setPrefSize(120, 25);
        setPassButton.setLayoutX(290);
        setPassButton.setLayoutY(185);
        setPassButton.setOnAction(this);

        setupPasswordField(oldPassInput, 320, 120);
        setupPasswordField(newPassInput, 320, 150);
        setupPasswordField(passwordInput, 300, 137);
        timeLimitInput.setPrefSize(35, 25);
        timeLimitInput.setLayoutX(385);
        timeLimitInput.setLayoutY(150);
        timeLimitInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                int maxLength = 3;
                if (timeLimitInput.getText().length() > maxLength) {
                    String s = timeLimitInput.getText().substring(0, maxLength);
                    timeLimitInput.setText(s);
                }
                if (!newValue.matches("\\d*")) {
                    timeLimitInput.setText(newValue.replaceAll("[^\\d]", ""));
                }

                setTimeLimit();
            }
        });
        // *** Set timeLimitInput text to whatever time limit you set if it was set, and set timeLimitOn to true
        timeLimitOn = App.getSettings().getParentControl();
        String toggleStatusString = timeLimitDefaultText + "(OFF)"; // *** Change to (ON) if time limit exists in data
        if (timeLimitOn) 
        {
            toggleStatusString = timeLimitDefaultText + "(ON)";
        }

        setupButton(toggleTimeLimit, toggleStatusString, 150);
        toggleTimeLimit.setPrefWidth(120);   

        setupButton(setPassword, "Change Password", 120);
        setupButton(revivePet, "Revive Pet", 180);

        setupCloseButton(closePControlsButton);
        setupCloseButton(closePControlsButton2);
        setupCloseButton(closePControlsButton3);

        parentalControls.getChildren().add(black);
        parentalControls.getChildren().add(bg);
        parentalControls.getChildren().add(title);
        parentalControls.getChildren().add(closePControlsButton);
        parentalControls.getChildren().add(toggleTimeLimit);
        parentalControls.getChildren().add(revivePet);
        parentalControls.getChildren().add(timeLimitInput);
        parentalControls.getChildren().add(minsText);
        parentalControls.getChildren().add(setPassword);
        parentalControls.getChildren().add(loginScreen);
        parentalControls.getChildren().add(passwordReset);

        loginScreen.getChildren().add(loginBg);
        loginScreen.getChildren().add(loginTitle);
        loginScreen.getChildren().add(loginButton);
        loginScreen.getChildren().add(passwordInput);    
        loginScreen.getChildren().add(closePControlsButton2);

        passwordReset.getChildren().add(pResetBg);
        passwordReset.getChildren().add(oldPassTitle);
        passwordReset.getChildren().add(newPassTitle);
        passwordReset.getChildren().add(oldPassInput);
        passwordReset.getChildren().add(newPassInput);
        passwordReset.getChildren().add(setPassButton);
        passwordReset.getChildren().add(closePControlsButton3);

        parentalControls.setLayoutY(-1000);
        if (password == null || password.equals("")) {
            loginScreen.setLayoutX(-1000);
        }
        passwordReset.setLayoutX(-1000);
    }

    private void setupPasswordField(TextField field, int xPos, int yPos)
    {
        field.setPrefSize(150, 25);
        field.setLayoutX(xPos);
        field.setLayoutY(yPos);
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (field.getText().length() > maxPassLen) {
                    String s = field.getText().substring(0, maxPassLen);
                    field.setText(s);
                }
            }
        });
    }

    private void setupCloseButton(Button btn)
    {
        btn.setText("X");
        btn.setLayoutX(452);
        btn.setLayoutY(75);
        btn.setOnAction(this);
    }

    private void setupButton(Button btn, String txt, int yPos)
    {
        btn.setText(txt);
        btn.setFont(Font.font("Comic Sans MS", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        btn.setPrefSize(200, 25);
        btn.setLayoutX(250);
        btn.setLayoutY(yPos);
        btn.setOnAction(this);
    }

    public Pane getParentalControls()
    {
        return parentalControls;
    }

    public Pane getLoginScreen()
    {
        return loginScreen;
    }

    public Pane getPasswordReset()
    {
        return passwordReset;
    }

    public String getPassword()
    {
        return password;
    }

    public void closeParentalControls()
    {
        parentalControls.setLayoutY(-1000);
    }

    private void openPassResetMenu()
    {
        passwordReset.setLayoutX(0);
        if (password == null || password.equals("")) 
        {
            oldPassInput.setDisable(true);
            oldPassTitle.setDisable(true);
        }
        else 
        {
            oldPassInput.setDisable(false);
            oldPassTitle.setDisable(false);
        }
    }

    public void closePassResetMenu()
    {
        passwordReset.setLayoutX(-1000);
    }

    public void clearPassText()
    {
        passwordInput.setText("");
        oldPassInput.setText("");
        newPassInput.setText("");
    }

    private void setTimeLimit()
    {
        if (timeLimitOn && timeLimitInput.getLength() > 0) {
            int timeLimit = Integer.parseInt(timeLimitInput.getText());
            int hours = timeLimit / 60;
            int minutes = timeLimit % 60;
            String timeString = "" + hours + ":" + "" + minutes;
            // *** Set time limit in settings to timeLimit
            App.getSettings().setTimeLimit(timeString);
        }
    }

    @Override
    public void handle(ActionEvent event)
    {
        App.playClickSound();
        // Parental controls events
        if (event.getSource() == closePControlsButton || event.getSource() == closePControlsButton2 || event.getSource() == closePControlsButton3) 
        {
            closeParentalControls();
        }
        else if (event.getSource() == setPassword) 
        {
            openPassResetMenu();
        }
        else if (event.getSource() == revivePet) 
        {
            // *** Check  if pet is dead
            // *** If dead, then revive, else, do nothing
            App.reviveCurrentPet();
        }
        else if (event.getSource() == toggleTimeLimit)
        {
            if (timeLimitOn) 
            {
                timeLimitOn = false;
                toggleTimeLimit.setText(timeLimitDefaultText + "(OFF)");
            }
            else
            {
                timeLimitOn = true;
                toggleTimeLimit.setText(timeLimitDefaultText + "(ON)");

                setTimeLimit();
            }
        }

        // Login screen events
        if (event.getSource() == loginButton) 
        {
            String passwordText = passwordInput.getText();

            if (passwordText.equals(password)) 
            {
                loginScreen.setLayoutX(-1000);
            }
        }

        // Password reset screen events
        if (event.getSource() == setPassButton) {
            if (password == null || oldPassInput.getText().equals(password) || password.equals("")) 
            {
                password = newPassInput.getText();
                // *** Set password in data to password
                App.getSettings().setParentPassword(password);
                closeParentalControls();
            }
        }
    }
}

