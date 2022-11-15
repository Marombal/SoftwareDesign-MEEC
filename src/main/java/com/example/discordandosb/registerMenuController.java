package com.example.discordandosb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import java.lang.*;

import java.sql.*;
public class registerMenuController {

    static final String db_url = "jdbc:postgresql://10.227.240.130:5432/pswa0603";
    static final String user   = "pswa0603";
    static final String passwd = "UhukZObc";
    static final String query  = "SELECT ID, user, password FROM DiscordandoDB.LoginAccounts";

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

    public void database(){
        // Connect to the DBMS (DataBase Management Server)
        try(Connection conn = DriverManager.getConnection(db_url, user, passwd);
            // Execute an SQL statement
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);) {
            // Analyse the resulting data
            while (rs.next()) {
                System.out.print("ID: "          + rs.getInt("ID"));
                System.out.print(", USER: " + rs.getString("user"));
                System.out.print(", PASSWORD: "      + rs.getString("password"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("ERRO");
            e.printStackTrace();
        }
}





    public int register_client(String username, String password, String email){
        send_email(username, email);
        return 1;
    }

    public int send_email(String username, String email){
        return 1;
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

        // RETURN -2 AINDA NÃO ESTÁ PROGRAMADO

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
    public void registerButton(ActionEvent e){
        database();
        email = emailField.getText();
        confirmEmail = confirmEmailField.getText();
        password = passwordField.getText();
        confirmPassword = confirmPasswordField.getText();
        username = userField.getText();

        int res;
        res = check_email(email, confirmEmail);
        if(res < 0)
            set_alert_email(res);
        res = check_password(password, confirmPassword);
        if(res < 0)
            set_alert_password(res);
        res = check_username(username);
        if(res < 0)
            set_alert_username(res);

        if(res > 0){
            register_client(username, password, email);
        }
    }
}
