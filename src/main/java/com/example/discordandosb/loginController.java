package com.example.discordandosb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class loginController {

    private Stage stage;
    private Parent root;
    private Scene scene;


    @FXML
    CheckBox passToggle;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label loginAnswer;
    @FXML
    Label passVisible;
    @FXML
    Button exitButton;
    @FXML
    AnchorPane loginPane;
    @FXML
    ImageView imageLogin;
    @FXML
    RadioButton sPass;


    private String pass_aux;
    @FXML
    public void showPassword(ActionEvent e){
        if(sPass.isSelected()){
            pass_aux = password.getText();
            password.clear();
            password.setPromptText(pass_aux);
        }else {
            password.setPromptText("");
            password.setText(pass_aux);
        }


    }

    public void LoginToApplication(ActionEvent e) throws IOException {

        String user = username.getText();
        String pass = password.getText();

        if(Objects.equals(user, "admin") && Objects.equals(pass, "admin") ){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("applicationmenu.fxml"));
            root = loader.load();

            appMenuController appController = loader.getController();
            appController.display(user);

            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Application");
            stage.setScene(scene);
            stage.show();
        }else{
            loginAnswer.setText("Wrong Credentials");
        }

    }

    public void exit(ActionEvent e){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Discordando");
        alert.setHeaderText("EXIT DiscordandoMusic");
        alert.setContentText("Do you want to exis DiscordandoMusic?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) loginPane.getScene().getWindow();
            System.out.println("EXIT");
            stage.close();
        }
    }

    public void Register(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("registerMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
    }
}