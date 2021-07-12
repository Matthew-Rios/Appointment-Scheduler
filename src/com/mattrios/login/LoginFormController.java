package com.mattrios.login;

import com.mattrios.Main;
import com.mattrios.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.util.Locale;

/**
 * @author Matt Rios
 */
public class LoginFormController {
    @FXML
    private TextField userIDField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink requestAccessHyperlink;
    @FXML
    private Label userLocationLabel;
    @FXML
    private Label userIDLabel;
    @FXML
    private Label loginFormLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label needAccessLabel;
    @FXML
    private Button loginButton;
    @FXML
    private VBox loginFormVBox;

    private String defaultLanguage;

    /**
     * Sets the user location label to the user's current ZoneId. Additionally this method checks the default language
     * of the user. If the user's preferred language is set to French it calls the translate to French method which
     * translates all text in this window to French.
     */
    public void initialize() {
        userLocationLabel.setText(ZoneId.systemDefault().toString());

        defaultLanguage = Locale.getDefault().getLanguage();
        if (defaultLanguage.equals("fr")) {
            translateToFrench();
        }
    }

    /**
     * When the login button is clicked this method will call the login successful method in PreparedStatements
     * and pass in the values in the userIDField and passwordField. That method queries the users table using the
     * passed information. If a match is found, the user is logged into the system and a singleton of the User
     * class is generated to be able to reference the user name and ID. If no match is found an error
     * message is provided in English or French depending on the user's language settings.
     *
     * This method also creates a log file called Login_Activity.txt in the root directory if it does not already exist.
     * It records the localized time/date of each login attempt and whether it was successful or not.
     */
    @FXML
    public void loginButtonClicked() {

        File loginActivity = new File ("Login_Activity.txt");
        LocalDateTime loginDateTime = LocalDateTime.now(ZoneOffset.UTC);
        LocalDate loginDate = loginDateTime.toLocalDate();
        LocalTime loginTime = loginDateTime.toLocalTime();

        try {
            loginActivity.createNewFile();
            FileWriter writer = new FileWriter(loginActivity, true);
            writer.write("- " + loginDate + ", " + loginTime + " UTC, ");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Database.loginSuccessful(userIDField.getText(), passwordField.getText())) {
            try {
                FileWriter writer = new FileWriter(loginActivity, true);
                writer.write("Success\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Database.generateUserSingleton(userIDField.getText());
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(loginFormVBox.getScene().getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("main/mainForm.fxml"));

            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e){
                if(defaultLanguage.equals("fr")) {
                    System.out.println("Impossible de charger la boîte de dialogue");
                } else {
                    e.printStackTrace();
                    System.out.println("Couldn't load the dialog");
                }
            }
            dialog.showAndWait();
        } else {
            try {
                FileWriter writer = new FileWriter(loginActivity, true);
                writer.write("Failed\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert;
            if (defaultLanguage.equals("fr")) {
                alert = new Alert(Alert.AlertType.ERROR, "Le nom d'utilisateur ou le mot de passe que vous " +
                        "avez tapé est incorrect.");
            } else {
                alert = new Alert(Alert.AlertType.ERROR, "The username or password you entered is incorrect.");
            }
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }

    /**
     * Generates and displays the add user form. If an exception occurs it will create an error message in English or
     * French depending on the user's preferred language
     */
    @FXML
    public void requestAccessHyperLinkClicked() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(loginFormVBox.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("users/addUserForm.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            if(defaultLanguage.equals("fr")) {
                System.out.println("Impossible de charger la boîte de dialogue");
            } else {
                System.out.println("Couldn't load the dialog");
            }
        }

        dialog.showAndWait();
    }

    /**
     * This is the method called by initialize which sets all the text in the window to French if needed.
     */
    private void translateToFrench() {
        userIDLabel.setText("Identifiant d'utilisateur");
        loginFormLabel.setText("Formulaire de connexion");
        passwordLabel.setText("Le mot de passe");
        loginButton.setText("Connexion");
        needAccessLabel.setText("Besoin d'un accès?");
        requestAccessHyperlink.setText("Demandez maintenant");
    }
}
