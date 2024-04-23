package Controllers;


import java.util.List;

import DB.DatabaseManager;
import Models.MP;
import Models.Product;
import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
public class StatistiquesController {
	 @FXML
	    private TableView<Product> zeroStockTable;
	    
	    @FXML
	    private TableView<Product> lowStockTable;
	    
	    @FXML
	    private TableView<MP> zeroStockTableMP;

	    @FXML
	    private TableView<MP> lowStockTableMP;
	    
	    @FXML
	    private TableColumn<Product, String> zeroStockNameColumn;
	    
	    @FXML
	    private TableColumn<Product, String> zeroStockReferenceColumn;
	    
	    @FXML
	    private TableColumn<Product, String> lowStockNameColumn;
	    
	    @FXML
	    private TableColumn<Product, String> lowStockReferenceColumn;
	    
	    @FXML
	    private TableColumn<MP, String> zeroStockNameColumn1;

	    @FXML
	    private TableColumn<MP, String> zeroStockReferenceColumn1;

	    @FXML
	    private TableColumn<MP, String> lowStockNameColumn1;

	    @FXML
	    private TableColumn<MP, String> lowStockReferenceColumn1;
	    
	    @FXML
	    private Label TotalInventaire;
	    
	    
	    private ObservableList<Product> products;

	    // ...

	    public void initialize() {
	        // Populate the zero stock table
	        List<Product> zeroStockProducts = DatabaseManager.getProductsByStockCondition("= 0");
	        zeroStockTable.getItems().setAll(zeroStockProducts);
	        
	        // Populate the low stock table
	        List<Product> lowStockProducts = DatabaseManager.getProductsByStockCondition("<= 5 AND stock <> 0");
	        lowStockTable.getItems().setAll(lowStockProducts);
	        
	        // Bind table columns to Product properties
	        zeroStockNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
	        zeroStockReferenceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReference()));
	        
	        lowStockNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
	        lowStockReferenceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReference()));
	        
	        List<MP> zeroStockMPs = DatabaseManager.getMPSByStockCondition("= 0");
	        zeroStockTableMP.getItems().setAll(zeroStockMPs);

	        // Populate the low stock MP table
	        List<MP> lowStockMPs = DatabaseManager.getMPSByStockCondition("<= 5 AND stock <> 0");
	        lowStockTableMP.getItems().setAll(lowStockMPs);

	        // Bind table columns to MP properties
	        zeroStockNameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
	        zeroStockReferenceColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReference()));

	        lowStockNameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
	        lowStockReferenceColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReference()));
	     // Convert the ArrayList to an ObservableList
	        products = FXCollections.observableArrayList(DatabaseManager.getAllProducts());
	        int totalInventaire = calculateTotalInventaire();
	        TotalInventaire.setText(totalInventaire + " DT");
	    }
	    
	    
	    private int calculateTotalInventaire() {
	        int total = 0;
	        for (Product product : products) {
	            total += product.getInventaire();
	        }
	        return total;
	    }
	    
	    
	    @FXML
	    private void switchToMainApp() {
	        Main.showMainAppScene();
	    }
	    
	    @FXML
	    private void handleLogout() {
	        // Clear the content or perform any necessary logout actions here

	        // Switch to the login scene
	        Main.showLoginScene();
	    }

}
