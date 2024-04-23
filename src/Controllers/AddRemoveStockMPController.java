package Controllers;

import DB.DatabaseManager;
import Models.ActionType;
import Models.MP;
import Models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddRemoveStockMPController {
	@FXML
    private ChoiceBox<String> actionChoiceBox;

    @FXML
    private TextField amountTextField;
    
    private MatieresPrimairesController MPController;
    
    private MP selectedMP; // Change the data type to MP

    public void setSelectedMP(MP mp) {
        this.selectedMP = mp;
    }

    public void initialize() {
        // Convert enum values to strings and populate the ChoiceBox
        actionChoiceBox.getItems().addAll(
            ActionType.AJOUTER.getDisplayName(),
            ActionType.RETIRER.getDisplayName()
        );
    }
    
    public void setProductManagementController(MatieresPrimairesController MPController) {
        this.MPController = MPController;
    }

    @FXML
    private void confirmAction() {
        String selectedAction = actionChoiceBox.getValue();
        String amountText = amountTextField.getText();

        if (selectedAction != null && !amountText.isEmpty() && selectedMP != null) {
            try {
                int amount = Integer.parseInt(amountText);

                if (selectedAction.equals("Ajouter")) {
                    // Update the stock column by adding the specified amount
                    selectedMP.setStock(selectedMP.getStock() + amount);
                } else if (selectedAction.equals("Retirer")) {
                    // Ensure you have validation to prevent negative stock
                    if (selectedMP.getStock() >= amount) {
                        // Update the stock column by deducting the specified amount
                        selectedMP.setStock(selectedMP.getStock() - amount);
                    } else {
                        // Handle insufficient stock error
                        // You can show a message to the user or take appropriate action
                    }
                }

                // Update the database with the new stock value
                DatabaseManager.updateMPStock(selectedMP);
                
                MPController.refreshProductTable();

                // Close the Add/Remove Stock stage after the action is performed
                Stage stage = (Stage) actionChoiceBox.getScene().getWindow();
                stage.close();
                showSuccessMessage("Stock mis à jour avec Succès");

            } catch (NumberFormatException e) {
                // Handle invalid input (non-integer) here
                e.printStackTrace();
            }
        } else {
            // Handle missing input here (action not selected, amount not entered, or MP not set)
        }
    }
    
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

}
