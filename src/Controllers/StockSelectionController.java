package Controllers;

import application.Main;
import javafx.fxml.FXML;

public class StockSelectionController {
	  @FXML
	    private void switchToProductManagement() {
	        Main.showProductManagementScene();
	    }
	  
	  @FXML
	    private void switchToMPManagement() {
	        Main.showMPManagementScene();
	    }
	  
	  @FXML
	    private void switchToMainApp() {
	        Main.showMainAppScene();
	    }
}
