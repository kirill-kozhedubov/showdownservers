package iq.ven.showdownclient.controller;

import iq.ven.showdownclient.view.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    public static ClientImpl client;
    private static final Logger logger = Logger.getLogger(LogInController.class);

    @FXML
    TextField signinUsernameTextField;
    @FXML
    PasswordField signinPasswordTextField;
    @FXML
    Button signinLogInButton;
    @FXML
    Button signinSignUpButton;
    @FXML
    Label signinAnswerLabel;


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        logInSetOnAction();
        signUpSetOnAction();
        client = new ClientImpl();
        client.initServerData();
        client.start();
    }

    private void logInSetOnAction() {
        signinLogInButton.setOnAction(event -> {
            boolean loggedIn = false;
            try {
                String username = signinUsernameTextField.getText();
                String password = signinPasswordTextField.getText();
                loggedIn = client.tryToLogin(username, password);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (loggedIn == false) {
                signinAnswerLabel.setText("Incorrect data");
            } else {
                openLobbyScene();
                System.out.println("logged in");
            }
        });
    }

    private void signUpSetOnAction() {
        signinSignUpButton.setOnAction(event -> {
            openSignupScene();
        });
    }


    private void openSignupScene() {
        Parent root = null;
        Stage stage = (Stage) signinLogInButton.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/signup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void openLobbyScene() {
        Stage stage = null;
        Parent root = null;
        stage = (Stage) signinLogInButton.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/lobby.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public ClientImpl getClient() {
        return client;
    }
}

