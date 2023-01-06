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
import user.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class editaccountController extends DataBase {
    private Parent root;
    private Scene scene;
    private Stage stage;

    private String password;

    private String confirmPassword;

    private String email;

    private String confirmEmail;

    private String username;

    @FXML
    TextField newuserField;

    @FXML
    TextField newemailField;

    @FXML
    TextField confirmnewEmailField;

    @FXML
    TextField newpasswordField;

    @FXML
    TextField confirmnewPasswordField;

    public int check_password(String password, String confirmPassword){

        int contador_capital = 0, contador_digits = 0;
        char x;

        if(Objects.equals(password, "")){
            return -3;
        }
        if(!Objects.equals(password, confirmPassword)){
            return -2;
        }

        for(int i = 0; i<password.length(); i++){

            x = password.charAt(i);

            if(Character.isUpperCase(x)){
                contador_capital++;
            }
            if(Character.isDigit(x)){
                contador_digits++;
            }
        }

        if((contador_capital == 0)||(contador_digits == 0)||(password.length() < 5)){
            return -1;
        }

        return 1;
    }

    public int check_email(String email, String confirmEmail){

        if(Objects.equals(email, "")){
            return -3;
        }

        if(!Objects.equals(email,confirmEmail)){
            return -2;
        }

        /*
        A valid email should have 1 and only 1 "@"
        @ cannot be neither the 1st neither the last character
         */
        if(email.contains("@")){
            char first = email.charAt(1);
            char last = email.charAt(email.length()-1);
            if((first == '@')||(last == '@')){
                return -1;
            }
        }else{return -1;}

        return 1;
    }

    public int check_username(String username){
        int contador_capital = 0, contador_digits = 0;
        char x;

        if(Objects.equals(username, "")){
            return -3;
        }

        if(DataBase.findUsername(username) == 1){
            return -2;
        }

        for(int i = 0; i<username.length(); i++){

            x = username.charAt(i);

            if(Character.isUpperCase(x)){
                contador_capital++;
            }
            if(Character.isDigit(x)){
                contador_digits++;
            }
        }

        if((contador_capital == 0)||(contador_digits == 0)||(username.length() < 5)){
            return -1;
        }

        return 1;
    }

    public void set_alert_geral_ok(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Change Done");
        alert.setHeaderText("Your change was successfully done in Discordando DataBase");
        alert.setContentText("You will receive an confirmation email soon. ");
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

    public void set_alert_password(int alert_type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Change fail");
        if(alert_type == -1){
            alert.setContentText("""
                    Password doesn't respect the minimum requirements
                    Password must contain at least 1 Capital
                    Password must contain at least 1 Digit
                    Password must have at least 5 characters
                    """);
            alert.showAndWait();
        }
        else if(alert_type == -2){
            alert.setContentText("Password doesn't match Confirm Password");
            alert.showAndWait();
        }
        else if(alert_type == -3){
            alert.setContentText("Password field cant be empty");
            alert.showAndWait();
        }
    }

    public void set_alert_email(int alert_type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("change fail");
        if(alert_type == -1){
            alert.setContentText("Email isn't valid");
            alert.showAndWait();
        }
        else if(alert_type == -2){
            alert.setContentText("Email doesn't match Confirm Email");
            alert.showAndWait();
        }
        else if(alert_type == -3){
            alert.setContentText("Email field cant be empty");
            alert.showAndWait();
        }

    }

    public void set_alert_username(int alert_type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Change fail");
        if(alert_type == -1){
            alert.setContentText("""
                    Username doesn't respect the minimum requirements
                    Username must contain at least 1 Capital
                    Username must contain at least 1 Digit
                    Username must have at least 5 characters
                    """);
            alert.showAndWait();
        }
        else if(alert_type == -2){
            alert.setContentText("Username already in use");
            alert.showAndWait();
        }
        else if(alert_type == -3){
            alert.setContentText("Username field cant be empty");
            alert.showAndWait();
        }
    }

    public int change_password(String username, String password, String email) throws Exception {
        int res;
        res = DataBase.changepassword(username, password);
        if(res < 0) return -1;
        res = send_email_newpass(username, email, password);
        if(res < 0) return -1;
        return 1;
    }

    public int change_mail(String username, String email) throws Exception {
        int res;
        res = DataBase.changemail(username, email);
        if(res < 0) return -1;
        res = send_email_newmail(username, email);
        if(res < 0) return -1;
        return 1;
    }

    public int change_user(String username, String new_username, String mail) throws Exception {
        int res;
        res = DataBase.changeuser(username,new_username);
        if(res<0) return -1;
        res = send_email_newuser(new_username, mail);
        if(res<0) return -1;
        return 1;
    }

    public static int send_email_newpass(String username, String email, String password) throws Exception{
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
            message.setSubject("New password");
            message.setContent("Hello "+username+" you change your password to : "+password+" \n<h1>Discordando Music</h1>", "text/html");

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

    public static int send_email_newmail(String username, String email) throws Exception{
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
            message.setSubject("New mail");
            message.setContent("Hello "+username+" you change your mail account successfully !!!  \n<h1>Discordando Music</h1>", "text/html");

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

    public static int send_email_newuser(String username, String email) throws Exception{
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
            message.setSubject("New username");
            message.setContent("Hello "+username+" you change your username successfully !!!  \n<h1>Discordando Music</h1>", "text/html");

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

    @FXML
    public void changepasswordButton(ActionEvent e) throws Exception{

        password = newpasswordField.getText();
        confirmPassword = confirmnewPasswordField.getText();

        String user_name = User.getName();
        String e_mail = DataBase.getEmail(user_name);

        int res;
        res = check_password(password, confirmPassword);
        if(res < 0){
            set_alert_password(res);
            return;
        }

        if(res > 0){
            res = change_password(user_name, password, e_mail);
            newpasswordField.clear();
            confirmnewPasswordField.clear();
        }

        if(res < 0){
            set_alert_geral_error();
        }else{
            set_alert_geral_ok();
        }
    }

    @FXML
    public void changemailButton(ActionEvent e) throws Exception{
        email = newemailField.getText();
        confirmEmail = confirmnewEmailField.getText();

        String user_name = User.getName();

        int res;
        res = check_email(email, confirmEmail);
        if(res < 0){
            set_alert_email(res);
            return;
        }
        if(res > 0){
            res = change_mail(user_name, email);
            newemailField.clear();
            confirmnewEmailField.clear();
        }
        if(res < 0){
            set_alert_geral_error();
        }else{
            set_alert_geral_ok();
        }
    }

    @FXML
    public void changeuserButton(ActionEvent e) throws Exception{
        username = newuserField.getText();

        String old_user = User.getName();
        String e_mail = DataBase.getEmail(old_user);

        int res;
        res = check_username(username);
        if(res<0){
            set_alert_username(res);
            return;
        }
        if(res>0){
            res = change_user(old_user, username, e_mail);
            newuserField.clear();
            User.setName(username);
        }
        if(res < 0){
            set_alert_geral_error();
        }else{
            set_alert_geral_ok();
        }
    }

    public void return_profile(ActionEvent e) throws IOException {

        root = FXMLLoader.load(getClass().getResource("profileMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();

    }

}
