module ZA_Home_Management {
    requires javafx.controls;
    requires java.sql;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base; // Add this line to require javafx.base

    opens Models to javafx.base;
    opens application to javafx.graphics, javafx.fxml;
    opens Controllers to javafx.fxml; // Open the Controllers package
    exports application;
}
