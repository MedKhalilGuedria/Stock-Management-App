package Controllers;
import java.util.List;

import DB.DatabaseManager;
import Models.Product;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductManagementController {
   

    @FXML
    private TableView<Product> productTable;
    
   
    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> libelleColumn;

    @FXML
    private TableColumn<Product, String> referenceColumn;

    @FXML
    private TableColumn<Product, String> categorieColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;
    
    @FXML
    private TableColumn<Product, Integer> productPriceColumn;

    @FXML
    private TableColumn<Product, Integer> inventaireColumn;
   
    @FXML
    private TextField SearchTextField;
    
    @FXML
    private Pagination productPagination;
    

    private ObservableList<Product> products = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
    	    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    	    libelleColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	    referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
    	    categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
    	    stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    	    productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
    	    inventaireColumn.setCellValueFactory(new PropertyValueFactory<>("inventaire"));

    	    // Set the items in the TableView
    	    productTable.setItems(products);
    	 // Add an event handler to the search TextField
    	    SearchTextField.setOnKeyReleased(event -> searchProducts());
    	    

    	    // Load products from the database or other source
    	    loadProductsFromDatabase();
    	    setupPagination();

    }

   /* private void addProduct() {
    	//String name = productNameField.getText();
        double price = Double.parseDouble(productPriceField.getText());

        Product product = new Product(0, name, price);
        DatabaseManager.addProduct(product);
        products.add(product);

        productNameField.clear();
        productPriceField.clear();
    } */
    
    
    private void setupPagination() {
        // Get the total number of products
        int totalProducts = products.size();
        System.out.println(totalProducts);

        // Set the number of items to display on each page (you can adjust this as needed)
        int itemsPerPage = 14;

        // Calculate the number of pages
        int totalPages = (int) Math.ceil((double) totalProducts / itemsPerPage);
        System.out.println(totalPages);

        // Set the number of pages for the Pagination control
        productPagination.setPageCount(totalPages);

        // Add a listener to handle page changes
        productPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int currentPageIndex = newValue.intValue();

            // Calculate the range of products to display on the current page
            int startIndex = currentPageIndex * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalProducts);

            if (startIndex < endIndex) {
                // Create a sublist of products to display on the current page
                List<Product> itemsOnPage = products.subList(startIndex, endIndex);
                System.out.println(itemsOnPage);

                // Update the TableView with the products for the current page
                productTable.setItems(FXCollections.observableArrayList(itemsOnPage));
            } else {
                // Handle the case where there are no items to display
                productTable.setItems(FXCollections.emptyObservableList());
            }
        });
    }
  
    
    
    
    private void searchProducts() {
        String searchText = SearchTextField.getText().toLowerCase(); // Get the search text and convert it to lowercase for case-insensitive search
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            productTable.setItems(products); // If the search field is empty, display all products
            return;
        }

        for (Product product : products) {
            if (product.getName().toLowerCase().contains(searchText) ||
                product.getReference().toLowerCase().contains(searchText) ||
                product.getCategorie().toLowerCase().contains(searchText) ||
                String.valueOf(product.getStock()).contains(searchText) ||
                String.valueOf(product.getPrixUnitaire()).contains(searchText) || // Search for integer values
                String.valueOf(product.getInventaire()).contains(searchText)) {     // Search for integer values
                filteredProducts.add(product);
            }
        }

        productTable.setItems(filteredProducts);// Update the TableView with the filtered products
    }
    
    public void refreshProductTable() {
        products.clear(); // Clear the existing data
        loadProductsFromDatabase(); // Load fresh data from the database
        setupPagination(); // Reconfigure pagination
    }
    
    @FXML
    private boolean showEditProductDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/EditProductScreen.fxml"));
            Parent root = loader.load();

            // Create a new stage for the edit product screen
            Stage editProductStage = new Stage();
            editProductStage.setTitle("Modifier Produit");
            editProductStage.initModality(Modality.WINDOW_MODAL);
            editProductStage.initOwner(productTable.getScene().getWindow());
            Image logoImage = new Image("file:src/Assets/ZA.jpg");	          
            editProductStage.getIcons().add(logoImage);
            editProductStage.setScene(new Scene(root, 600, 400));
            editProductStage.setResizable(false);
            // Get the controller and set the product to edit
            EditProductController controller = loader.getController();
           System.out.println(productTable.getSelectionModel().getSelectedItem());
            controller.setSelectedProduct(productTable.getSelectionModel().getSelectedItem());
            controller.setProductManagementController(this);
            // Show the edit product screen and wait for it to close
            editProductStage.showAndWait();

            // Return true if changes were saved, false otherwise
            return true; // Modify this based on your actual implementation
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @FXML
    private void openAddProductScene() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/AddProduct.fxml"));
            Parent root = loader.load();

            // Create a new stage for the "Add Product" scene
            Stage addProductStage = new Stage();
            addProductStage.setTitle("Ajouter Produit");
            Image logoImage = new Image("file:src/Assets/ZA.jpg");	          
            addProductStage.getIcons().add(logoImage);
            addProductStage.setScene(new Scene(root, 600, 400));
            addProductStage.setResizable(false);
            
            AddProductController controller = loader.getController();
            controller.setProductManagementController(this);

            // Show the new stage
            addProductStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void openAddRemoveStockScene() {
        try {
            // Load the AddRemoveStock.fxml and get the controller
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/AddRemoveStock.fxml"));
            Parent root = loader.load();
            AddRemoveStockController addRemoveStockController = loader.getController();

            // Set the selected product in the AddRemoveStockController
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                addRemoveStockController.setSelectedProduct(selectedProduct);
            }

            // Create a new stage for the "Add/Remove Stock" scene
            Stage addRemoveStockStage = new Stage();
            addRemoveStockStage.setTitle("Ajouter/Retirer Stock Produit");
            Image logoImage = new Image("file:src/Assets/ZA.jpg");	          
            addRemoveStockStage.getIcons().add(logoImage);
            addRemoveStockStage.setScene(new Scene(root, 400, 200)); // Adjust the size as needed
            addRemoveStockStage.setResizable(false);
            addRemoveStockController.setProductManagementController(this);
            // Show the new stage
            addRemoveStockStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    
  

    private void loadProductsFromDatabase() {
    	List<Product> productListFromDB = DatabaseManager.getAllProducts();
        products.addAll(productListFromDB);
    }
    
 // Handle deletion of a product
    @FXML
    private void deleteProduct() {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Show a confirmation dialog
            if (showConfirmationDialog("Produit")) {
                // Remove the selected product from the TableView
                products.remove(selectedProduct);

                // Delete the selected product from the database using DatabaseManager
                DatabaseManager.deleteProduct(selectedProduct);

                showDeletionSuccessMessage("Produit");
            }
        } else {
            showAlert("selectionner un produit à supprimer s'il vous plait.");
        }
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Gestion des Produits");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Image logoImage = new Image("file:src/Assets/ZA.jpg"); // Replace with the correct path

        
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(logoImage);
        alert.showAndWait();
    }
    
    // Handle modification of a product (e.g., edit in a dialog)
   
    @FXML
    private void showstockselection() {
        // Clear the content or perform any necessary logout actions here

        // Switch to the login scene
        Main.openStockSelectionScene();
    }
    
    @FXML
    private void handleLogout() {
        // Clear the content or perform any necessary logout actions here

        // Switch to the login scene
        Main.showLoginScene();
    }
    
    private void showDeletionSuccessMessage(String itemName) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(itemName + " Supprimé avec succées.");
 Image logoImage = new Image("file:src/Assets/ZA.jpg"); // Replace with the correct path

        
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(logoImage);
        alert.showAndWait();
    }
    
    private boolean showConfirmationDialog(String itemName) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de supprimer ce " + itemName + "?");
       Image logoImage = new Image("file:src/Assets/ZA.jpg"); // Replace with the correct path

        
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(logoImage);

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        return result == ButtonType.OK;
    }
    
}