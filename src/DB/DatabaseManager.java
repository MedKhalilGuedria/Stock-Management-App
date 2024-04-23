package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.Categorie;
import Models.MP;
import Models.Product;
import Models.User;

public class DatabaseManager {
    private static Connection connection = null;

    static {
        String host = "sql8.freesqldatabase.com";
        String databaseName = "sql8651847";
        String username = "sql8651847";
        String password = "pp1wzvYKut";
        int port = 3306;

        try {
            // Initialize the connection
            String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerUser(User user) {
        String insertUserSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to register user.");
        }
    }

    public static User getUserByUsername(String username) {
        String selectUserSQL = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectUserSQL)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String foundUsername = resultSet.getString("username");
                String foundPassword = resultSet.getString("password");
                return new User(foundUsername, foundPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectAllUsersSQL = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsersSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                userList.add(new User(username, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    /*
    public static void addProduct(Product product) {
        String insertProductSQL = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertProductSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add product.");
        }
    }
*/
    
    
    public static void addProduct(Product product) {
        String insertProductSQL = "INSERT INTO products (name, reference, categorie, stock, categorie_id, prix_unitaire, inventaire) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertProductSQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getReference());
            preparedStatement.setString(3, product.getCategorie());
            preparedStatement.setInt(4, product.getStock());
            preparedStatement.setInt(5, product.getCategorieId());
            preparedStatement.setDouble(6, product.getPrixUnitaire()); // Set Prix Unitaire
            preparedStatement.setDouble(7, product.getPrixUnitaire() * product.getStock()); // Set Inventaire
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add product.");
        }
    }
    
    public static List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectAllProductsSQL = "SELECT * FROM products";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllProductsSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String reference = resultSet.getString("reference");
                String categorie = resultSet.getString("categorie");
                int stock = resultSet.getInt("stock");
                double prixUnitaire = resultSet.getDouble("prix_unitaire"); // Retrieve Prix Unitaire from the database
                productList.add(new Product(id, name, reference, categorie, stock, prixUnitaire, stock * prixUnitaire));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    
    public static void updateProductStock(Product product) {
        String updateStockSQL = "UPDATE products SET stock = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStockSQL)) {
            preparedStatement.setInt(1, product.getStock());
            preparedStatement.setInt(2, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update product stock.");
        }
    }
    
    public static List<Categorie> getAllCategories() {
        List<Categorie> categorieList = new ArrayList<>();
        String selectAllCategoriesSQL = "SELECT * FROM categories"; // Replace "categories" with your actual table name.
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllCategoriesSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom"); // Replace with the actual column name.
                categorieList.add(new Categorie(id, nom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorieList;
    }
    
    
    public static List<Product> getProductsByStockCondition(String condition) {
        List<Product> productList = new ArrayList<>();
        String selectProductsByStockSQL = "SELECT id, name, reference, categorie, stock, prix_unitaire, inventaire FROM products WHERE stock " + condition;
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectProductsByStockSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String reference = resultSet.getString("reference");
                String categorie = resultSet.getString("categorie");
                int stock = resultSet.getInt("stock");
                int inventaire = resultSet.getInt("inventaire");
                int prix_unitaire = resultSet.getInt("prix_unitaire");

                Product product = new Product(id, name, reference, categorie, stock, prix_unitaire, inventaire);
                product.setInventaire(inventaire);
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    public static List<MP> getMPSByStockCondition(String condition) {
        List<MP> mpList = new ArrayList<>();
        String selectMPSByStockSQL = "SELECT id, name, reference, categorie, stock, categorie_id FROM mps WHERE stock " + condition;
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectMPSByStockSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String reference = resultSet.getString("reference");
                String categorie = resultSet.getString("categorie");
                int stock = resultSet.getInt("stock");
                int categoryId = resultSet.getInt("categorie_id");

                MP mp = new MP(id, name, reference, categorie, stock, categoryId);
                mpList.add(mp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mpList;
    }
    
    
    public static List<Categorie> getCategoriesByName(String query) {
        List<Categorie> filteredCategories = new ArrayList<>();
        String selectCategoriesByNameSQL = "SELECT * FROM categories WHERE nom LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCategoriesByNameSQL)) {
            preparedStatement.setString(1, "%" + query + "%"); // Use the % wildcard for a partial match
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                filteredCategories.add(new Categorie(id, nom));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredCategories;
    }

    
    public static void addCategory(Categorie category) {
        String insertCategorySQL = "INSERT INTO categories (nom) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertCategorySQL)) {
            preparedStatement.setString(1, category.getNom());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add category.");
        }
    }
    
    public static void addMP(MP mp) {
        String insertMPSQL = "INSERT INTO mps (name, reference, categorie, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertMPSQL)) {
            preparedStatement.setString(1, mp.getName());
            preparedStatement.setString(2, mp.getReference());
            preparedStatement.setString(3, mp.getCategorie());
            preparedStatement.setInt(4, mp.getStock());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add Matière Première.");
        }
    }
    
    public static List<MP> getAllMPs() {
        List<MP> mpList = new ArrayList<>();
        String selectAllMPSQL = "SELECT * FROM mps"; // Replace "mps" with your actual table name.

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllMPSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String reference = resultSet.getString("reference");
                String categorie = resultSet.getString("categorie");
                int stock = resultSet.getInt("stock");

                // Create an MP object and add it to the list
                MP mp = new MP(id, name, reference, categorie, stock);
                mpList.add(mp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mpList;
    }
    
    
    public static void updateMPStock(MP mp) {
        String updateStockSQL = "UPDATE mps SET stock = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateStockSQL)) {
            preparedStatement.setInt(1, mp.getStock());
            preparedStatement.setInt(2, mp.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update MP stock.");
        }
    }
    
    public static void deleteMP(MP mp) {
        String deleteMPSQL = "DELETE FROM mps WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteMPSQL)) {
            preparedStatement.setInt(1, mp.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete MP.");
        }
    }
    
    
    public static void updateMP(MP mp) {
        String updateMPSQL = "UPDATE mps SET name = ?, reference = ?, categorie = ?, stock = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateMPSQL)) {
            preparedStatement.setString(1, mp.getName());
            preparedStatement.setString(2, mp.getReference());
            preparedStatement.setString(3, mp.getCategorie());
            preparedStatement.setInt(4, mp.getStock());
            preparedStatement.setInt(5, mp.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update MP.");
        }
    }
    
    public static void deleteProduct(Product product) {
        String deleteProductSQL = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteProductSQL)) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete product.");
        }
    }
    
    public static void updateProduct(Product product) {
        String updateProductSQL = "UPDATE products SET name = ?, reference = ?, categorie = ?, stock = ?, prix_unitaire = ?, inventaire = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateProductSQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getReference());
            preparedStatement.setString(3, product.getCategorie());
            preparedStatement.setInt(4, product.getStock());
            preparedStatement.setDouble(5, product.getPrixUnitaire()); // Update Prix Unitaire
            preparedStatement.setDouble(6, product.getInventaire()); // Update Inventaire
            preparedStatement.setInt(7, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update product.");
        }
    }
    
    
    // Delete a category from the database.
    public static void deleteCategory(Categorie category) {
        String deleteCategorySQL = "DELETE FROM categories WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteCategorySQL)) {
            preparedStatement.setInt(1, category.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete category.");
        }
    }

   
    // Update an existing category in the database.
    public static void updateCategory(Categorie category) {
        String updateCategorySQL = "UPDATE categories SET nom = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateCategorySQL)) {
            preparedStatement.setString(1, category.getNom());
            preparedStatement.setInt(2, category.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update category.");
        }
    }
    
    
 // Find a category by its ID
    public static Categorie findCategoryById(int categoryId) {
    	System.out.println(categoryId);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Categorie category = null;

        try {
            String query = "SELECT * FROM categories WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, categoryId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                category = new Categorie(id, nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return category;
    }
    
    
    public static Categorie findCategoryByNom(String nom) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Categorie category = null;

        try {
            String query = "SELECT * FROM categories WHERE nom = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                category = new Categorie(id, nom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, statement, resultSet);
        }

        return category;
    }
    
    public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // ... (other methods)
}


    

    
