package Controllers;

import comment.Comment;
import database.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;

import java.io.IOException;


public class suggestionsController {

    private Stage stage;
    private Parent root;
    private Scene scene;


    @FXML
    TextField Suggestion;

    private void set_alert_geral_ok(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suggestion");
        alert.setHeaderText("Your suggestion was successfully received in Discordando DataBase");
        alert.setContentText("Thank for helping us making DiscordandoMusic better!");
        alert.showAndWait();
    }

    private void set_alert_geral_error(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suggestion");
        alert.setHeaderText("Something went wrong");
        alert.setContentText("Please make sure you don't submit neither empty nor too big suggestion!");
        alert.showAndWait();
    }

    private int checkSuggestion(String suggestion){
        if((suggestion.length() == 0) || (suggestion.length() > 300)){
            return -1;
        }
        return 1;
    }

    public void submit(ActionEvent e) throws IOException{
        String sug = Suggestion.getText();
        Suggestion.setText("");
        int res = checkSuggestion(sug);
        if(res == 1) {
            int res2 = DataBase.submitSuggestion(User.getName() , sug);
            if(res2 > -1){
                set_alert_geral_ok();
            }else{
                set_alert_geral_error();
            }
        }else{
            set_alert_geral_error();
        }
    }

    public void GoBack(ActionEvent e) throws IOException{

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
