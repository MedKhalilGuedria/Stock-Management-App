package Controllers;

import DB.DatabaseManager;
import Models.User;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;

    private Stage primaryStage;

    public void setMainApp(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = DatabaseManager.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            Main.showMainAppScene();

            // You can add logic here to transition to another screen or main application screen
        } else {
            errorMessageLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    @FXML
    private void switchToRegistration() {
        // Load and show the registration scene
        Main.showRegistrationScene();
    }
}
