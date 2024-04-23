package Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

import DB.DatabaseManager;
import Models.Categorie;
import application.Main;


public class CategoryManagementController {
	
	 @FXML
	    private TableView<Categorie> categoryTableView;
	 @FXML
	    private TextField categoryNameTextField;

	    @FXML
	    private TableColumn<Categorie, String> categoryNameColumn;
	    @FXML
	    private Pagination categorypagination;
	    @FXML
	    private TextField rechercherTextField;
	    private List<Categorie> categories; // List of all categories
	    private int itemsPerPage = 14; // Number of items to display per page

	    public void initialize() {
	        // Retrieve categories from the database.
	         categories = DatabaseManager.getAllCategories();

	        // Configure the cell value factory to display the "nom" property.
	        categoryNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));

	        // Add the categories to the TableView.
	        categoryTableView.getItems().addAll(categories);

	       

	        // Set up the search functionality
	        rechercherTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	            filterCategories(newValue);
	        }); 
	        
	        setupPagination();
	        
	        
	    }
	    
	    private void setupPagination() {
	        // Calculate the number of pages
	        int totalPages = (int) Math.ceil((double) categories.size() / itemsPerPage);
	        System.out.println(totalPages);

	        // Set the number of pages for the Pagination control
	        categorypagination.setPageCount(totalPages);

	        // Add a listener to handle page changes
	        categorypagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
	            int currentPageIndex = newValue.intValue();

	            // Calculate the range of categories to display on the current page
	            int startIndex = currentPageIndex * itemsPerPage;
	            int endIndex = Math.min(startIndex + itemsPerPage, categories.size());

	            if (startIndex < endIndex) {
	                // Create a sublist of categories to display on the current page
	                List<Categorie> itemsOnPage = categories.subList(startIndex, endIndex);
	                System.out.println(itemsOnPage);

	                // Update the TableView with the categories for the current page
	                categoryTableView.setItems(FXCollections.observableArrayList(itemsOnPage));
	            } else {
	                // Handle the case where there are no items to display
	                categoryTableView.setItems(FXCollections.emptyObservableList());
	            }
	        });
	    }


	    private Node createPage(int pageIndex) {
	        int fromIndex = pageIndex * itemsPerPage;
	        int toIndex = Math.min(fromIndex + itemsPerPage, categories.size());
	        List<Categorie> pageItems = categories.subList(fromIndex, toIndex);
	        
            System.out.println(pageItems) ;  

	        categoryTableView.setItems(FXCollections.observableArrayList(pageItems));

	        return categoryTableView;
	    }

	    // ... Existing methods ...

	    @FXML
	    private void handlePageChange() {
	        int pageIndex = categorypagination.getCurrentPageIndex();
	        categorypagination.setCurrentPageIndex(pageIndex);
	    }
	   

	    private void filterCategories(String query) {
	        List<Categorie> filteredCategories = DatabaseManager.getCategoriesByName(query); // Implement a method in DatabaseManager to retrieve filtered categories
	        categoryTableView.getItems().clear();
	        categoryTableView.getItems().addAll(filteredCategories);
	    }
	    

	    public void addCategory() {
	        String categoryName = categoryNameTextField.getText();
	        
	        // Check if the category name is empty or null.
	        if (categoryName == null || categoryName.trim().isEmpty()) {
	            showAlert("Nom de Catégorie ne peut pas etre vide.");
	            return;
	        }

	        // Create a new Categorie object.
	        Categorie newCategory = new Categorie(0, categoryName);

	        // Add the new category to the database.
	        DatabaseManager.addCategory(newCategory);

	        // Clear the text field.
	        categoryNameTextField.clear();
	        this.initialize();

	        showAlert("Category ajouté avec succées.");
	    }

	    private void showAlert(String message) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Information");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
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
	    
	    @FXML
	    private void deleteCategory() {
	        Categorie selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
	        if (selectedCategory != null) {
	            // Show a confirmation dialog
	            if (showConfirmationDialog("Category")) {
	                // Delete the selected category from the database.
	                DatabaseManager.deleteCategory(selectedCategory);

	                // Remove the selected category from the TableView.
	                categoryTableView.getItems().remove(selectedCategory);

	                showDeletionSuccessMessage("Category");
	            }
	        } else {
	            showAlert("Selectionner une catégorie a supprimer s'il vous plait.");
	        }
	    }

	    private void showDeletionSuccessMessage(String itemName) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Success");
	        alert.setHeaderText(null);
	        alert.setContentText(itemName + " Supprimé avec succés.");
	        Image logoImage = new Image("file:src/Assets/ZA.jpg"); // Replace with the correct path

	        
	        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
	        alertStage.getIcons().add(logoImage);
	        alert.showAndWait();
	    }
	    
	    @FXML
	    private void editCategory() {
	        Categorie selectedCategory = categoryTableView.getSelectionModel().getSelectedItem();
	        if (selectedCategory != null) {
	            // Open an edit dialog to modify the category details.
	            boolean isEditClicked = showCategoryEditDialog(selectedCategory);
	            if (isEditClicked) {
	                // Refresh the category list in the TableView after editing.
	                refreshCategoryList();
	            }
	        } else {
	            showAlert("Selectionner une catégorie a modifier s'il vous plait.");
	        }
	    }

	    private void refreshCategoryList() {
	        // Clear the current items in the TableView.
	        categoryTableView.getItems().clear();

	        // Retrieve categories from the database and add them to the TableView.
	        List<Categorie> categories = DatabaseManager.getAllCategories();
	        categoryTableView.getItems().addAll(categories);
	    }

	    

	    private boolean showCategoryEditDialog(Categorie category) {
	    	
	    	Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Category");

	        // Load and set your logo image.
	        Image logoImage = new Image("file:src/Assets/ZA.jpg"); // Replace with the correct path

	        // Create an ImageView with the logo image.

	        // Set the logo ImageView as the icon for the custom Stage.
	        dialogStage.getIcons().add(logoImage);
	    	
	    	
	        // Create a custom dialog.
	        Dialog<Pair<String, String>> dialog = new Dialog<>();
	        dialog.setTitle("Modifier Catégorie");
	       

	        // Set the button types (Save and Cancel).
	        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

	        // Create a GridPane to layout the dialog content.
	        GridPane gridPane = new GridPane();
	        gridPane.setHgap(10);
	        gridPane.setVgap(10);

	        // Add labels and text fields for editing.
	        TextField categoryNameTextField = new TextField(category.getNom());
	        gridPane.add(new Label("Nom Catégorie:"), 0, 0);
	        gridPane.add(categoryNameTextField, 1, 0);

	        // Set the content of the dialog.
	        dialog.getDialogPane().setContent(gridPane);

	        // Request focus on the category name text field by default.
	        categoryNameTextField.requestFocus();

	        // Convert the result to a pair (name, null if canceled).
	        dialog.setResultConverter(dialogButton -> {
	            if (dialogButton == ButtonType.OK) {
	                return new Pair<>(categoryNameTextField.getText(), null);
	            }
	            return null;
	        });

	        // Show the dialog and wait for the user's response.
	        Optional<Pair<String, String>> result = dialog.showAndWait();

	        result.ifPresent(pair -> {
	            if (pair.getKey() != null) {
	                // User clicked Save, update the category name.
	                category.setNom(pair.getKey());
	                // Update the category in the database here.
	                DatabaseManager.updateCategory(category);
	            }
	        });

	        // Return true if the user clicked Save, false otherwise.
	        return result.isPresent();
	    }

	    
	    @FXML
	    private void switchToMainApp() {
	        Main.showMainAppScene();;
	    }
	    

}
