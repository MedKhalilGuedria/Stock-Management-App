package Models;

public class MP {
    private int id;
    private String name;
    private String reference;
    private String categorie;
    private int stock;
    private int categoryId; // New property

    public MP(int id, String name, String reference, String categorie, int stock, int categoryId) {
        this.setId(id);
        this.setName(name);
        this.setReference(reference);
        this.setCategorie(categorie);
        this.setStock(stock);
        this.setCategoryId(categoryId); // Set the new property
    }

    // First constructor with categoryId

    public MP(int id, String name, String reference, String categorie, int stock) {
        this.setId(id);
        this.setName(name);
        this.setReference(reference);
        this.setCategorie(categorie);
        this.setStock(stock);
        // You can leave categoryId unset in this constructor
    }

    // Other constructors (if needed)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // Other getters and setters (if needed)
}
