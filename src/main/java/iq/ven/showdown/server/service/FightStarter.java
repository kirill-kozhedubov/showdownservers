package iq.ven.showdown.server.service;

import iq.ven.showdown.fighting.impl.Lobby;
import iq.ven.showdown.fighting.impl.ThreadFight;

/**
 * Created by User on 25.03.2017.
 */
public class FightStarter {

    private Lobby lobby;

    public FightStarter(Lobby lobby) {
        this.lobby = lobby;
    }

    public ThreadFight startFight() {
        ThreadFight fight = new ThreadFight(lobby.getClient1(), lobby.getClient2(), lobby);
        fight.start();
        return fight;
    }
}

