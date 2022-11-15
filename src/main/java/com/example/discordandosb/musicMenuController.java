package com.example.discordandosb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class musicMenuController implements Initializable{

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    ComboBox searchCombo;
    @FXML
    TextField searchText;
    @FXML
    Label label1;
    @FXML
    Button playButton, pauseButton, resetButton;
    @FXML
    Label musicLabel;
    @FXML
    Slider volumeSlider;
    @FXML
    ProgressBar progressBar;

    private File directory;
    private File[] files;
    private ArrayList<File> songs;

    private int songNumber;
    private Timer timer;
    private TimerTask taks;
    private boolean running;

    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        songs = new ArrayList<File>();

        directory = new File("music");

        files = directory.listFiles();
        if(files != null){
            for(File file : files){
                songs.add(file);
                System.out.println(file);
            }
        }
/*
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        musicLabel.setText(songs.get(songNumber).getName());

 */
    }
    public void signOut(ActionEvent e) throws IOException {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    public void search(ActionEvent e) throws IOException{

        searchCombo.setOpacity(1);
        searchText.setOpacity(1);
    }

    public void profile(ActionEvent e) throws IOException{

        String user = label1.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profileMenu.fxml"));
        root = loader.load();

        profileMenuController profileController = loader.getController();
        profileController.display(user);

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }

    public void play(ActionEvent e) throws IOException{

    }
    public void pause(ActionEvent e) throws IOException{

    }
    public void reset(ActionEvent e) throws IOException{

    }
    public void beginTimer(){

    }
    public void CancelTimer(){

    }
}
