module com.example.alik {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.alik to javafx.fxml;
    exports com.example.alik;
    exports com.example.alik.database;
    opens com.example.alik.database to javafx.fxml;
    exports com.example.alik.controllers;
    opens com.example.alik.controllers to javafx.fxml;
}