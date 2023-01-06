package Controllers;

import comment.Comment;
import database.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import suggestion.Suggestion;
import user.User;

import java.io.IOException;

public class adminmenuController {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    Label listLabel;
    @FXML
    Label text;
    @FXML
    ListView list;
    @FXML
    Button readB;
    @FXML
    Button giveB;

    @FXML
    ImageView readPic;
    @FXML
    ImageView givePic;

    /*
    * Mode 0 - None
    * Mode 1 - Users
    * Mode 2 - Musics
    * ""
    * */
    private int Mode = 0;
    private final int MAX = 255;

    private Suggestion[] suggestions = new Suggestion[MAX];
    private Comment[] comments = new Comment[MAX];

    private void set_alert_suggestion(String name, String sug){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUGGESTION");
        alert.setHeaderText(name + " Suggested...");
        alert.setContentText(sug);
        alert.showAndWait();
    }
    private void set_alert_comment(String name, String comm){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("COMMENT");
        alert.setHeaderText(name + " Commented...");
        alert.setContentText(comm);
        alert.showAndWait();
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

    public void signOut(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    public void userList(ActionEvent e) throws IOException{
        String[] Users = DataBase.getAllUsers();
        text.setText("List of Users: ");
        list.getItems().clear();

        int i = 0;
        while(true){
            assert Users != null;
            if (Users[i] == null) break;
            list.getItems().add(Users[i++]);
        }
        Mode = 1;

        readB.setOpacity(0);
        giveB.setOpacity(0);

        readPic.setOpacity(0);
        givePic.setOpacity(0);
    }
    public void musicList(ActionEvent e) throws IOException{
        text.setText("List of Musics: ");
        String[] Musics = DataBase.getAllMusic();
        list.getItems().clear();

        int i = 0;
        while(true){
            assert Musics != null;
            if (Musics[i] == null) break;
            list.getItems().add(Musics[i++]);
        }
        Mode = 2;

        readB.setOpacity(0);
        giveB.setOpacity(0);


        readPic.setOpacity(0);
        givePic.setOpacity(0);
    }

    public void suggestionsList(ActionEvent e) throws IOException{
        text.setText("List of Suggestions: ");
        list.getItems().clear();
        //String[] SuggestionsName = DataBase.getSuggestionsName();
        suggestions = DataBase.getSuggestionsClass();

        int i = 0;
        while(true){
            assert suggestions[i] != null;
            if (suggestions[i] == null) break;
            list.getItems().add(suggestions[i++].getName());
        }

        Mode = 3;

        readB.setOpacity(1);
        giveB.setOpacity(0);


        readPic.setOpacity(1);
        givePic.setOpacity(0);

    }
    public void commentList(ActionEvent e) throws IOException{
        text.setText("List of Comments: ");
        list.getItems().clear();
        //String[] SuggestionsName = DataBase.getSuggestionsName();
        comments = DataBase.getCommentsClass();

        int i = 0;
        while(true){
            assert comments[i] != null;
            if (comments[i] == null) break;
            list.getItems().add(comments[i++].getName());
        }

        Mode = 5;

        readB.setOpacity(1);
        giveB.setOpacity(0);


        readPic.setOpacity(1);
        givePic.setOpacity(0);

    }

    public void requestList(ActionEvent e) throws IOException{
        text.setText("List of Content Creator requests: ");
        list.getItems().clear();

        String[] requests = DataBase.getRequests();

        int i = 0;
        while(true){
            assert requests[i] != null;
            if (requests[i] == null) break;
            list.getItems().add(requests[i++]);
        }

        Mode = 4;

        readB.setOpacity(0);
        giveB.setOpacity(1);


        readPic.setOpacity(0);
        givePic.setOpacity(1);
    }

    public void read(ActionEvent e) throws IOException{
        if(Mode == 3){
            int index = list.getSelectionModel().getSelectedIndex();
            set_alert_suggestion(suggestions[index].getName(), suggestions[index].getSuggestion());
        }
        if(Mode == 5){
            int index = list.getSelectionModel().getSelectedIndex();
            set_alert_comment(comments[index].getName(), comments[index].getComment());
        }
    }

    public void remove(ActionEvent e) throws IOException{

        int index = list.getSelectionModel().getSelectedIndex();

        if(Mode == 1){
            // User mode
            //System.out.println(list.getItems().get(index));
            String BanUser = (String) list.getItems().get(index);
            int res = DataBase.removeUser(BanUser);
            if(res < 0) System.out.println("err");
            userList(e);
        }
        else if(Mode == 2){
            // Music mode
            //System.out.println(list.getItems().get(index));
            String BanMusic = (String) list.getItems().get(index);

            int musicID = DataBase.getMusicIDfromName(BanMusic);
            int[] playlistsIDs = DataBase.playlistsFromMusic(musicID);

            //apagar da tabela de atribuições
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
            musicList(e);
        }
        else if(Mode == 3){
            int res = DataBase.removeSuggestion(suggestions[index]);
            if(res < 0) System.out.println("err");
            suggestionsList(e);
        }
        else if (Mode == 4) {
            int res = DataBase.removeRequest((String) list.getItems().get(index));
            if(res < 0) System.out.println("err");
            requestList(e);
        }
        else if(Mode == 5){
            int res = DataBase.removeComment(comments[index]);
            if(res < 0) System.out.println("err");
            commentList(e);
        }
    }

    public void give(ActionEvent e) throws IOException{
        if(Mode == 4){
            int index = list.getSelectionModel().getSelectedIndex();
            String name = (String) list.getItems().get(index);
            DataBase.givePrivilege(name);
            requestList(e);
        }
    }

    public void profile(ActionEvent e) throws IOException{


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


}
