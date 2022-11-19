package com.example.discordandosb;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        String dirName = "musics";
        Path path = Paths.get(dirName);
        if(Files.notExists(path)){
            try{
                Files.createDirectory(path);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });
    }

    public void exit(Stage stage){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Discordando");
        alert.setHeaderText("EXIT DiscordandoMusic");
        alert.setContentText("Do you want to exis DiscordandoMusic?");

        if(alert.showAndWait().get() == ButtonType.OK){
            //System.out.println("EXIT");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}