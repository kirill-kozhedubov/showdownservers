package iq.ven.showdown.fighting.impl;

import iq.ven.showdown.database.ClientEntity;

import java.io.Serializable;

/**
 * Created by User on 25.03.2017.
 */
public class ThreadFightRoundResult implements Serializable {
    private ThreadFightRound round;
    private int player1HpChange;
    private int player2HpChange;
    private ClientEntity player1Lost;
    private ClientEntity player2Lost;

    //p1stats
    private int p1Attack;
    private int p1Block;
    private int p1Damage;
    private int p1Hp;
    private int p1Protection;

    //p2stats
    private int p2Attack;
    private int p2Block;
    private int p2Damage;
    private int p2Hp;
    private int p2Protection;


    public ThreadFightRoundResult(ThreadFightRound round) {
        this.round = round;

        p1Attack = round.getPlayer1Turn().getAttack();
        p1Block = round.getPlayer1Turn().getBlock();
        p1Damage = round.getPlayer1().getHero().getDmg();
        p1Hp = round.getPlayer1().getHero().getHp();
        p1Protection = round.getPlayer1().getHero().getProtection();

        p2Attack = round.getPlayer2Turn().getAttack();
        p2Block = round.getPlayer2Turn().getBlock();
        p2Damage = round.getPlayer2().getHero().getDmg();
        p2Hp = round.getPlayer2().getHero().getHp();
        p2Protection = round.getPlayer2().getHero().getProtection();


        player1HpChange = calculatePlayer1HpChange();
        player2HpChange = calculatePlayer2HpChange();

        round.getPlayer1().getHero().setHp(p1Hp - player1HpChange);
        round.getPlayer2().getHero().setHp(p2Hp - player2HpChange);

        if (round.getPlayer1().getHero().getHp() <= 0) {
            player1Lost = round.getPlayer1();
        }
        if (round.getPlayer2().getHero().getHp() <= 0) {
            player2Lost = round.getPlayer2();
        }
    }

    private int calculatePlayer1HpChange() {
        int hpchange = 0;
        boolean attackPassed = false;
        if (p2Attack == p1Block) {
            return hpchange;
        } else {
            attackPassed = true;
        }
        if (attackPassed) {
            float playersProtection = (p1Protection / 100);
            int dmgThatWillGetMitigated = (p2Damage * (int) playersProtection);
            hpchange = p2Damage - dmgThatWillGetMitigated;
        }
        return hpchange;
    }

    private int calculatePlayer2HpChange() {
        int hpchange = 0;
        boolean attackPassed = false;
        if (p1Attack == p2Block) {
            return hpchange;
        } else {
            attackPassed = true;
        }
        if (attackPassed) {
            float playersProtection = (p2Protection / 100);
            int dmgThatWillGetMitigated = (p1Damage * (int) playersProtection);
            hpchange = p1Damage - dmgThatWillGetMitigated;
        }
        return hpchange;
    }

    @Override
    public String toString() {
        String p1name = round.getPlayer1().getUsername();
        String p2name = round.getPlayer2().getUsername();
        String p1lose = "\n" + p1name + " lost";
        String p2lose = "\n" + p2name + " lost";
        String twoLost = "\n" + "DRAW";
        String loseMsg = "";

        if (player1Lost != null && player2Lost != null) {
            loseMsg = twoLost;
        } else if (player1Lost != null) {
            loseMsg = p1lose;
        } else if (player2Lost != null) {
            loseMsg = p2lose;
        }
        return p1name + " attacked " + p1Attack + "and blocked " + p1Block + " and did " + player2HpChange + " damage.\n"
                + p2name + " attacked " + p2Attack + "and blocked " + p2Block + " and did " + player1HpChange + " damage." + loseMsg;
    }
}


