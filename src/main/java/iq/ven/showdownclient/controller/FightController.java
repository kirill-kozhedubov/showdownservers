package iq.ven.showdownclient.controller;

import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.fighting.impl.ThreadFight;
import iq.ven.showdown.fighting.impl.ThreadFightPlayerTurn;
import iq.ven.showdownclient.view.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class FightController implements Initializable {
    private ClientImpl client = LogInController.client;
    private ThreadFight threadFight = ClientImpl.threadFight;
    private ClientEntity clientMe = getWhosMe();
    private ClientEntity clientOther = getWhosOther();


    //my things
    @FXML
    ImageView myHeroArchIV;
    @FXML
    ImageView myWeaponIV;
    @FXML
    ImageView myArmorIV;

    @FXML
    Label myNameL;
    @FXML
    ProgressBar myPB;

    //other things
    @FXML
    ImageView otherHeroArchIV;
    @FXML
    ImageView otherWeaponIV;
    @FXML
    ImageView otherArmorIV;

    @FXML
    Label otherNameL;
    @FXML
    ProgressBar otherPB;

    //turnThings
    ToggleGroup attackGroup;
    @FXML
    RadioButton attackHeadRB;
    @FXML
    RadioButton attackBodyRB;
    @FXML
    RadioButton attackLegsRB;


    ToggleGroup blockGroup;
    @FXML
    RadioButton blockHeadRB;
    @FXML
    RadioButton blockBodyRB;
    @FXML
    RadioButton blockLegsRB;

    @FXML
    Button makePlayB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientMe.getHero().heroInitialize();
        clientOther.getHero().heroInitialize();
        initToggleGroups();
        initImages();
        initHeroInfo();
        makePlaySetOnAction();
    }

    private void makePlaySetOnAction() {
        makePlayB.setOnAction(event -> {
            RadioButton attackRB = (RadioButton) attackGroup.getSelectedToggle();
            RadioButton blockRB = (RadioButton) attackGroup.getSelectedToggle();
            String blockValue = blockRB.getText();
            String attackValue = attackRB.getText();
            int block = getABValue(blockValue);
            int attack = getABValue(attackValue);
            ThreadFightPlayerTurn turn = new ThreadFightPlayerTurn(attack, block);
        });
    }

    private int getABValue(String s) {
        switch (s) {
            case "Head":
                return 0;
            case "Body":
                return 1;
            case "Legs":
                return 2;
        }
        return 1;
    }

    private void initHeroInfo() {
        myNameL.setText(clientMe.getUsername());
        otherNameL.setText(clientOther.getUsername());
        myPB.setProgress(clientMe.getHero().getHp());
        otherPB.setProgress(clientOther.getHero().getHp());
    }


    private void initImages() {
        myArmorIV.setImage(new Image(clientMe.getHero().getArmor().getImgLink()));
        myWeaponIV.setImage(new Image(clientMe.getHero().getWeapon().getImgLink()));
        myHeroArchIV.setImage(new Image(clientMe.getHero().getHeroArchetype().getImgLink()));

        otherArmorIV.setImage(new Image(clientOther.getHero().getArmor().getImgLink()));
        otherWeaponIV.setImage(new Image(clientOther.getHero().getWeapon().getImgLink()));
        otherHeroArchIV.setImage(new Image(clientOther.getHero().getHeroArchetype().getImgLink()));
    }

    private void initToggleGroups() {
        attackGroup = new ToggleGroup();
        attackBodyRB.setToggleGroup(attackGroup);
        attackHeadRB.setToggleGroup(attackGroup);
        attackLegsRB.setToggleGroup(attackGroup);


        blockGroup = new ToggleGroup();
        blockHeadRB.setToggleGroup(blockGroup);
        blockBodyRB.setToggleGroup(blockGroup);
        blockLegsRB.setToggleGroup(blockGroup);


    }


    private ClientEntity getWhosMe() {
        String myUserName = client.getClientEntity().getUsername();
        String client1Username = threadFight.getClient1().getUsername();
        String client2Username = threadFight.getClient2().getUsername();
        if (myUserName.equals(client1Username)) {
            return threadFight.getClient1();
        } else if (myUserName.equals(client2Username)) {
            return threadFight.getClient2();
        }
        return null;
    }

    private ClientEntity getWhosOther() {
        String myUserName = client.getClientEntity().getUsername();
        String client1Username = threadFight.getClient1().getUsername();
        String client2Username = threadFight.getClient2().getUsername();
        if (!myUserName.equals(client1Username)) {
            return threadFight.getClient1();
        } else if (!myUserName.equals(client2Username)) {
            return threadFight.getClient2();
        }
        return null;
    }

}
