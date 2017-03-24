package iq.ven.showdown.client.impl;

import iq.ven.showdown.database.ClientEntity;

import java.io.Serializable;

/**
 * Created by User on 24.03.2017.
 */
public class InitialDataForServerObject implements Serializable {
    private String username;
    private String password;

    public InitialDataForServerObject(String username, String password) {
        this.username = username;
        this.password = password;
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


}
