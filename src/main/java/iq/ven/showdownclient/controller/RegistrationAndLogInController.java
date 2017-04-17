package iq.ven.showdownclient.controller;

import iq.ven.showdownclient.view.ClientImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationAndLogInController implements Initializable {
    private ClientImpl client = LogInController.client;
    private static final Logger logger = Logger.getLogger(LogInController.class);

    @FXML
    TextField signupUsernameTextField;
    @FXML
    PasswordField signupPasswordTextField;
    @FXML
    PasswordField signupConfirmPasswordTextField;

    @FXML
    ImageView signupWeaponImage;
    @FXML
    ImageView signupArmorImage;
    @FXML
    ImageView signupHeroArchImage;

    @FXML
    Button signupCompleteButton;
    @FXML
    ComboBox<String> signupArchetypeComboBox;
    @FXML
    ComboBox<String> signupWeaponComboBox;
    @FXML
    ComboBox<String> signupArmorComboBox;


    @Override

    public void initialize(URL location, ResourceBundle resources) {
        logger.log(Level.INFO, "client = " + client);
        signupSetOnAction();
        archetypeSelectorSet();
        weaponSelectorSet();
        armorSelectorSet();
        signupArchetypeComboBox.setItems(getListOfArchetypes());
        signupArmorComboBox.setItems(getListOfArmor());
        signupWeaponComboBox.setItems(getListOfWeapons());

        signupHeroArchImage.setImage(new Image("http://i.imgur.com/NxijXKS.jpg"));
        signupWeaponImage.setImage(new Image("http://i.imgur.com/nby7J7p.jpg"));
        signupArmorImage.setImage(new Image("http://i.imgur.com/ntjp4vy.jpg"));
    }


    private void signupSetOnAction() {
        signupCompleteButton.setOnAction(event -> {
            String username = signupUsernameTextField.getText();
            String password = signupPasswordTextField.getText();
            String archetype = signupArchetypeComboBox.getSelectionModel().getSelectedItem();
            String weapon = signupWeaponComboBox.getSelectionModel().getSelectedItem();
            String armor = signupArmorComboBox.getSelectionModel().getSelectedItem();
            try {
                boolean logon = client.registerAndLogin(username, password, archetype, armor, weapon);
                if (logon == true) {
                    openLobbyScene();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registration fail");
                    alert.setHeaderText(null);
                    alert.setContentText("Your registration fail, pls try another time");

                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void openLobbyScene() {
        Parent root = null;
        Stage stage = (Stage) signupCompleteButton.getScene().getWindow();
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

    private ObservableList<String> getListOfArchetypes() {
        ObservableList<String> archetypeList = FXCollections.observableArrayList();
        archetypeList.add("Солдат 69");
        archetypeList.add("Тадлос 96");
        archetypeList.add("Владислав Кромберг");
        archetypeList.add("Джаспер");

        return archetypeList;
    }

    private ObservableList<String> getListOfWeapons() {
        ObservableList<String> weaponsList = FXCollections.observableArrayList();
        weaponsList.add("Боевой цилиндр");
        weaponsList.add("Двуручная шаурма");
        weaponsList.add("Кастет номер 69");
        weaponsList.add("VENIQUNKNOWN'S SHURIKEN");

        return weaponsList;
    }

    private ObservableList<String> getListOfArmor() {
        ObservableList<String> armorList = FXCollections.observableArrayList();
        armorList.add("Костюм");
        armorList.add("Спортивки");
        armorList.add("Шляпа");
        armorList.add("Крест");

        return armorList;
    }

    private void archetypeSelectorSet() {
        signupArchetypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Солдат 69":
                    signupHeroArchImage.setImage(new Image("http://i.imgur.com/HuQv0I4.png"));
                    break;
                case "Тадлос 96":
                    signupHeroArchImage.setImage(new Image("http://i.imgur.com/nqhz7Uq.png"));
                    break;
                case "Владислав Кромберг":
                    signupHeroArchImage.setImage(new Image("http://i.imgur.com/HFuognf.png"));
                    break;
                case "Джаспер":
                    signupHeroArchImage.setImage(new Image("http://i.imgur.com/Oy3JmMA.png"));
                    break;
            }
        });
    }


    private void armorSelectorSet() {
        signupArmorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Костюм":
                    signupArmorImage.setImage(new Image("http://i.imgur.com/oPtcssB.jpg"));
                    break;
                case "Спортивки":
                    signupArmorImage.setImage(new Image("http://i.imgur.com/xY92Uhm.jpg"));
                    break;
                case "Шляпа":
                    signupArmorImage.setImage(new Image("http://i.imgur.com/QVGCZEi.jpg"));
                    break;
                case "Крест":
                    signupArmorImage.setImage(new Image("http://i.imgur.com/bwfH4B4.jpg"));
                    break;
            }
        });
    }


    private void weaponSelectorSet() {
        signupWeaponComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Боевой цилиндр":
                    signupWeaponImage.setImage(new Image("http://i.imgur.com/K5iOCal.jpg"));
                    break;
                case "Двуручная шаурма":
                    signupWeaponImage.setImage(new Image("http://i.imgur.com/x1iWX8M.png"));
                    break;
                case "Кастет номер 69":
                    signupWeaponImage.setImage(new Image("http://i.imgur.com/JNWM2zE.png"));
                    break;
                case "VENIQUNKNOWN'S SHURIKEN":
                    signupWeaponImage.setImage(new Image("http://i.imgur.com/KquN8ro.png"));
                    break;
            }
        });
    }

}
