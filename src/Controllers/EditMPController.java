package Controllers;

import DB.DatabaseManager;
import Models.Categorie;
import Models.MP;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class EditMPController {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField referenceTextField;

    @FXML
    private TextField stockTextField;
    private MatieresPrimairesController MPController;
    @FXML
    private ComboBox<Categorie> editMPCategoryComboBox;

    private MP selectedMP;

    public void setSelectedMP(MP mp) {
        this.selectedMP = mp;
        // Initialize fields with the selected MP's data
        nameTextField.setText(selectedMP.getName());
        referenceTextField.setText(selectedMP.getReference());
        stockTextField.setText(Integer.toString(selectedMP.getStock()));

        // Set the StringConverter for the ComboBox
        editMPCategoryComboBox.setConverter(new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie categorie) {
                return categorie.getNom();
            }

            @Override
            public Categorie fromString(String string) {
                // You can implement this if you need two-way conversion
                return null;
            }
        });

        // Populate and set the ComboBox with available categories
        List<Categorie> categories = DatabaseManager.getAllCategories();
        editMPCategoryComboBox.getItems().setAll(categories);
        System.out.println(selectedMP.getCategorie());

        // Find the category of the selected MP and set it as the default value
        Categorie mpCategory = DatabaseManager.findCategoryByNom(selectedMP.getCategorie());
        editMPCategoryComboBox.setValue(mpCategory);
    }
    
    public void setProductManagementController(MatieresPrimairesController MPController) {
        this.MPController = MPController;
    }
    @FXML
    private void editMP() {
        if (selectedMP != null) {
            // Update the selected MP's data
            selectedMP.setName(nameTextField.getText());
            selectedMP.setReference(referenceTextField.getText());
            selectedMP.setStock(Integer.parseInt(stockTextField.getText()));
            selectedMP.setCategorie(editMPCategoryComboBox.getValue().getNom());
            selectedMP.setCategoryId(editMPCategoryComboBox.getValue().getId());

            // Update the MP in the database
            DatabaseManager.updateMP(selectedMP);
            MPController.refreshProductTable();

            // Close the Edit MP stage
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.close();
            showSuccessMessage("MP Modifié Avec Succès");

        }
    }
    
    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Image logoImage = new Image("file:src/Assets/ZA.jpg"); // Replace with the correct path

        
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(logoImage);

        alert.showAndWait();
    }
}
