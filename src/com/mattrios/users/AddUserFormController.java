package com.mattrios.users;

import com.mattrios.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Matt Rios
 */
public class AddUserFormController {
    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;


    /**
     * Checks the test found in the user name field to ensure it is of the correct length and it does not already exist
     * in the users table. The method also checks the password for proper length and that it matches the confirm
     * password field. If any of these checks fail an appropriate alert message is displayed. If all checks are passed
     * the insertUser method is called in the PreparedStatements class which creates a new entry in the user table.
     * An alert message is generated upon success.
     */
    public void createAccountButtonClicked() {
        String userName = userNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (userName.length() > 50 || userName.length() < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User Name must be between 1-50 characters.");
            alert.showAndWait();
            return;
        }

        if (password.length() > 256 || password.length() < 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Password must be between 4-256 characters.");
            alert.showAndWait();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Password and confirm password do not match.");
            alert.showAndWait();
            return;
        }

        if (Database.userNameExists(userName)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User name already exists. Please choose another user name.");
            alert.showAndWait();
            return;
        }

        Database.insertUser(userName, password);

        Alert alert = new Alert(Alert.AlertType.NONE, "Account successfully created!");
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
        alert.showAndWait();

        closeStage();
    }

    /**
     * This hyperlink will return the user to the login form.
     */
    public void loginHyperlinkClicked() {
        closeStage();
    }

    /**
     * Closes the create account window.
     */
    private void closeStage() {
        Stage stage = (Stage) userNameField.getScene().getWindow();
        stage.close();
    }
}
