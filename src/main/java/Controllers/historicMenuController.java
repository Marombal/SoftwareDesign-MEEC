package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class historicMenuController implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    public ListView<String> historiclistView;

    List<String> historic = new ArrayList<>( Arrays.asList("Coldplay","Hymn_For_The_Weekend", "Viva_La_Vida",
            "Yellow","A_Sky_Full_Of_Stars","Paradise", "Music")
    );

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historiclistView.getItems().addAll(User.getHistoric());
    }
}
