package iq.ven.showdown.fighting.impl;

import iq.ven.showdown.fighting.model.PlayerTurn;

import java.io.Serializable;

/**
 * Created by User on 21.03.2017.
 */
public class ThreadFightPlayerTurn implements PlayerTurn, Serializable {

    /**
     * attack :
     * head = 0
     * chest = 1
     * legs = 2
     * <p>
     * block :
     * head = 0
     * chest = 1
     * legs = 2
     */


    private int attack;
    private int block;

    public ThreadFightPlayerTurn(int attack, int block) {
        this.attack = attack;
        this.block = block;
    }

    public int getAttack() {
        return attack;
    }

    public int getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return "ThreadFightPlayerTurn{" +
                "attack=" + attack +
                ", block=" + block +
                '}';
    }
}
