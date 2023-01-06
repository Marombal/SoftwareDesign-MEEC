package Controllers;

import database.DataBase;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.ResourceBundle;

import music.Music;
import playlist.Playlist;
import user.User;
import user.User.*;

import static database.DataBase.findMusicsFromPlaylist;
import static database.DataBase.getAllMusic;


public class appMenuController implements Initializable {

    private User userObject = new User();
    private Stage stage;
    private Parent root;
    private Scene scene;
    private volatile boolean stop = false;

    private static final int MAXPLAYLISTS = 255;
    musicMenuController nSong = new musicMenuController();


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

    private static String[] playlistArray;
    private static String[] musicsArray;
    public static int indexApp = 0;
    public static int playlistIndex = 0;
    Playlist[] playlists = new Playlist[MAXPLAYLISTS];    // new architecture
    Playlist AllSongs;
    private void getPlaylists(String user){
        playlistArray = DataBase.findPlaylists(user);

        // get all songs
        AllSongs = new Playlist("all");
        playlists[0] = AllSongs;

        // new architecture

        /*while(true){
            assert playlistArray != null;
            System.out.println(playlistArray[i-1]);
            if (playlistArray[i-1] == null) break;
            playlists[i] = new Playlist(playlistArray[i-1], user);
            i++;
        }*/
        int i = 1;
        while(playlistArray[i-1] != null){
            //System.out.println(playlistArray[i-1]);
            playlists[i] = new Playlist(playlistArray[i-1], user);
            i++;
        }

        userObject.setNumberOfPlaylists(i);
    }

    public int whichMusicApp(){
        System.out.println("indexApp " + indexApp);
        return indexApp;
    }
    public int whichPlaylistApp(){
        System.out.println("indexPlaylsit " + playlistIndex);
        return playlistIndex;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
         *               Sempre que a janela appMenu abre existem uma série de funcionalidades que devem ser executadas.
         * 1) A label1 deve ser atualizada com o nome do user que está logado
         * 2) a lista de playlists deve ser atualizada
         * 3) as list views devem ser configuradas para reagir a clicks nas respetivas variaveis
         *
         * */

        label1.setText(User.getName());

        playlistView.getItems().clear();
        getPlaylists(User.getName());

        //playlistView.getItems().add(AllSongs.getName());
        // new architecture
        int i = 0;
        while(playlists[i] != null){
            //System.out.println("- " + playlists[i].getName());
            playlistView.getItems().add(playlists[i].getName());
            i++;
        }

        playlistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                musicslistView.getItems().clear();

                int index = playlistView.getSelectionModel().getSelectedIndex();
                playlistIndex = index;
                int nsongs = playlists[index].getnSongs();
                Music[] musics = playlists[index].getMusics();

                System.out.println("Index: " + index + " name " + playlists[index].getName() +  " nsongs " + nsongs);

                for(int i = 0; i < nsongs; i++){
                    if(musics[i].getName() != null){
                        musicslistView.getItems().add(musics[i].getName());
                    }
                }
            }
        });

        musicslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1){

                indexApp = musicslistView.getSelectionModel().getSelectedIndex();
                System.out.println("indexApp dps " + indexApp);



                /*FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("musicMenu.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = new Stage();
                stage.setTitle("Music Controller");
                stage.setScene(scene);
                stage.show();*/

            }
        });

        System.out.println("NQ-> " + DataBase.numberOfDataBaseQuerys);
    }

    public void GO(ActionEvent e) throws IOException{

        if(indexApp < 0){
            indexApp = 0;
        }

        root = FXMLLoader.load(getClass().getResource("musicMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Music Controller");
        stage.setScene(scene);
        stage.show();
    }



    public void updatePlaylist(){

        playlistView.getItems().clear();
        getPlaylists(Username);

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

        root = FXMLLoader.load(getClass().getResource("searchMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Search");
        stage.setScene(scene);
        stage.show();

    }

    public void historic(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("historicMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Historic");
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

    public void FAQ(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("FAQmenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("FAQ");
        stage.setScene(scene);
        stage.show();
    }


    public void addToPlaylist(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("addToPlaylist.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Add Music to Playlist");
        stage.setScene(scene);
        stage.show();
    }

    public void set_alert_CantRemove(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Cannot remove music");
        alert.setContentText("Sorry, you can't remove musics from the 'All Musics' playlist.");
        alert.showAndWait();
    }

    public void removeMusicFromPlaylist(ActionEvent e) throws IOException{

            int indice = indexApp;
            playlistIndex = playlistView.getSelectionModel().getSelectedIndex();
            //System.out.println(playlistIndex);

            String BanMusic = (String) musicslistView.getItems().get(indice);
            String playlist = (String) playlistView.getItems().get(playlistIndex);
            String user = User.getName();

            System.out.println("User " + user + "Playlist " + playlist);

            if(playlist.equals("All Musics")){
                set_alert_CantRemove();
                return;
            }
            else {

                int musicID = DataBase.getMusicIDfromName(BanMusic);
                int playlistsIDs = DataBase.getPlaylistID(playlist, user);

                DataBase.removeAttribution(musicID, playlistsIDs);

                int nsongs = DataBase.getNumberSongs2(playlistsIDs);
                nsongs--;
                DataBase.updatenSgons(playlistsIDs, nsongs);
            }

            playlists[playlistIndex].updatePlaylist();

            musicslistView.getItems().clear();

            int nsongs = playlists[playlistIndex].getnSongs();
            Music[] musics = playlists[playlistIndex].getMusics();

            System.out.println("Index: " + playlistIndex + " name " + playlists[playlistIndex].getName() +  " nsongs " + nsongs);
            for(int i = 0; i < nsongs; i++){
                if(musics[i].getName() != null){
                    musicslistView.getItems().add(musics[i].getName());
                }
            }
    }

}
