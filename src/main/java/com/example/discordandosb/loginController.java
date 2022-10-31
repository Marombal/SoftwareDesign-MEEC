package com.example.discordandosb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    public void showPassword(ActionEvent e){

    }

    public void LoginToApplication(ActionEvent e) throws IOException {

        String user = username.getText();
        String pass = password.getText();

        if(Objects.equals(user, "admin") && Objects.equals(pass, "admin") ){
            root = FXMLLoader.load(getClass().getResource("applicationmenu.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Application");
            stage.setScene(scene);
            stage.show();
        }
        else{
            loginAnswer.setText("Wrong Credentials");
        }

    }
    public void ApplicationToLogin(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }
}