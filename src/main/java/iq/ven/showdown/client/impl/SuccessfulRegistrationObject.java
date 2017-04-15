package iq.ven.showdown.client.impl;

import java.io.Serializable;

/**
 * Created by User on 24.03.2017.
 */
public class SuccessfulRegistrationObject implements Serializable {
    private String username;
    private String password;
    private String heroArchName;
    private String heroArmorName;
    private String heroWeaponName;

    public SuccessfulRegistrationObject(String username, String password, String heroArchName, String heroArmorName, String heroWeaponName) {
        this.username = username;
        this.password = password;
        this.heroArchName = heroArchName;
        this.heroArmorName = heroArmorName;
        this.heroWeaponName = heroWeaponName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeroArchName() {
        return heroArchName;
    }

    public void setHeroArchName(String heroArchName) {
        this.heroArchName = heroArchName;
    }

    public String getHeroArmorName() {
        return heroArmorName;
    }

    public void setHeroArmorName(String heroArmorName) {
        this.heroArmorName = heroArmorName;
    }

    public String getHeroWeaponName() {
        return heroWeaponName;
    }

    public void setHeroWeaponName(String heroWeaponName) {
        this.heroWeaponName = heroWeaponName;
    }

    @Override
    public String toString() {
        return "SuccessfulRegistrationObject{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", heroArchName='" + heroArchName + '\'' +
                ", heroArmorName='" + heroArmorName + '\'' +
                ", heroWeaponName='" + heroWeaponName + '\'' +
                '}';
    }
}
