package com.example.discordandosb;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;



public class appMenuController {


    private Stage stage;
    private Parent root;
    private Scene scene;
    private volatile boolean stop = false;

    @FXML
    Label label1;
    @FXML
    Label timeLabel;
    @FXML
    ComboBox searchCombo;
    @FXML
    TextField searchText;

    public void display(String username){
        label1.setText(username);
    }


    public void signOut(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));

        stop = true;

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    public void profile(ActionEvent e) throws IOException{

        String user = label1.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profileMenu.fxml"));
        root = loader.load();

        profileMenuController profileController = loader.getController();
        profileController.display(user);

        stop = true;

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }

    public void clock(){
        Thread thread = new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            while(!stop){
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String clockNow = sdf.format(new Date());
                Platform.runLater(() ->{
                    timeLabel.setText(clockNow);
                });
            }
        });
        thread.start();
    }

    public void search(ActionEvent e) throws IOException{

        searchCombo.setOpacity(1);
        searchText.setOpacity(1);

    }

    public void musicMenu(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("musicMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Music");
        stage.setScene(scene);
        stage.show();


    }


}
