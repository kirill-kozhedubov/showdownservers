package iq.ven.showdown.fighting.model;


import iq.ven.showdown.database.ArmorEntity;
import iq.ven.showdown.database.HeroArchetypeEntity;
import iq.ven.showdown.database.WeaponEntity;

/**
 * Created by User on 21.03.2017.
 */

public interface Hero {

    String getName();

    WeaponEntity getWeapon();

    ArmorEntity getArmor();

    int getDmg();

    int getHp();

    int getProtection();

    String getArmorName();

    String getWeaponName();

    HeroArchetypeEntity getHeroArchetype();

}
