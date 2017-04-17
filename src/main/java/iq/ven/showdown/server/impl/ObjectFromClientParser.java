package iq.ven.showdown.server.impl;

import iq.ven.showdown.client.impl.*;
import iq.ven.showdown.fighting.impl.Lobby;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by User on 15.04.2017.
 */
public class ObjectFromClientParser {
    private static final Logger logger = Logger.getLogger(ObjectFromClientParser.class);

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private AbstractThreadClient abstractThreadClient;

    public ObjectFromClientParser(ObjectOutputStream out, ObjectInputStream in, AbstractThreadClient abstractThreadClient) {
        this.out = out;
        this.in = in;
        this.abstractThreadClient = abstractThreadClient;
    }


    void parseInputObject(Object object) {
        if (object instanceof SuccessfulRegistrationObject) {
            logger.log(Level.DEBUG, "Got SuccessfulRegistrationObject from client " + object);
            authorize(object);
        } else if (object instanceof InitialDataForServerObject) {
            logger.log(Level.DEBUG, "Got InitialDataForServerObject from client " + object);
            authorize(object);
        } else if (object instanceof CreateLobbyObject) {
            logger.log(Level.DEBUG, "Got CreateLobbyObject from client " + object);
            lobbyCreate();
        } else if (object instanceof JoinLobbyObject) {
            logger.log(Level.DEBUG, "Got JoinLobbyObject from client " + object);
            joinLobby();
        } else if (object instanceof FightStartObject) {
            logger.log(Level.DEBUG, "Got FightStartObject from client " + object);
            startFight();
        }

    }

    void startFight() {

    }

    void joinLobby() {
        try {
            if (AbstractThreadClient.lobbyList.size() <= 0) {
                out.writeObject(new LobbyError());
            } else {
                abstractThreadClient.connectToLobby(AbstractThreadClient.lobbyList.get(AbstractThreadClient.lobbyList.size() - 1));
                out.writeObject(AbstractThreadClient.lobbyList.get(AbstractThreadClient.lobbyList.size() - 1).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void lobbyCreate() {
        try {
            if (AbstractThreadClient.lobbyList.size() > 0) {
                out.writeObject(new LobbyError());
            } else {
                Lobby lobby = abstractThreadClient.createLobby();
                out.writeObject(new LobbyObject(lobby.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void authorize(Object object) {
        try {
            abstractThreadClient.tryAuthorize(object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
