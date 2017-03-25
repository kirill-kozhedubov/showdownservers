package iq.ven.showdown.database;

import iq.ven.showdown.fighting.model.Weapon;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 21.03.2017.
 */

@Entity(name = "weapon")
public class WeaponEntity implements Weapon, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "damage", nullable = false)
    private int dmg;
    @Column(name = "imglink", nullable = false)
    private String imgLink = "http://i.imgur.com/NbzvoRd.jpg";


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

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
