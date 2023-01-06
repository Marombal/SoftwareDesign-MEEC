package Controllers;

import database.DataBase;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import music.Music;
import playlist.Playlist;
import user.User;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.*;

import static java.lang.String.valueOf;

public class musicMenuController implements Initializable{
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    Label label1, artistLabel, albumLabel;
    @FXML
    Button playButton, pauseButton, resetButton;
    @FXML
    Label musicLabel;
    @FXML
    Slider volumeSlider;
    @FXML
    ProgressBar progressBar;
    @FXML
    ImageView likeImageON, likeImageOFF;
    @FXML
    private  ListView<String> listOfSongs;
    @FXML
    Hyperlink hyperlink;

    private File directory;
    private File[] files;
    private ArrayList<File> songs;


    private int existe = 0;
    private static int songNumber = 0;
    private int nSong;
    private int play = 0, pause = 0;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    private AudioInputStream audioInput;
    private Clip clip;
    private long clipTimePosition = 0;

    private String url;
    private int c = 1;
    Music music = new Music(c);
    public static int index = 0;
    public static int playlistIndex = 0;
    public static String[] musicasTodas;
    Playlist[] playlists = new Playlist[255];
    private static String[] playlistArray;
    private User userObject = new User();

    private void getPlaylists(String user){
        playlistArray = DataBase.findPlaylists(user);

        // new architecture
        Playlist AllSongs = new Playlist("all");
        playlists[0] = AllSongs;
        // new architecture
        int i = 1;
        while(playlistArray[i-1] != null){
            //System.out.println(playlistArray[i-1]);
            playlists[i] = new Playlist(playlistArray[i-1], user);
            i++;
        }



        userObject.setNumberOfPlaylists(i);
    }


    public String whichMusic(){
        System.out.println(index);
        return musicasTodas[index];
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        appMenuController indexApp = new appMenuController();
        appMenuController indexPlaylist = new appMenuController();
        getPlaylists(User.getName());
        musicasTodas = DataBase.getAllMusic();

            for(int m = 0; m < 20; m++){
                musicasTodas[m] = null;
            }



        System.out.println("index " + index);

        label1.setText(User.getName());

        songs = new ArrayList<File>();

        //definicao do diretorio das musicas
        String dir = System.getProperty("user.dir");
        String path = dir + File.separator + "music";
        directory = new File(path);


        files = directory.listFiles();
        if(files == null){
            System.out.println("null");
        }
        if(files != null){
            //System.out.println("abri");
            for(File file : files){
                songs.add(file);
                System.out.println(file);
            }

            playlistIndex = indexPlaylist.whichPlaylistApp();
            int nsongs = playlists[playlistIndex].getnSongs();
            Music[] musics = playlists[playlistIndex].getMusics();

            System.out.println("playlistIndex = " + playlistIndex + "nSongs = " + nsongs);
            for(int z = 0; z < nsongs; z++){
                System.out.println("musics" + musics[z].getName());
            }


            if(playlistIndex == 0){
                musicasTodas = DataBase.getAllMusic();
            }
            else{
                for(int i = 0; i < nsongs; i++){
                    if(musics[i].getName() != null){
                        musicasTodas[i] = musics[i].getName();
                    }
                }
            }

            listOfSongs.getItems().clear();

            int i = 0;
            while(true){
                assert musicasTodas != null;
                if (musicasTodas[i] == null) {
                    break;
                }
                listOfSongs.getItems().add(musicasTodas[i++]);
            }

            index = indexApp.whichMusicApp();
            existe = doesMusicExist();


            if(existe == 1){
                String nomeMusica = songs.get(songNumber).getName();
                String[] nomeM = nomeMusica.split("-");

                musicLabel.setText(nomeM[1]);
                artistLabel.setText((nomeM[0]));
            }
            else{
                musicLabel.setText(musicasTodas[index]);
            }

            //Verificar se musica ja tem like:
            likeVerify();





            listOfSongs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    index = listOfSongs.getSelectionModel().getSelectedIndex();


                    existe = doesMusicExist();

                    //Atualizaçao do nome das labels
                    if(existe == 1){
                        String nomeMusica = songs.get(songNumber).getName();
                        String[] nomeM = nomeMusica.split("-");

                        musicLabel.setText(nomeM[1]);
                        artistLabel.setText((nomeM[0]));
                    }
                    else{
                        musicLabel.setText(musicasTodas[index]);
                    }
                    //Verificar se musica ja tem like:
                    likeVerify();


                    if(clip != null){
                        clip.stop();
                    }

                    if(running){
                        cancelTimer();
                    }

                    play = 0;
                }
            });
        }



        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println(volumeSlider.getValue());
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue((float) -volumeSlider.getValue());
                System.out.println(listOfSongs.getSelectionModel().getSelectionMode());
            }
        });
    }

    public int doesMusicExist(){
        //ver se existe a musica ou nao
        String[] musicasExist = new String[songs.size()];
        for(int i = 0; i < songs.size(); i++){
            musicasExist[i] = songs.get(i).getName();
            if(Objects.equals(musicasTodas[index], musicasExist[i])){
                songNumber = i;
                existe = 1;
                break;
            }
            else{
                songNumber = -1;
                existe = 0;
            }
        }

        //se nao existir da uma alerta
        if(existe == 0){
            music = new Music(musicasTodas[index]);
            url = music.getLink();
            System.out.println(url);
            System.out.println("ERROR");
            hyperlink.setText(musicasTodas[index]);
            set_alert_MissingMusic();
        }
        return existe;
    }

    public void set_alert_MissingMusic(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Music is missing");
        alert.setContentText("This music is not in your system " + "Please try the link on the left");
        alert.showAndWait();
    }

    public void hyperlink(ActionEvent e) throws IOException{
        Desktop desktop = Desktop.getDesktop();
        System.out.println(url);
        desktop.browse(java.net.URI.create(url));
    }




    public void play() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        if(play == 0){

            beginTimer();
            audioInput = AudioSystem.getAudioInputStream(new File(songs.get(songNumber).toURI().toURL().getPath()));
            //System.out.println("play" + songNumber);
            clip = AudioSystem.getClip();
            clip.open(audioInput);

            User.updateHistoric(songs.get(songNumber).getName());
            //System.out.println(User.getHistoric());

            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
            play = 1;
            System.out.println("Play");
        }


    }
    public void pause(){

        if(play == 1) {
            clipTimePosition = clip.getMicrosecondPosition();
            cancelTimer();
            clip.stop();
            System.out.println("Pause");
            play = 0;
        }
    }
    public void reset(){

        clipTimePosition = 0;
        progressBar.setProgress(0);
        clip.setMicrosecondPosition(clipTimePosition);
    }
    public void next() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        System.out.println(songs.size());

        if(songNumber != songs.size() - 1){
            songNumber++;
        }
        else if(songNumber == songs.size()-1){
            songNumber = 0;
        }
        musicLabel.setText(songs.get(songNumber).getName());
        clip.stop();

        if(running){
            cancelTimer();
        }

        play = 0;
        play();
    }
    public void previous() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        if(songNumber != 0){
            songNumber--;
        }
        else if(songNumber == 0){
            songNumber = songs.size()-1;
        }
        musicLabel.setText(songs.get(songNumber).getName());
        clip.stop();

        if(running){
            cancelTimer();
        }

        play = 0;
        play();
    }
    public void beginTimer(){

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double currentTime = clip.getMicrosecondPosition();
                double endTime = clip.getMicrosecondLength();

                progressBar.setProgress(currentTime/endTime);

                if(currentTime / endTime == 1){
                    cancelTimer();
                }
            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000);


    }
    public void cancelTimer(){

        running = false;
        timer.cancel();
    }

    public void comments(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("commentsMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Comments");
        stage.setScene(scene);
        stage.show();
        pause();

    }
    public void likeVerify(){
        //Verificar se já tem like:
        /*if(existe == 0){
            likeImageON.setOpacity(0);
            likeImageOFF.setOpacity(1);
            return;
        }*/
        int idMusic = DataBase.getMusicIDfromName(musicasTodas[index]);
        int like; // like = -1 means not liked yet, = 1 already like
        like = DataBase.alreadylike(idMusic, User.getID());
        if(like == 1) {
            System.out.println("Ja ta like");
            likeImageON.setOpacity(1);
            likeImageOFF.setOpacity(0);
        }
        if(like == -1) {
            System.out.println("nao ta like");
            likeImageON.setOpacity(0);
            likeImageOFF.setOpacity(1);
        }
    }
    public void like(ActionEvent e) throws IOException{
        int idMusic = DataBase.getMusicIDfromName(musicasTodas[index]);

        int like; // like = -1 means not liked yet, = 1 already like
        like = DataBase.alreadylike(idMusic, User.getID());
        //System.out.println( songs.get(songNumber).getName() + "-" + User.getID() + "-" + like);

        if(like == -1){
            //add like

            System.out.println("Add LIKE");
            likeImageON.setOpacity(1);
            likeImageOFF.setOpacity(0);

            DataBase.addLike(idMusic, User.getID());
        }
        else if(like == 1){
            //remove like
            System.out.println("Remove LIKE");
            likeImageON.setOpacity(0);
            likeImageOFF.setOpacity(1);

            DataBase.removeLike(idMusic, User.getID());

        }

        //VisualFeedback
    }


    public void signOut(ActionEvent e) throws IOException {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
        pause();
    }

    public void search(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("searchMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Search");
        stage.setScene(scene);
        stage.show();
        pause();

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
        pause();
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
        pause();
    }

    public void historic(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("historicMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Historic");
        stage.setScene(scene);
        stage.show();
        pause();

    }

    public void createPlaylist(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("createplaylistMenu2.fxml"));
        //root = FXMLLoader.load(getClass().getResource("createplaylistMenu2.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Create Playlist");
        stage.setScene(scene);
        stage.show();
        pause();
    }


}
