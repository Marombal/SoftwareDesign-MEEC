package com.example.discordandosb;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.discordandosb.DataBase.findMusicsFromPlaylist;


public class appMenuController implements Initializable {


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

    private static String Username;

    public String currentPlaylist;

    @FXML
    private ListView<String> playlistView;
    @FXML
    private ListView<String> musicslistView;

    String[] test = {"Musica1", "Musica2", "Musica3"};

    private static String[] playlistArray;
    private static String[] musicsArray;
    private int getPlaylists(String user){
        playlistArray = DataBase.findPlaylists(user);
        return 1;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        System.out.println(TESTE);
        int res = getPlaylists();
        if(res != 1){
            return;
        }
        playlistView.getItems().addAll(playlistArray);
        */

        playlistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                musicslistView.getItems().clear();
                currentPlaylist = playlistView.getSelectionModel().getSelectedItem();
                musicsArray = findMusicsFromPlaylist(currentPlaylist);
                for(int i = 0 ; i<255; i++){
                    if(musicsArray[i] != null){
                        musicslistView.getItems().add(musicsArray[i]);
                    }
                }
            }
        });
    }


    public void updatePlaylist(){

        int res = getPlaylists(Username);
        if(res != 1){
            return;
        }
        for(int i = 0 ; i<255; i++){
            if(playlistArray[i] != null){
                playlistView.getItems().add(playlistArray[i]);
            }
        }
    }


    public void display(String username){
        label1.setText(username);
        Username = username;
    }

    public static String logUser(){
        return Username;
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

    public void createPlaylist(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("createplaylistMenu2.fxml"));
        //root = FXMLLoader.load(getClass().getResource("createplaylistMenu2.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Create Playlist");
        stage.setScene(scene);
        stage.show();
    }



}
