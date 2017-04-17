package iq.ven.showdownclient.controller;

import iq.ven.showdownclient.view.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    ClientImpl client = LogInController.client;

    @FXML
    Button lobbyCreateButton;
    @FXML
    Button lobbyStartFightButton;
    @FXML
    Button lobbyJoinButton;
    @FXML
    Label lobbyAnswerLabel;

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        createSetOnAction();
        joinSetOnAction();
    }

    private void joinSetOnAction() {
        lobbyJoinButton.setOnAction(event -> {
            client.joinLobby();
        });
    }

    private void createSetOnAction() {
        lobbyCreateButton.setOnAction(event -> {
            client.createLobby();
        });
    }

    private void opeFightScene() {
        Parent root = null;
        Stage stage = (Stage) lobbyStartFightButton.getScene().getWindow();
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/fight.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
