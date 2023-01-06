package Controllers;

import database.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class addMusicController implements Initializable {

    private Stage stage;
    private Parent root;
    private Scene scene;

    private String musicName;
    private String musicGenre;
    private String URL;

    private String adder;

    @FXML
    private TextField nameField;
    @FXML
    private TextField URLField;
    @FXML
    private ChoiceBox<String> genreField;

    private String[] genres = {"Rock", "Pop", "Jazz", "HipHop", "Fado"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreField.getItems().addAll(genres);
    }

    int check_name(String name){
        if(DataBase.checkMusic(name) < 1){
            return -2;
        }
        if(Objects.equals(name, "")){
            return -1;
        }
        if(name.length() > 50){
            return -3;
        }
        return 1;
    }

    int check_genre(String genre){
        if(genre == null) return -1;
        return 1;
    }

    int checkURL(String url){
        if((url == null) || (Objects.equals(url, ""))){
            return -1;
        }
        return 1;
    }


    public void addMusic(ActionEvent e) throws IOException {
        musicName = nameField.getText();
        musicGenre = genreField.getValue();
        URL = URLField.getText();
        adder = User.getName();

        if(check_name(musicName) < 1){
            set_alert_name();
            return;
        }

        if(check_genre(musicGenre) < 1){
            set_alert_genre();
            return;
        }

        if(checkURL(URL) < 1){
            set_alert_URL();
            return;
        }

        if(DataBase.addMusic(musicName, musicGenre, URL, adder) < 1){
            System.out.println("err");
        }else{
            set_alert_ok();
            nameField.clear();
            URLField.clear();
            genreField.getSelectionModel().clearSelection();
        }
    }


    public void home(ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profileMenu.fxml"));
        root = loader.load();

        profileMenuController profileController = loader.getController();
        profileController.display(User.getName());

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();

    }

    private void set_alert_name(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error adding Music");
        alert.setHeaderText("Music's name can't be empty, can't be too big and should be unique");
        alert.setContentText("Try a different name!");
        alert.showAndWait();
    }

    private void set_alert_genre(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error adding Music");
        alert.setHeaderText("Music's genre can't be empty");
        alert.setContentText("Please choose a genre from the ChoiceList!");
        alert.showAndWait();
    }

    private void set_alert_URL(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error adding Music");
        alert.setHeaderText("Music's URL can't be empty");
        alert.setContentText("Please tell us where can we find your music!");
        alert.showAndWait();
    }

    private void set_alert_ok(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Your music was added with Success!");
        alert.setHeaderText("Thanks for making DiscordandoMusic Bigger");
        alert.setContentText("Your music is now Available in Discordando Music 'All Songs' playlist");
        alert.showAndWait();
    }

}
