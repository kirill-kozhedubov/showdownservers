package iq.ven.showdown.database;

import iq.ven.showdown.fighting.model.Weapon;

import javax.persistence.*;

/**
 * Created by User on 21.03.2017.
 */

@Entity(name = "weapon")
public class WeaponEntity implements Weapon {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "damage", nullable = false)
    private int dmg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
}
