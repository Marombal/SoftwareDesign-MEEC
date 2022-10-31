module com.example.discordandosb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.discordandosb to javafx.fxml;
    exports com.example.discordandosb;
}