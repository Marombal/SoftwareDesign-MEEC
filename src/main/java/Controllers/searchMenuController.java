package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import database.DataBase;
import user.User;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class searchMenuController implements Initializable {

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private ListView<String> searchlistView;

    @FXML
    private ChoiceBox<String> genreField;
    @FXML
    private ChoiceBox<String> ownerField;
    @FXML
    ImageView likeImageON, likeImageOFF;

    private String[] genres = {null ,"Rock", "Pop", "Jazz", "HipHop", "Fado"};

    private static String[] ownersArray;

    public static String MUSIC = null;



    private void set_alert_like(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Liked this Songs");
        alert.setHeaderText("Your Like was successfully registered");
        alert.setContentText("You can check your liked musics on your ProfileMenu!");
        alert.showAndWait();
    }
    private void set_alert_alreadylikes(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Already Liked this Songs");
        alert.setHeaderText("Your Like was already registered");
        alert.setContentText("You can check your liked musics on your ProfileMenu!");
        alert.showAndWait();
    }


    private String[] getOwners(){
        ownersArray = DataBase.getAllAdders();

        // Create a Set to store the unique elements
        Set<String> set = new HashSet<>();

        // Iterate over the array and add each element to the Set
        for(String s : ownersArray){
            if(!Objects.equals(s, "") && s != null){
                set.add(s);
            }
        }

        // Create a new array with the size of the Set
        String[] uniqueOwners = new String[set.size()+1];

        // Iterate over the Set and copy the elements to the new array
        int i = 0;
        for (String s : set) {
            uniqueOwners[i++] = s;
        }
        uniqueOwners[i] = null;
        return uniqueOwners;
    }

    public void searchlist(ActionEvent e)throws Exception{
        String ownersName = ownerField.getValue();
        String genderName = genreField.getValue();
        searchlistView.getItems().clear();

        if(Objects.equals(ownersName, null) && !Objects.equals(genderName,null)){
            //Procurar por owner sem gender
            System.out.println("1");
            String[] Musics1 = DataBase.searchbygender(genderName);

            int i = 0;
            while(true){
                assert Musics1 != null;
                if (Musics1[i] == null) break;
                searchlistView.getItems().add(Musics1[i++]);
            }

        } else if (!Objects.equals(ownersName, null) && Objects.equals(genderName,null)) {
            //Procurar por gender sem owner
            System.out.println("2");
            String[] Musics2 = DataBase.searchbyowner(ownersName);

            int i = 0;
            while(true){
                assert Musics2 != null;
                if (Musics2[i] == null) break;
                searchlistView.getItems().add(Musics2[i++]);
            }

        } else if (!Objects.equals(ownersName, null) && !Objects.equals(genderName,null)) {
            //Procurar por gender e owner
            System.out.println("3");
            String[] Musics3 = DataBase.searchbyownerandgender(ownersName, genderName);

            int i = 0;
            while(true){
                assert Musics3 != null;
                if (Musics3[i] == null) break;
                searchlistView.getItems().add(Musics3[i++]);
            }

        }else{
            //Todas as musicas
            System.out.println("4");
            String[] Musics = DataBase.getAllMusic();

            int i = 0;
            while(true){
                assert Musics != null;
                if (Musics[i] == null) break;
                searchlistView.getItems().add(Musics[i++]);
            }

        }

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

    public void likeMusic(ActionEvent e) throws IOException{
        String music = searchlistView.getSelectionModel().getSelectedItem();
        if(Objects.equals(music, "") || music == null){
            return;
        }

        int musicID = DataBase.getMusicIDfromName(music);

        // check if it is already likes, if yes just return without doing nothing
        int res = DataBase.alreadylike(musicID, User.getID());
        if(res == 1){
            System.out.println("Already like");
            set_alert_alreadylikes();
            return;
        }else {
            //adding the like
            DataBase.addLike(musicID, User.getID());
            likeImageON.setOpacity(1);
            likeImageOFF.setOpacity(0);
            set_alert_like();
            System.out.println("Add Like");
        }

    }

    public void addToPlaylist(ActionEvent e) throws IOException{

        String music = searchlistView.getSelectionModel().getSelectedItem();
        if(Objects.equals(music, "") || music == null){
            return;
        }

        MUSIC = music;
        
        root = FXMLLoader.load(getClass().getResource("addToPlaylist.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Add Music to Playlist");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreField.getItems().addAll(genres);
        ownerField.getItems().addAll(getOwners());
    }

    public void historic(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("historicMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Historic");
        stage.setScene(scene);
        stage.show();

    }
}
