package iq.ven.showdownclient.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpScene {

    public SignUpScene(Stage primaryStage) {
        replaceSceneContent(primaryStage);
    }

    private Parent replaceSceneContent(Stage primaryStage) {
        Parent page = null;
        try {
            page = FXMLLoader.load(getClass().getResource("fxml/signup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = primaryStage.getScene();
        primaryStage.setTitle("Register for VENIQUNKNOWN'S BATTLEGROUNDS");
        if (scene == null) {
            scene = new Scene(page, 650, 550);
            primaryStage.setScene(scene);
        } else {
            primaryStage.getScene().setRoot(page);
        }
        primaryStage.sizeToScene();
        return page;
    }
}
