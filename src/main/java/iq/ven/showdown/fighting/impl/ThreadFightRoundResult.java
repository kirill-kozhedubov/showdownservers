package iq.ven.showdown.fighting.impl;

import java.io.Serializable;

/**
 * Created by User on 25.03.2017.
 */
public class ThreadFightRoundResult implements Serializable {
    private ThreadFightRound round;
    private int player1HpChange;
    private int player2HpChange;

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
        return "Player 1 attacked " + p1Attack + "and blocked " + p1Block + " and did " + player2HpChange + " damage."
                + "\nPlayer 2 attacked " + p2Attack + "and blocked " + p2Block + " and did " + player1HpChange + " damage.";
    }
}


