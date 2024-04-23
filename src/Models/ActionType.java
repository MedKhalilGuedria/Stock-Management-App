package Models;

public enum ActionType {
    AJOUTER("Ajouter"),
    RETIRER("Retirer");

    private final String displayName;

    ActionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}