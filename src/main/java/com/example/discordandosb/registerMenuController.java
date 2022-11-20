package com.example.discordandosb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Objects;

import java.lang.*;

import java.sql.*;
import java.util.Properties;

public class registerMenuController extends DataBase {

    static final String db_url = "jdbc:postgresql://10.227.240.130:5432/pswa0603";
    static final String user   = "pswa0603";
    static final String passwd = "UhukZObc";
    static final String query  = "INSERT INTO discordando.login (id, user, password, email, )" +
            "VALUES (3, 'Miguel2', 'Miguel3', 'm@gmail.com');";

    private Parent root;
    private Scene scene;
    private Stage stage;

    private String email;
    private String confirmEmail;
    private String password;
    private String username;
    private String confirmPassword;

    @FXML
    TextField emailField;
    @FXML
    TextField confirmEmailField;
    @FXML
    TextField userField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField confirmPasswordField;

    public void set_alert_geral_ok(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register Done");
        alert.setHeaderText("Your account was successfully register in Discordando DataBase");
        alert.setContentText("You will receive an confirmation email soon. " +
                "Your LogIn is ready");
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
        alert.setHeaderText("Register fail");
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
        alert.setHeaderText("Register fail");
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
        alert.setHeaderText("Register fail");
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


    public int register_client(String username, String password, String email) throws Exception {
        int res;
        res = DataBase.addLogin(username, password, email);
        if(res < 0) return 0;
        res = send_email(username, email);
        if(res < 0) return 0;
        return 1;
    }

    public static int send_email(String username, String email) throws Exception{
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
            message.setSubject("Register confimation mail");
            message.setContent("Hello "+username+" welcome to our family! \n<h1>Discordando Music</h1>", "text/html");

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

    /*
    * Return:
    * 1 when everything okay
    * -1 when password doesn't respect minimum parameters (too weak password)
    * -2 when password and confirm password doesn't match
    * -3 when password field is empty
    * */
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

    /*
     * Return:
     * 1 when everything okay
     * -1 when username doesn't respect minimum parameters
     * -2 when username is already in use
     * -3 when username field is empty
     * */
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

    /*
     * Return:
     * 1 when everything okay
     * -1 when email isn't a valid email
     * -2 when email and confirm email doesn't match
     * -3 when email field is empty
     * */
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


    @FXML
    public void returnToLogin(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void registerButton(ActionEvent e) throws Exception {

        email = emailField.getText();
        confirmEmail = confirmEmailField.getText();
        password = passwordField.getText();
        confirmPassword = confirmPasswordField.getText();
        username = userField.getText();

        int res;
        res = check_email(email, confirmEmail);
        if(res < 0){
            set_alert_email(res);
            return;
        }
        res = check_password(password, confirmPassword);
        if(res < 0){
            set_alert_password(res);
            return;
        }
        res = check_username(username);
        if(res < 0){
            set_alert_username(res);
            return;
        }

        if(res > 0){
            res = register_client(username, password, email);
        }


        if(res < 0){
            set_alert_geral_error();
        }else{
            set_alert_geral_ok();
            returnToLogin(e);
        }
    }
}
