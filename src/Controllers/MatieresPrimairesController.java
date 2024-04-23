package Controllers;

import java.util.List;

import DB.DatabaseManager;
import Models.MP;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MatieresPrimairesController {
	
	@FXML
    private TableView<MP> mpTable;

    @FXML
    private TableColumn<MP, Integer> idColumn;
    
    @FXML
    private Pagination mpPagination;

    private static final int ITEMS_PER_PAGE = 14; // Set the number of items to display per page

    @FXML
    private TableColumn<MP, String> nameColumn;

    @FXML
    private TableColumn<MP, String> referenceColumn;

    @FXML
    private TableColumn<MP, String> categorieColumn;

    @FXML
    private TableColumn<MP, Integer> stockColumn;
    
    @FXML
    private TextField SearchMPTextField;

    private ObservableList<MP> mps = FXCollections.observableArrayList();
    
    
   

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Set the items in the TableView
        mpTable.setItems(mps);

        // Load MPs from the database or other source
        loadMPsFromDatabase();
        
        setupPagination();

        // Add an event handler to the search TextField
        SearchMPTextField.setOnKeyReleased(event -> searchMaterials());
    }
    
    public void refreshProductTable() {
        mps.clear(); // Clear the existing data
        loadMPsFromDatabase(); // Load fresh data from the database
        setupPagination(); // Reconfigure pagination
    }
   
    
    private void searchMaterials() {
        String searchText = SearchMPTextField.getText().toLowerCase(); // Get the search text and convert it to lowercase for case-insensitive search
        ObservableList<MP> filteredMaterials = FXCollections.observableArrayList();

        if (searchText.isEmpty()) {
            mpTable.setItems(mps); // If the search field is empty, display all materials
            return;
        }

        for (MP material : mps) {
            if (material.getName().toLowerCase().contains(searchText) || // Match by name
                material.getReference().toLowerCase().contains(searchText) || // Match by reference
                material.getCategorie().toLowerCase().contains(searchText) || // Match by category
                String.valueOf(material.getStock()).contains(searchText)) { // Match by stock (converted to string)
                filteredMaterials.add(material);
            }
        }

        mpTable.setItems(filteredMaterials); // Update the TableView with the filtered materials
    }
    
    private void setupPagination() {
        // Get the total number of materials
        int totalMaterials = mps.size();

        // Calculate the number of pages
        int totalPages = (int) Math.ceil((double) totalMaterials / ITEMS_PER_PAGE);

        // Set the number of pages for the Pagination control
        mpPagination.setPageCount(totalPages);

        // Add a listener to handle page changes
        mpPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int currentPageIndex = newValue.intValue();

            // Calculate the range of materials to display on the current page
            int startIndex = currentPageIndex * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, totalMaterials);

            if (startIndex < endIndex) {
                // Create a sublist of materials to display on the current page
                List<MP> itemsOnPage = mps.subList(startIndex, endIndex);

                // Update the TableView with the materials for the current page
                mpTable.setItems(FXCollections.observableArrayList(itemsOnPage));
            } else {
                // Handle the case where there are no items to display
                mpTable.setItems(FXCollections.emptyObservableList());
            }
        });
    }

    @FXML
    private void openAddMPScene() {
        // Implement the logic to open the "Add MP" scene
    	 try {
             FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/AddMP.fxml"));
             Parent root = loader.load();

             // Create a new stage for the "Add Product" scene
             Stage addProductStage = new Stage();
             Image logoImage = new Image("file:src/Assets/ZA.jpg");	          
             addProductStage.getIcons().add(logoImage);
             addProductStage.setTitle("Ajouter MP");
             addProductStage.setScene(new Scene(root, 600, 400));
             addProductStage.setResizable(false);
             
             AddMPController controller = loader.getController();
             controller.setProductManagementController(this);

             // Show the new stage
             addProductStage.show();
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    @FXML
    private void openAddRemoveStockMPScene() {
        try {
            // Load the AddRemoveStock.fxml and get the controller
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/AddRemoveStockMP.fxml"));
            Parent root = loader.load();
            AddRemoveStockMPController addRemoveStockController = loader.getController();

            // Set the selected MP in the AddRemoveStockController
            MP selectedMP = mpTable.getSelectionModel().getSelectedItem();
            if (selectedMP != null) {
                addRemoveStockController.setSelectedMP(selectedMP);
            }

            // Create a new stage for the "Add/Remove Stock" scene
            Stage addRemoveStockStage = new Stage();
            addRemoveStockStage.setTitle("Ajouter/Retirer Stock  MP");
            Image logoImage = new Image("file:src/Assets/ZA.jpg");	          
            addRemoveStockStage.getIcons().add(logoImage);
            addRemoveStockStage.setScene(new Scene(root, 400, 200));
            addRemoveStockStage.setResizable(false);
            // Adjust the size as needed
            
            addRemoveStockController.setProductManagementController(this);

            // Show the new stage
            addRemoveStockStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMPsFromDatabase() {
        // Implement the logic to load MPs from the database and add them to the mps list
    	// Clear the existing data in the observable list
        mps.clear();

        // Call the DatabaseManager method to retrieve all MPs from the database
        List<MP> mpListFromDB = DatabaseManager.getAllMPs();

        // Add the retrieved MPs to the observable list
        mps.addAll(mpListFromDB);
    }

   

    

    @FXML
    private void handleLogout() {
        // Implement the logic to handle logout
    }
    
    @FXML
    private void editMP() {
        MP selectedMP = mpTable.getSelectionModel().getSelectedItem();
        if (selectedMP != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/EditMPScreen.fxml"));
                Parent root = loader.load();
                EditMPController editMPController = loader.getController();

                // Pass the selected MP to the EditMPController
                editMPController.setSelectedMP(selectedMP);

                // Create a new stage for the edit MP scene
                Stage editMPStage = new Stage();
                editMPStage.setTitle("Modifier MP");
                Image logoImage = new Image("file:src/Assets/ZA.jpg");	          
                editMPStage.getIcons().add(logoImage);
                editMPStage.setScene(new Scene(root, 600, 400));
                editMPStage.setResizable(false);
                editMPController.setProductManagementController(this);
                editMPStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteMP() {
        MP selectedMP = mpTable.getSelectionModel().getSelectedItem();
        if (selectedMP != null) {
            // Show a confirmation dialog
            if (showConfirmationDialog("MP")) {
                // Remove the selected MP from the list
                mps.remove(selectedMP);

                // Update the database to delete the MP
                DatabaseManager.deleteMP(selectedMP);

                showDeletionSuccessMessage("MP");
            }
        } else {
            showAlert("Selectionner une MP a supprimer s'il vous plait.");
        }}
        
        
    
    @FXML
    private void showstockselection() {
        // Clear the content or perform any necessary logout actions here

        // Switch to the login scene
        Main.openStockSelectionScene();
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
