package com.example.discordandosb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;

import java.io.IOException;

public class profileMenuController {

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    Label labelChange;
    @FXML
    Label usernameLabel;
    @FXML
    CubicCurve shapeChange;
    @FXML
    ImageView profilePicture;


    public void display(String username){

        usernameLabel.setText(username);
    }
    public void displayLabelChange(){

        labelChange.setOpacity(1);
    }
    public void notDisplayLabelChange(){

        labelChange.setOpacity(0);
    }


    public void signOut(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    public void home(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("applicationmenu.fxml"));
        root = loader.load();

        appMenuController appController = loader.getController();
        appController.clock();

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("App Menu");
        stage.setScene(scene);
        stage.show();
    }


}
