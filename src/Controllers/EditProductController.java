package Controllers;

import DB.DatabaseManager;
import Models.Categorie;
import Models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.util.List;

public class EditProductController {
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField referenceTextField;

    @FXML
    private TextField stockTextField;
    
    @FXML
    private TextField editUnitPriceTextField;

    @FXML
    private ComboBox<Categorie> editProductCategoryComboBox;

    private Product selectedProduct;
    
    private ProductManagementController productManagementController;

    public void setProductManagementController(ProductManagementController productManagementController) {
        this.productManagementController = productManagementController;
    }    

    public void setSelectedProduct(Product product) {
        this.selectedProduct = product;
        // Initialize fields with the selected product's data
        nameTextField.setText(selectedProduct.getName());
        referenceTextField.setText(selectedProduct.getReference());
        stockTextField.setText(Integer.toString(selectedProduct.getStock()));
        editUnitPriceTextField.setText(String.valueOf(selectedProduct.getPrixUnitaire()));
        // Set the StringConverter for the ComboBox
        editProductCategoryComboBox.setConverter(new StringConverter<Categorie>() {
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
        editProductCategoryComboBox.getItems().setAll(categories);
        System.out.println(selectedProduct.getCategorie());

        // Find the category of the selected product and set it as the default value
        Categorie productCategory = DatabaseManager.findCategoryByNom(selectedProduct.getCategorie());
        editProductCategoryComboBox.setValue(productCategory);
    }
    @FXML
    private void editProduct() {
    	if (selectedProduct != null) {
            // Update the selected product's data
            selectedProduct.setName(nameTextField.getText());
            selectedProduct.setReference(referenceTextField.getText());
            selectedProduct.setStock(Integer.parseInt(stockTextField.getText()));
            selectedProduct.setCategorie(editProductCategoryComboBox.getValue().getNom());
            selectedProduct.setCategorieId(editProductCategoryComboBox.getValue().getId());

            // Update the Prix Unitaire (price)
            double unitPrice = Double.parseDouble(editUnitPriceTextField.getText());
            selectedProduct.setPrixUnitaire(unitPrice);

            // Calculate and update the Inventaire
           double inventaire = unitPrice * selectedProduct.getStock();
            selectedProduct.setInventaire(inventaire);

            // Update the product in the database
            DatabaseManager.updateProduct(selectedProduct);
            productManagementController.refreshProductTable();


            // Close the Edit Product stage
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.close();
            showSuccessMessage("Produit Modifié Avec Succès");

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

        // Add the ImageView to the top-left corner of the DialogPane
        // Set the custom StackPane as the content of the Alert
       

        alert.showAndWait();
    }
    }
