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
import jdk.internal.icu.impl.Norm2AllModes;

import javax.sound.sampled.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static java.lang.String.valueOf;

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

    private int songNumber = 0;
    private Timer timer;
    private TimerTask taks;
    private boolean running;

    private Media media;
    private MediaPlayer mediaPlayer;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        songs = new ArrayList<File>();
        directory = new File("C:\\Users\\Ricardo\\Desktop\\music");

        //String home = System.getProperty("user.home");
        //System.out.println(home);
        //directory = new File("/Users/miguelmarombal/Desktop/music.wav");
        //new File(FileNameUtils.normalize(home + "/Desktop/Testing/Java.txt"));
        //directory = new File(home + File.separator + "Desktop" + File.separator + "teste");


        files = directory.listFiles();
        if(files == null){
            System.out.println("nullu");
        }
        if(files != null){
            //System.out.println("abri");
            for(File file : files){
                songs.add(file);
                System.out.println(file);
            }
            musicLabel.setText(songs.get(songNumber).getName());
        }

        /*try {
            System.out.println(songs.get(songNumber).toURI().toURL().getPath());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }*/
        /*media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);*/



        //musicLabel.setText(songs.get(songNumber).getName());
    }
    public void signOut(ActionEvent e) throws IOException {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    public void search(){

        searchCombo.setOpacity(1);
        searchText.setOpacity(1);
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

    public void play(){
        try {
            //AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File("C:\\Users\\Ricardo\\Desktop\\music\\Its_Dark_Its_Cold_It_Winter.wav"));
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(songs.get(songNumber).toURI().toURL().getPath()));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        //mediaPlayer.play();
    }
    public void pause(){
        //mediaPlayer.play();
    }
    public void reset(){

    }
    public void beginTimer(){

    }
    public void CancelTimer(){

    }
}
