package Controllers;

import DB.DatabaseManager;
import Models.Categorie;
import Models.MP;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class AddMPController {
    @FXML
    private TextField mpNameField;

    @FXML
    private TextField mpReferenceField;

    @FXML
    private TextField mpStockField;

    @FXML
    private ComboBox<Categorie> mpCategoryComboBox;
    
    private MatieresPrimairesController MPController;

    @FXML
    public void initialize() {
        // Fetch categories from the database
        List<Categorie> categories = DatabaseManager.getAllCategories();

        // Add categories to the ComboBox
        mpCategoryComboBox.getItems().addAll(categories);

        // Set the cell factory to display only "nom" attribute
        mpCategoryComboBox.setCellFactory(param -> new ListCell<Categorie>() {
            @Override
            protected void updateItem(Categorie item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });

        // Set the cell factory for the ComboBox dropdown list
        mpCategoryComboBox.setButtonCell(new ListCell<Categorie>() {
            @Override
            protected void updateItem(Categorie item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });
    }
    
    public void setProductManagementController(MatieresPrimairesController MPController) {
        this.MPController = MPController;
    }

    @FXML
    private void addMPToDatabase() {
        // Retrieve MP details from the input fields
        String name = mpNameField.getText();
        String reference = mpReferenceField.getText();
        int stock = Integer.parseInt(mpStockField.getText());

        // Get the selected category
        Categorie selectedCategory = mpCategoryComboBox.getValue();
        int categorieId = selectedCategory.getId();

        // Create a new MP object with the category ID
        MP newMP = new MP(0, name, reference, selectedCategory.getNom(), stock, categorieId);

        // Add the new MP to the database
        DatabaseManager.addMP(newMP);
        MPController.refreshProductTable();
        // Close the "Add MP" stage
        Stage stage = (Stage) mpNameField.getScene().getWindow();
        stage.close();
        showSuccessMessage("MP Ajouté Avec Succès");

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
