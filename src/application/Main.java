package application;
	
import Controllers.LoginController;
import Controllers.RegistrationController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	
	 private static Stage primaryStage;

	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        Main.primaryStage = primaryStage;
	        showLoginScene();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }

	    public static void showLoginScene() {
	        try {
	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/Login.fxml"));
	            Parent root = loader.load();
	            LoginController loginController = loader.getController();
	            loginController.setMainApp(Main.primaryStage);
	            primaryStage.setTitle("Login");
	            Image logoImage = new Image("file:src/Assets/ZA.jpg");
	            primaryStage.getIcons().add(logoImage);
	            primaryStage.setScene(new Scene(root, 600, 400));
	            primaryStage.setResizable(false);
	            primaryStage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void showStatsScene() {
	    	try {
	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/Statistiques.fxml"));
	            Parent root = loader.load();
	            primaryStage.setTitle("Statistiques");
	            Image logoImage = new Image("file:src/Assets/ZA.jpg");	           
	            primaryStage.getIcons().add(logoImage);
	            primaryStage.setScene(new Scene(root, 800, 600));
	            primaryStage.setMaximized(true);
	            primaryStage.setResizable(false);
// Adjust the size as needed
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void showRegistrationScene() {
	        try {
	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/Registration.fxml"));
	            Parent root = loader.load();
	            RegistrationController registrationController = loader.getController();
	            primaryStage.setTitle("Registration");
	            Image logoImage = new Image("file:src/Assets/ZA.jpg");	            primaryStage.getIcons().add(logoImage);
	            primaryStage.getIcons().add(logoImage);
	            primaryStage.setScene(new Scene(root, 600, 400));
	            primaryStage.setResizable(false);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void showMainAppScene() {
	        try {
	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/MainApp.fxml"));
	            Parent root = loader.load();

	            // Get the primary screen's dimensions
	            Screen screen = Screen.getPrimary();
	            double screenWidth = screen.getBounds().getWidth();
	            System.out.println(screenWidth);
	            double screenHeight = screen.getBounds().getHeight();

	            

	            // Set your scene to the screen's dimensions
	            primaryStage.setTitle("Accueil");
	            primaryStage.setWidth(1550);
	            primaryStage.setHeight(screenHeight);
	            Image logoImage = new Image("file:src/Assets/ZA.jpg");
	            primaryStage.getIcons().add(logoImage);

	            primaryStage.setScene(new Scene(root));
	            primaryStage.setMaximized(true);

	            primaryStage.setResizable(false);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void showProductManagementScene() {
	        try {
	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/ProductManagement.fxml"));
	            Parent root = loader.load();
	            primaryStage.setTitle("Gestion des Produits");
	            Image logoImage = new Image("file:src/Assets/ZA.jpg");	            primaryStage.getIcons().add(logoImage);
	            primaryStage.getIcons().add(logoImage);
	            primaryStage.setScene(new Scene(root, 800, 600));
	            primaryStage.setMaximized(true);// Adjust the size as needed
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public static void showMPManagementScene() {
	        try {
	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/MatierePrimairesManagement.fxml"));
	            Parent root = loader.load();
	            primaryStage.setTitle("Gestion des MP");
	            Image logoImage = new Image("file:src/Assets/ZA.jpg");	            primaryStage.getIcons().add(logoImage);
	            primaryStage.getIcons().add(logoImage);
	            primaryStage.setScene(new Scene(root, 800, 600));
	            primaryStage.setMaximized(true);// Adjust the size as needed
	        } catch (Exception e) {
	            e.printStackTrace();
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
	                addProductStage.setScene(new Scene(root, 600, 400));
		            Image logoImage = new Image("file:src/Assets/ZA.jpg");	            primaryStage.getIcons().add(logoImage);
		            addProductStage.getIcons().add(logoImage);
	                // Show the new stage
	                addProductStage.show();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }}
	            
	            @FXML
	            public static void openStockSelectionScene() {
	            	try {
	    	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/StockSelection.fxml"));
	    	            Parent root = loader.load();
	    	            primaryStage.setTitle("Selection de Stock");
	    	            primaryStage.setScene(new Scene(root, 800, 600));
	    	            primaryStage.setMaximized(true);
	    	            Image logoImage = new Image("file:src/Assets/ZA.jpg");	            primaryStage.getIcons().add(logoImage);
	    	            primaryStage.getIcons().add(logoImage);// Adjust the size as needed
	    	        } catch (Exception e) {
	    	            e.printStackTrace();
	    	        
	    	    }}
	            	
	            	@FXML
		            public static void openCategoryManagementScene() {
		            	try {
		    	            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Views/CategoryManagement.fxml"));
		    	            Parent root = loader.load();
		    	            Image logoImage = new Image("file:src/Assets/ZA.jpg");	          
		    	            primaryStage.getIcons().add(logoImage);
		    	            primaryStage.setTitle("Gestion des Catégories des Produits et MP");
		    	            primaryStage.setScene(new Scene(root, 800, 600));
		    	            primaryStage.setMaximized(true);// Adjust the size as needed
		    	        } catch (Exception e) {
		    	            e.printStackTrace();
		    	        
		    	    }
	        

    }}