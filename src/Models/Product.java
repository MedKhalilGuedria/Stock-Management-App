package Models;

public class Product {
    private int id;
    private String name;
    private String reference;
    private String categorie;
    private int stock;
    private int categorieId;
    private double prixUnitaire;
    private double inventaire;

    public Product(int id, String name, String reference, String categorie, int stock, int categorieId, double prixUnitaire, double inventaire) {
        this.setId(id);
        this.setName(name);
        this.setReference(reference);
        this.setCategorie(categorie);
        this.setStock(stock);
        this.setCategorieId(categorieId);
        this.setPrixUnitaire(prixUnitaire);
        this.setInventaire(inventaire);
    }

    public Product(int id, String name, String reference, String categorie, int stock, double prixUnitaire, double inventaire) {
        this.setId(id);
        this.setName(name);
        this.setReference(reference);
        this.setCategorie(categorie);
        this.setStock(stock);
        this.setPrixUnitaire(prixUnitaire);
        this.setInventaire(inventaire);
    }

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

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getInventaire() {
        return inventaire;
    }

    public void setInventaire(double inventaire) {
        this.inventaire = inventaire;
    }
}