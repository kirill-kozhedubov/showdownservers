package iq.ven.showdown.fighting.impl;

import iq.ven.showdown.client.impl.PlayerLostFightObject;
import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.fighting.model.Fight;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.03.2017.
 */
public class ThreadFight extends Thread implements Fight {
    private static final Logger logger = Logger.getLogger(ThreadFight.class);

    private ClientEntity client1;
    private ClientEntity client2;
    private List<ThreadFightRound> rounds;
    private Lobby lobby;

    private ObjectInputStream inputClient1;
    private ObjectOutputStream outputClient1;
    private ObjectInputStream inputClient2;
    private ObjectOutputStream outputClient2;

    public ThreadFight(ClientEntity client1, ClientEntity client2, Lobby lobby) {
        this.client1 = client1;
        this.client2 = client2;
        this.lobby = lobby;
        rounds = new ArrayList<ThreadFightRound>();

        inputClient1 = lobby.getClient1Thread().getIn();
        outputClient1 = lobby.getClient1Thread().getOut();
        inputClient2 = lobby.getClient2Thread().getIn();
        outputClient2 = lobby.getClient2Thread().getOut();
        logger.log(Level.INFO, "ThreadFight created.");
    }

    @Override
    public void run() {

        Object client1Turn = null;
        Object client2Turn = null;

        while (true) {
            try {
                client1Turn = inputClient1.readObject();
                client2Turn = inputClient2.readObject();
            } catch (IOException e) {
                System.out.println("------client turn exception1!!!!-----");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("------client turn exception2!!!!-----");
                e.printStackTrace();
            }
            ThreadFightRoundResult result = null;
            if (client1Turn instanceof ThreadFightPlayerTurn && client2Turn instanceof ThreadFightPlayerTurn) {
                ThreadFightRound round = new ThreadFightRound(
                        (ThreadFightPlayerTurn) client1Turn,
                        (ThreadFightPlayerTurn) client2Turn,
                        this);
                result = round.generateAndGetRoundResult();

                try {
                    outputClient2.writeObject(result);
                    outputClient1.writeObject(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("player sent not a turn");
            }

//Check if somebody lost
            checkLosesAndSendInfo(result);

        }

        //!TODO do fight

        /*
        * if on or both player's hp <=0
        * get player 1 and 2 turns
        * apply turn results
        * return round object
        * apply round results on client side
        * continue
        * */


    }

    public ClientEntity getClient1() {
        return client1;
    }

    public ClientEntity getClient2() {
        return client2;
    }

    private ThreadFightRoundResult getRoundResults(ThreadFightPlayerTurn player1Turn, ThreadFightPlayerTurn player2Turn) {
        ThreadFightRound round = new ThreadFightRound(player1Turn, player2Turn, this);
        ThreadFightRoundResult result = round.generateAndGetRoundResult();
        rounds.add(round);
        logger.log(Level.INFO, "ThreadFight.getRoundResults " + round);
        return result;
    }


    private boolean sentPlayerLostObject(ObjectOutputStream output) {
        PlayerLostFightObject playerLost = new PlayerLostFightObject();
        try {
            logger.log(Level.INFO, "ThreadFight.sentPlayerLostObject some player lost " + playerLost);
            output.writeObject(playerLost);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.FATAL, "ThreadFight.sentPlayerLostObject", e);
            return false;
        }
    }

    private void checkLosesAndSendInfo(ThreadFightRoundResult result) {

        if (result.getPlayer1Lost() != null && result.getPlayer2Lost() != null) {
            sentPlayerLostObject(outputClient1);
            sentPlayerLostObject(outputClient2);
        }
        if (result.getPlayer1Lost() != null) {
            sentPlayerLostObject(outputClient1);
        }
        if (result.getPlayer2Lost() != null) {
            sentPlayerLostObject(outputClient2);
        }
    }

}
