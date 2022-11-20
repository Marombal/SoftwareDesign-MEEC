module com.example.discordandosb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires java.sql;
    requires java.mail;


    opens com.example.discordandosb to javafx.fxml;
    exports com.example.discordandosb;
}