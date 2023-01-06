package Controllers;

import database.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

import javafx.scene.Node;
import user.User;

public class createPlaylistController2 {

    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    AnchorPane loginPane;
    @FXML
    TextField nameField;

    private String Name;

    private int getPlaylistName(){
        Name = nameField.getText();
        if(Objects.equals(Name, "")){
            return -1;
        }
        return 1;
    }

    private void alert_Sucess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Playlist Success");
        alert.setHeaderText("Playlist created!");
        alert.setContentText("You now have a new playlist!");
        alert.showAndWait();
    }

    private void alert_Fail(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Playlist Failed");
        alert.setHeaderText("Error");
        alert.setContentText("The playlist could not be created");
        alert.showAndWait();
    }

    private void alert_Name(int type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Create Playlist Failed");
        alert.setHeaderText("Error");
        if(type == 1){
            alert.setContentText("Name can't be empty");
        } else if (type == 2) {
            alert.setContentText("Name can't have more then 20 characters");
        }
        else{
            alert.setContentText("Error in Name");
        }

        alert.showAndWait();
    }

    public void createPlaylist(ActionEvent e){

        int res = getPlaylistName();
        if(res == -1){
            alert_Name(1);
            return;
        }

        if(Name.length() > 20){
            alert_Name(2);
            return;
        }

        //String creator = appMenuController.logUser();
        String creator = User.getName();
        res = DataBase.createPlaylist(Name, creator);

        if(res == 0){
            alert_Fail();
        }else{
            alert_Sucess();
            nameField.clear();
        }
    }


    public void returnToApplication(ActionEvent e) throws IOException {
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
