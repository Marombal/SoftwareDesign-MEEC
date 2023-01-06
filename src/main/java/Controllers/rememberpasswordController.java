package Controllers;

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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static java.sql.Types.NULL;

public class rememberpasswordController {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    TextField email;

    @FXML
    TextField username;

    public void set_alert_geral_ok(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Password Created");
        alert.setHeaderText("The Password was successfully sent to your email account");
        alert.setContentText("You will receive an email soon. ");
        alert.showAndWait();
    }

    public void set_alert_geral_error(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText("Something went wrong here :(" +
                "Try again latter");
        alert.showAndWait();
    }

    public void set_alert_email(int alert_type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Fail Action");
        if(alert_type == -1){
            alert.setContentText("Email field cant be empty");
            alert.showAndWait();
        }
        else if(alert_type == -2){
            alert.setContentText("Email doesn't match with the account email ");
            alert.showAndWait();
        }

    }

    public void set_alert_username(int alert_type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Fail Action");
        if(alert_type == -1){
            alert.setContentText("Username doesn't exists");
            alert.showAndWait();
        }

        else if(alert_type == -2){
            alert.setContentText("Username field cant be empty");
            alert.showAndWait();
        }
    }

    public int remember_password(String email, String username) throws Exception{
        String pass;
        int res;
        pass = DataBase.getPassword(username);
        if(pass == String.valueOf(NULL)) return -1;
        res = send_email(pass, email, username);
        if(res < 0) return-1;
        return 1;
    }

    public void remember(ActionEvent e) throws Exception {
        String user = username.getText();
        String mail = email.getText();

        int res;
        res = check_email(user, mail);
        if(res < 0){
            set_alert_email(res);
            return;
        }

        res = check_username(user);
        if(res < 0){
            set_alert_username(res);
            return;
        }

        if (res > 0){
            res = remember_password(mail, user);
        }

        if(res < 0){
            set_alert_geral_error();
        }else{
            set_alert_geral_ok();
            exit(e);
        }

    }

    public static int send_email(String pass, String email, String username) throws Exception{
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocl", "smtp");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("discordantomusic@gmail.com", "bodqwwnilenlctba");
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setSubject("Remember password");
            message.setContent("Mr. "+username+" \nYour password is: "+pass+" \n<h1>Discordando Music</h1>", "text/html");

            Address addressTo = new InternetAddress(email);
            message.setRecipient(Message.RecipientType.TO, addressTo);

            Transport.send(message);

            System.out.println("Message sent successfully...");

            return 1;
        }
        catch(Exception e) {
            System.out.println("Errore email: "+e.toString());
            return -1;
        }
    }

    private int check_email(String user_, String mail_){
        if(Objects.equals(mail_, "")){
            return -1;
        }
        int res = DataBase.checkEmail(user_, mail_);
        if (res == 1) return 1;
        else return -2;
    }

    public int check_username(String username){

        if(Objects.equals(username, "")){
            return -2;
        }
        if(DataBase.findUsername(username) != 1){
            return -1;
        }

        return 1;
    }

    public void exit(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }
}
