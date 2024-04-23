package Controllers;

import java.util.List;

import DB.DatabaseManager;
import Models.Categorie;
import Models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AddProductController {
    @FXML
    private TextField productNameField;

    @FXML
    private TextField productReferenceField;

    @FXML
    private TextField productCategoryField;
    
    @FXML
    private TextField unitPrice;

    @FXML
    private TextField productStockField;
    @FXML
    private ComboBox<Categorie> productCategoryComboBox; 
    // Add a ComboBox to select categories
    @FXML
    
    
    private ProductManagementController productManagementController;
    
    
    public void initialize() {
        // Fetch categories from the database
        List<Categorie> categories = DatabaseManager.getAllCategories();

        // Add categories to the ComboBox
        productCategoryComboBox.getItems().addAll(categories);

        // Set the cell factory to display only "nom" attribute
        productCategoryComboBox.setCellFactory(param -> new ListCell<Categorie>() {
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
        productCategoryComboBox.setButtonCell(new ListCell<Categorie>() {
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
    
    public void setProductManagementController(ProductManagementController productManagementController) {
        this.productManagementController = productManagementController;
    }

    @FXML
    private void addProductToDatabase() {
    	// Retrieve product details from the input fields
        String name = productNameField.getText();
        String reference = productReferenceField.getText();
        int stock = Integer.parseInt(productStockField.getText());
        double prixUnitaire = Double.parseDouble(unitPrice.getText());  // Retrieve Prix Unitaire value

        // Get the selected category
        Categorie selectedCategory = productCategoryComboBox.getValue();
        int categorieId = selectedCategory.getId();

        // Create a new Product object with the category ID and Prix Unitaire
        Product newProduct = new Product(0, name, reference, selectedCategory.getNom(), stock, categorieId, prixUnitaire, stock * prixUnitaire);

        // Add the new product to the database
        DatabaseManager.addProduct(newProduct);
        
        productManagementController.refreshProductTable();

        // Close the "Add Product" stage
        Stage stage = (Stage) productNameField.getScene().getWindow();
        
        
        stage.close();
        
        showSuccessMessage("Produit Ajouté Avec Succès");

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