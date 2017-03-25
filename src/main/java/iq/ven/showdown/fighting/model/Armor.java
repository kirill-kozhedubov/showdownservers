package iq.ven.showdown.fighting.model;

/**
 * Created by User on 21.03.2017.
 */
public interface Armor  extends Imagible{

    String getName();

    int getHitPoints();

    void setHitPoints(int hitPoints);

/*    int getDodgePercent();*/

    int getArmorPercent();

}
