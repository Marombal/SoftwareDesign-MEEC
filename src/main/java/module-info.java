module com.example.discordandosb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires java.mail;
    //requires org.junit.jupiter.api;

    requires javafx.graphics;

    opens Controllers to javafx.fxml;
    exports Controllers;
    exports database;
    opens database to javafx.fxml;
}