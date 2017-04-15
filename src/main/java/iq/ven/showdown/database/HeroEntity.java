package iq.ven.showdown.database;

import iq.ven.showdown.fighting.model.Hero;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 21.03.2017.
 */
@Entity(name = "hero")
public class HeroEntity implements Hero, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "hero_archetype_id", nullable = false)
    private HeroArchetypeEntity heroArchetype;

    @OneToOne
    @JoinColumn(name = "armor_id", nullable = false)
    private ArmorEntity armor;

    @OneToOne
    @JoinColumn(name = "weapon_id", nullable = false)
    private WeaponEntity weapon;


    @Transient
    private int dmg;
    @Transient
    private int protection;
    @Transient
    private int hp;
    @Transient
    private String armorName;
    @Transient
    private String weaponName;


    public void heroInitialize() {
        this.dmg = weapon.getDmg();
        this.protection = armor.getArmorPercent();
        this.hp = armor.getHitPoints();
        this.armorName = armor.getName();
        this.weaponName = weapon.getName();
    }


    //POJO Methods

    public String getArmorName() {
        return armorName;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public int getDmg() {
        return dmg;
    }

    public int getProtection() {
        return protection;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    //Entity Methods


    public HeroArchetypeEntity getHeroArchetype() {
        return heroArchetype;
    }

    public void setHeroArchetype(HeroArchetypeEntity heroArchetype) {
        this.heroArchetype = heroArchetype;
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

    public ArmorEntity getArmor() {
        return armor;
    }

    public void setArmor(ArmorEntity armor) {
        this.armor = armor;
    }

    public WeaponEntity getWeapon() {
        return weapon;
    }

    public void setWeapon(WeaponEntity weapon) {
        this.weapon = weapon;
    }

    @Override
    public String toString() {
        return "HeroEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", heroArchetype=" + heroArchetype +
                ", armor=" + armor +
                ", weapon=" + weapon +
                ", dmg=" + dmg +
                ", protection=" + protection +
                ", hp=" + hp +
                ", armorName='" + armorName + '\'' +
                ", weaponName='" + weaponName + '\'' +
                '}';
    }
}
