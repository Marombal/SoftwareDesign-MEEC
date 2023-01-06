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
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import music.Music;
import playlist.Playlist;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addToPlaylistController implements Initializable {
    private User userObject = new User();
    private Stage stage;
    private Parent root;
    private Scene scene;

    private static String[] playlistArray;
    Playlist[] playlists = new Playlist[255];
    Playlist AllSongs;


    @FXML
    private ChoiceBox<String> playlistBox;
    @FXML
    private ChoiceBox<String> musicBox;
    @FXML
    private RadioButton liked;

    private int justLiked = 0;

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

    private void getPlaylists(String user){
        playlistArray = DataBase.findPlaylists(user);

        // get all songs
        AllSongs = new Playlist("all");
        playlists[0] = AllSongs;

        // new architecture
        int i = 1;
        while(playlistArray[i-1] != null){
            //System.out.println(playlistArray[i-1]);
            playlists[i] = new Playlist(playlistArray[i-1], user);
            playlistBox.getItems().add(playlists[i].getName());
            i++;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verifica se foi aberto pelo Search
        String m = searchMenuController.MUSIC;
        if(m != null){
            searchMenuController.MUSIC = null;
            musicBox.getSelectionModel().select(m);
        }

        getPlaylists(User.getName());

        Music[] musics = playlists[0].getMusics();
        for(int i = 0; i < playlists[0].getnSongs(); i++){
            musicBox.getItems().add(musics[i].getName());
        }
    }

    public void add(ActionEvent e) throws IOException{
        String playlistName = playlistBox.getValue();
        String musicName = musicBox.getValue();

        if(playlistName == null || musicName == null){
            alert_Fail();
            return;
        }

        System.out.println(musicName);
        int pID = DataBase.getPlaylistID(playlistName, User.getName());
        int mID = DataBase.getMusicIDfromName(musicName);

        System.out.println(pID + ">>>>" + mID);

        if(pID <= 0 || mID <= 0){
            return;
        }

        int res = DataBase.addMusicToPlaylist(mID, pID);
        if(res > 0 ){
            int nsongs = DataBase.getNumberSongs(playlistName, User.getName());
            nsongs++;
            DataBase.updatenSgons(pID, nsongs);
            System.out.println("Add");
        }
        else{
            System.out.println("Error");
        }


        playlistBox.getSelectionModel().clearSelection();
        musicBox.getSelectionModel().clearSelection();
    }

    public void radioButtonLike(ActionEvent e) throws IOException{
        if(liked.isSelected()){
            justLiked = 1;

            int[] musicIDs = DataBase.getAllLikes(User.getID()); //1.

            musicBox.getItems().clear();

            int i = 0;
            while(true){
                assert musicIDs != null;
                if (!(musicIDs[i] > 0)) break;
                musicBox.getItems().add(DataBase.getMusicNameFromID(musicIDs[i])); //2 e 4
                i++;
            }
        }
        else {
            justLiked = 0;
            getPlaylists(User.getName());

            Music[] musics = playlists[0].getMusics();
            for(int i = 0; i < playlists[0].getnSongs(); i++){
                musicBox.getItems().add(musics[i].getName());
            }
        }

        System.out.println(justLiked);

    }

    private void alert_Fail(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Music To Playlist Failed");
        alert.setHeaderText("Empty Fields");
        alert.setContentText("Choose the music and the playlist where you want add the song");
        alert.showAndWait();
    }
}
