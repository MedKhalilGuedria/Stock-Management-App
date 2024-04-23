package Models;



public class Categorie {
    private int id;
    private String nom;

    public Categorie(int id, String nom) {
        this.setId(id);
        this.setNom(nom);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    

    // Ajoutez d'autres méthodes si nécessaire, par exemple, toString() pour l'affichage.
}

