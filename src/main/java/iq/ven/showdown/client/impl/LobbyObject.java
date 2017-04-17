package iq.ven.showdown.client.impl;

import java.io.Serializable;

public class LobbyObject implements Serializable {

    private String lobbyId;

    public LobbyObject(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    @Override
    public String toString() {
        return "LobbyObject{" +
                "lobbyId='" + lobbyId + '\'' +
                '}';
    }
}
