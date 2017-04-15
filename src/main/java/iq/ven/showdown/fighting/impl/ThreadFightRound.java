package iq.ven.showdown.fighting.impl;

import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.fighting.model.Round;

import java.io.Serializable;

/**
 * Created by User on 21.03.2017.
 */
public class ThreadFightRound implements Round, Serializable {

    private ThreadFightPlayerTurn player1Turn;
    private ThreadFightPlayerTurn player2Turn;
    private ClientEntity player1;
    private ClientEntity player2;
    private ThreadFightRoundResult roundResult;

    public ThreadFightRound(ThreadFightPlayerTurn player1Turn, ThreadFightPlayerTurn player2Turn, ThreadFight threadFight) {
        this.player1Turn = player1Turn;
        this.player2Turn = player2Turn;
        this.player1 = threadFight.getClient1();
        this.player2 = threadFight.getClient2();
    }

    public ThreadFightPlayerTurn getPlayer1Turn() {
        return player1Turn;
    }


    public ThreadFightPlayerTurn getPlayer2Turn() {
        return player2Turn;
    }

    public ClientEntity getPlayer1() {
        return player1;
    }

    public ClientEntity getPlayer2() {
        return player2;
    }

    public ThreadFightRoundResult generateAndGetRoundResult() {
        roundResult = new ThreadFightRoundResult(this);
        return roundResult;
    }

    @Override
    public String toString() {
        return "ThreadFightRound{" +
                "player1Turn=" + player1Turn +
                ", player2Turn=" + player2Turn +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", roundResult=" + roundResult +
                '}';
    }
}
