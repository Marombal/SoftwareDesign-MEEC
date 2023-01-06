package Controllers;

import comment.Comment;
import database.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import music.Music;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class commentsMenuController implements Initializable{

    private Stage stage;
    private Parent root;
    private Scene scene;

    public int i = 0;
    private boolean stop = false;
    public int nComment = 0;
    public static int c;
    musicMenuController nSong = new musicMenuController();
    //musicMenuController index = new musicMenuController();
    private Comment[] comments = new Comment[255];

    @FXML
    Label label1, teste;
    @FXML
    TextField Comment;
    @FXML
    ListView listComments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateComments(nComment);
        Music music = new Music(nSong.whichMusic());
        teste.setText(music.getName());
    }

    private void set_alert_geral_ok(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Comment");
        alert.setHeaderText("Your comment was successfully received in Discordando DataBase");
        alert.setContentText("Please don't make inappropriate comments, otherwise it will be removed");
        alert.showAndWait();
    }

    private void set_alert_geral_error(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Comment");
        alert.setHeaderText("Something went wrong");
        alert.setContentText("Please make sure you don't submit neither empty nor too big comments!");
        alert.showAndWait();
    }

    private int checkComment(String comment){
        if((comment.length() == 0) || (comment.length() > 300)){
            return -1;
        }
        return 1;
    }


    public void addComment(ActionEvent e) throws IOException{
        Music music = new Music(nSong.whichMusic());
        String comm = Comment.getText();
        Comment.setText("");
        int res = checkComment(comm);
        if(res == 1) {
            int res2 = DataBase.submitComment(User.getName() , comm, music.getName());
            if(res2 > -1){
                set_alert_geral_ok();
                updateComments(c);
            }else{
                set_alert_geral_error();
            }
        }else{
            set_alert_geral_error();
        }
    }

    public void updateComments(int i){
        Music music = new Music(nSong.whichMusic());
        System.out.println("olaaaaaaaa");
        //listComments.getItems().clear();
        //String[] SuggestionsName = DataBase.getSuggestionsName();
        comments = DataBase.getCommentsClass();

        while(true){
            assert comments[i] != null;
            if (comments[i] == null){
                c = i;
                break;
            }
            System.out.println("Commentario " + comments[i].getMusica());
            System.out.println("Musica " + music.getName());
            if(comments[i].getMusica().equals(music.getName())){
                listComments.getItems().add("@" + comments[i].getName() + ":   " + comments[i].getComment());
                i++;
            }
            else{
                i++;
            }

        }
    }

    public void home(ActionEvent e) throws IOException {
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

    public void search(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("searchMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Search");
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

    public void createPlaylist(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("createplaylistMenu2.fxml"));
        //root = FXMLLoader.load(getClass().getResource("createplaylistMenu2.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Create Playlist");
        stage.setScene(scene);
        stage.show();
    }
    public void profile(ActionEvent e) throws IOException{


        FXMLLoader loader = new FXMLLoader(getClass().getResource("profileMenu.fxml"));
        root = loader.load();

        profileMenuController profileController = loader.getController();
        profileController.display(User.getName());

        // stop = true;

        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }

    public void back(ActionEvent e) throws IOException{

        root = FXMLLoader.load(getClass().getResource("musicMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Music");
        stage.setScene(scene);
        stage.show();
    }


}

