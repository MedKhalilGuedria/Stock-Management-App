package Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import DB.DatabaseManager;
import Models.ActionType;
import Models.Product;

public class AddRemoveStockController {

    @FXML
    private ChoiceBox<String> actionChoiceBox;

    @FXML
    private TextField amountTextField;

    private Product selectedProduct; // You need to set this value from ProductManagementController
    
    private ProductManagementController productManagementController;

    public void setProductManagementController(ProductManagementController productManagementController) {
        this.productManagementController = productManagementController;
    }    
    public void setSelectedProduct(Product product) {
        this.selectedProduct = product;
    }

    public void initialize() {
        // Convert enum values to strings and populate the ChoiceBox
        actionChoiceBox.getItems().addAll(
            ActionType.AJOUTER.getDisplayName(),
            ActionType.RETIRER.getDisplayName()
        );
    }
    
    
    @FXML
    private void confirmAction() {
        String selectedAction = actionChoiceBox.getValue();
        String amountText = amountTextField.getText();

        if (selectedAction != null && !amountText.isEmpty() && selectedProduct != null) {
            try {
                int amount = Integer.parseInt(amountText);

                if (selectedAction.equals("Ajouter")) {
                    // Update the stock column by adding the specified amount
                    selectedProduct.setStock(selectedProduct.getStock() + amount);
                } else if (selectedAction.equals("Retirer")) {
                    // Ensure you have validation to prevent negative stock
                    if (selectedProduct.getStock() >= amount) {
                        // Update the stock column by deducting the specified amount
                        selectedProduct.setStock(selectedProduct.getStock() - amount);
                    } else {
                        // Handle insufficient stock error
                        // You can show a message to the user or take appropriate action
                    }
                }

                // Update the database with the new stock value
                DatabaseManager.updateProductStock(selectedProduct);

                productManagementController.refreshProductTable();

                // Close the Add/Remove Stock stage after the action is performed
                Stage stage = (Stage) actionChoiceBox.getScene().getWindow();
                stage.close();
                showSuccessMessage("Stock mis à jour avec Succès");

            } catch (NumberFormatException e) {
                // Handle invalid input (non-integer) here
                e.printStackTrace();
            }
        } else {
            // Handle missing input here (action not selected, amount not entered, or product not set)
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