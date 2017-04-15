package iq.ven.showdown.database;

import iq.ven.showdown.fighting.model.Armor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 21.03.2017.
 */
@Entity(name = "armor")
public class ArmorEntity implements Armor, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "hp", nullable = false)
    private int hitPoints;
    @Column(name = "armor", nullable = false)
    private int armorPercent;
    @Column(name = "imglink", nullable = false)/*, insertable = false*/
    private String imgLink = "http://i.imgur.com/xm2Bikw.jpg";


    public ArmorEntity() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getArmorPercent() {
        return armorPercent;
    }

    public void setArmorPercent(int armorPercent) {
        this.armorPercent = armorPercent;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Override
    public String toString() {
        return "ArmorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hitPoints=" + hitPoints +
                ", armorPercent=" + armorPercent +
                ", imgLink='" + imgLink + '\'' +
                '}';
    }
}
