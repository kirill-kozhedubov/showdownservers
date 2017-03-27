package iq.ven.showdown.fighting.impl;

import iq.ven.showdown.client.model.Client;
import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.fighting.model.Fight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.03.2017.
 */
public class ThreadFight extends Thread implements Fight {

    private ClientEntity client1;
    private ClientEntity client2;
    private List<ThreadFightRound> rounds;

    public ThreadFight(ClientEntity client1, ClientEntity client2) {
        this.client1 = client1;
        this.client2 = client2;
        rounds = new ArrayList<ThreadFightRound>();


    }

    @Override
    public void run() {
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


    private ThreadFightRoundResult getRoundResults(ThreadFightPlayerTurn player1Turn, ThreadFightPlayerTurn player2Turn) {
        ThreadFightRound round = new ThreadFightRound(player1Turn, player2Turn, this);
        ThreadFightRoundResult result = round.generateAndGetRoundResult();
        rounds.add(round);
        return result;
    }

    public ClientEntity getClient1() {
        return client1;
    }

    public ClientEntity getClient2() {
        return client2;
    }
}
