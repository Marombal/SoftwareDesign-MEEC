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
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import music.Music;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class profileMenuController implements Initializable {

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    Label labelChange;
    @FXML
    Label usernameLabel;
    @FXML
    CubicCurve shapeChange;
    @FXML
    ImageView profilePicture;

    @FXML
    ImageView trash;

    @FXML
    private ListView<String> likedMusics;
    @FXML
    private ListView<String> ownMusics;
    @FXML
    private Button Remove;

    private int checkCurrentStatus(String name){
        return DataBase.checkStatus(name);
    }
    private boolean check_Request(String name) {
        return DataBase.checkCCRequest(name) == 1;
    }


    private void set_alert_Not_CC(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Permission Denied!");
        alert.setHeaderText("Permission Denied!");
        alert.setContentText("Your account has no permission to upload new musics");
        alert.showAndWait();
    }
    private void set_alert_Already_With_Status(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Status already given");
        alert.setHeaderText("Your account already has Content Creator Status");
        alert.setContentText("You already can upload your musics to DiscordandoMusic DataBase.");
        alert.showAndWait();
    }

    private void set_alert_Not_Admin(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Permission Denied!");
        alert.setHeaderText("Permission Denied!");
        alert.setContentText("Your account has no permission to get into ADMIN MENU");
        alert.showAndWait();
    }

    private void set_alert_Request_Already_made(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Request Done!");
        alert.setHeaderText("Your request will be evaluated soon!");
        alert.setContentText("Wait until your request is processed by an Admin. Apologies for the delay!");
        alert.showAndWait();
    }


    private int set_alert_Confirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure you want ContentCreator Status?");
        alert.setHeaderText("Your request will be evaluated soon");
        alert.setContentText("As ContentCreator you already will be able to" +
                " upload your musics to DiscordandoMusic DataBase.");
        Optional<ButtonType> response = alert.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.OK) {
            return 1;
        }
        else return -1;
    }

    private void askCCStatus(String name){
        System.out.println("askCCStatus");
        DataBase.askCCStatus(name);
    }

    public void display(String username){

        usernameLabel.setText(username);
    }
    public void displayLabelChange(){

        labelChange.setOpacity(1);
    }
    public void notDisplayLabelChange(){

        labelChange.setOpacity(0);
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

    public void editaccount(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("editaccountMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Edit_Account");
        stage.setScene(scene);
        stage.show();

    }

    public void signOut(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
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

    public void adminMenu(ActionEvent e) throws IOException{
        System.out.println(User.getPrivilege());
        if(User.getPrivilege() != 2) {
            set_alert_Not_Admin();
            return;
        }

        root = FXMLLoader.load(getClass().getResource("adminmenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Admin Menu");
        stage.setScene(scene);
        stage.show();

    }

    public void suggestions(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("suggestions.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Admin Menu");
        stage.setScene(scene);
        stage.show();

    }

    public void contentCreatorStatus(ActionEvent e) throws IOException{
        int status = checkCurrentStatus(User.getName());
        if(status == 0){
            // é user e quer status.
            //System.out.println("USER");
            /* Verifica se o pedido já nao foi feito */

            if(check_Request(User.getName())){
                set_alert_Request_Already_made();
                return;
            }

            int res = set_alert_Confirmation();
            if(res == 1){
                askCCStatus(User.getName());
            }else{
                // nada
                System.out.println("Didn't confirm");
            }
        } else if(status == 1){
            // ja tem status
            //System.out.println("CC");
            set_alert_Already_With_Status();
        } else if (status == 2) {
            // ja tem status
            //System.out.println("ADMIN");
            set_alert_Already_With_Status();
        }
        else{
            return;
        }
    }

    public void createMusic(ActionEvent e) throws IOException{
        int status = checkCurrentStatus(User.getName());
        if(status == 0){
            set_alert_Not_CC();
            return;
        }
        root = FXMLLoader.load(getClass().getResource("addMusic.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Add Music");
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Remove.setOpacity(0);
        trash.setOpacity(0);

        usernameLabel.setText(User.getName());

        int[] musicIDs = DataBase.getAllLikes(User.getID());
        int i = 0;
        while(true){
            assert musicIDs != null;
            if (!(musicIDs[i] > 0)) break;


            String n = DataBase.getMusicNameFromID(musicIDs[i]);
            if(n != null){
                likedMusics.getItems().add(DataBase.getMusicNameFromID(musicIDs[i]));
            }
            i++;

        }

        if(User.getPrivilege() > 0){
            Music[] musicas = DataBase.getMusicsFromUser(User.getName());

            int y = 0;
            while(true){
                assert musicas != null;
                if (musicas[y] == null) break;
                ownMusics.getItems().add(musicas[y].getName());
                y++;
            }
        }
        else {
            ownMusics.getItems().add("None");
        }

        likedMusics.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

            }
        });

        ownMusics.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                Remove.setOpacity(1);
                trash.setOpacity(1);
            }
        });
    }

    public void remove(ActionEvent e) throws IOException{

        if(User.getPrivilege() > 0){

            int index = ownMusics.getSelectionModel().getSelectedIndex();

            String BanMusic = (String) ownMusics.getItems().get(index);

            int musicID = DataBase.getMusicIDfromName(BanMusic);
            int[] playlistsIDs = DataBase.playlistsFromMusic(musicID);

            // apagar da tabela de likes
            DataBase.removeMusicLikes(musicID);

            int i = 0;
            while(true){
                assert playlistsIDs != null;
                if (!(playlistsIDs[i] > 0)) break;

                DataBase.removeAttribution(musicID, playlistsIDs[i]);

                int nsongs = DataBase.getNumberSongs2(playlistsIDs[i]);
                nsongs--;
                DataBase.updatenSgons(playlistsIDs[i], nsongs);

                i++;
            }


            int res = DataBase.removeMusic(BanMusic);
            if(res < 0) System.out.println("err");


            ownMusics.getItems().clear();

            Music[] musicas = DataBase.getMusicsFromUser(User.getName());

            int y = 0;
            while(true){
                assert musicas != null;
                if (musicas[y] == null) break;
                ownMusics.getItems().add(musicas[y].getName());
                y++;
            }
        }


    }
}
