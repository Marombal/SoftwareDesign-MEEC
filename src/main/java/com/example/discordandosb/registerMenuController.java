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
public class registerMenuController {

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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Register fail");
        char x;
        int contador_capital = 0; int contador_digits = 0;

        boolean emptySpace = false;
        if(!Objects.equals(emailField.getText(), "")){
            email = emailField.getText();
            //System.out.println(email);
        }else{
            //System.out.println("email empty");
            emptySpace = true;
        }

        if(!Objects.equals(confirmEmailField.getText(), "")){
            confirmEmail = confirmEmailField.getText();
            //System.out.println(confirmEmail);
        }else{
            //System.out.println("cemail empty");
            emptySpace = true;
        }

        if(!Objects.equals(userField.getText(), "")){
            username = userField.getText();
            for(int i = 0; i<username.length(); i++){
                x = username.charAt(i);
                if(Character.isUpperCase(x)){
                    contador_capital++;
                }
                if(Character.isDigit(x)){
                    contador_digits++;
                }
            }
            if(contador_capital == 0){
                alert.setContentText("username doesn't respect the minimum requirements");
                alert.showAndWait();
            }
            else if(contador_digits == 0){
                alert.setContentText("username doesn't respect the minimum requirements");
                alert.showAndWait();
            }
            //System.out.println(username);
        }else{
            //System.out.println("user empty");
            emptySpace = true;
        }

        contador_capital = 0; contador_digits = 0;
        if(!Objects.equals(passwordField.getText(), "")){
            password = passwordField.getText();
            for(int i = 0; i<password.length(); i++){
                x = password.charAt(i);
                if(Character.isUpperCase(x)){
                    contador_capital++;
                }
                if(Character.isDigit(x)){
                    contador_digits++;
                }
            }
            if(contador_capital == 0){
                alert.setContentText("password doesn't respect the minimum requirements");
                alert.showAndWait();
            }
            else if(contador_digits == 0){
                alert.setContentText("password doesn't respect the minimum requirements");
                alert.showAndWait();
            }
        }else{
            //System.out.println("pass empty");
            emptySpace = true;
        }

        if(!Objects.equals(confirmPasswordField.getText(), "")){
            confirmPassword = confirmPasswordField.getText();
        }else{
            //System.out.println("cpass empty");
            emptySpace = true;
        }

        /*  Verificar se os campos confirm dão match ou há espaços vazios*/
        if(emptySpace){
            alert.setContentText("Don't leave empty fields");
            alert.showAndWait();
        }
        else if(!Objects.equals(email, confirmEmail)){
            alert.setContentText("'Confirm email' must match 'email'");
            alert.showAndWait();
        }
        else if(!Objects.equals(password, confirmPassword)){
            alert.setContentText("'Confirm password' must match 'password'");
            alert.showAndWait();
        }






    }
}
