package Controllers;

import DB.DatabaseManager;
import Models.User;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {
    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private void handleRegistration() {
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();

        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            registrationMessageLabel.setText("Un champ est vide!");
        } else if (DatabaseManager.getUserByUsername(newUsername) != null) {
            registrationMessageLabel.setText("Nom d'utilisateur existe deja.");
        } else {
            User newUser = new User(newUsername, newPassword);
            DatabaseManager.registerUser(newUser);
            registrationMessageLabel.setText("Inscrit avec succées!");

            // You can add logic here to transition to the login screen
        }
    }

    @FXML
    private void switchToLogin() {
        // Load and show the login scene
        Main.showLoginScene();
    }
}
