package Controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainAppController {
	  @FXML
	    private Label contentLabel;
	  
	  

	    @FXML
	    private void handleLogout() {
	        // Clear the content or perform any necessary logout actions here

	        // Switch to the login scene
	        Main.showLoginScene();
	    }
	    
	    @FXML
	    private void switchToProductManagement() {
	        Main.showProductManagementScene();
	    }
	    
	    @FXML
	    private void openStockSelectionScene() {
	        Main.openStockSelectionScene();

	    }
	    
	    @FXML
	    private void openCategoryManagementScene() {
	        Main.openCategoryManagementScene();

	    }
	    
	    @FXML
	    private void openStatsScene() {
	        Main.showStatsScene();

	    }

}
