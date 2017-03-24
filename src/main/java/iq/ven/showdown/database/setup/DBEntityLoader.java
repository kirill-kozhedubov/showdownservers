package iq.ven.showdown.database.setup;

import iq.ven.showdown.database.ArmorEntity;
import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.database.HeroArchetypeEntity;
import iq.ven.showdown.database.WeaponEntity;
import iq.ven.showdown.server.main.Main;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by User on 24.03.2017.
 */
public class DBEntityLoader {

    private SessionFactory sessionFactory = Main.getDBSetupDatabase().getSessionFactory();


    public ArmorEntity loadArmorFromName(String armorname) {
        Session session = sessionFactory.openSession();
        String queryString = "FROM iq.ven.showdown.database.ArmorEntity A WHERE A.name = :armorname";
        Query query = session.createQuery(queryString);
        query.setParameter("armorname", armorname);
        List<ArmorEntity> results = query.list();
        session.close();
        if (results.size() >= 1) {
            return results.get(0);
        }

        return null;
    }

    public WeaponEntity loadWeaaponFromName(String weaponname) {
        Session session = sessionFactory.openSession();
        String queryString = "FROM iq.ven.showdown.database.WeaponEntity W WHERE W.name = :weaponname";
        Query query = session.createQuery(queryString);
        query.setParameter("weaponname", weaponname);
        List<WeaponEntity> results = query.list();
        session.close();
        if (results.size() >= 1) {
            return results.get(0);
        }

        return null;
    }

    public HeroArchetypeEntity loadHeroArchetypeFromName(String archname) {
        Session session = sessionFactory.openSession();
        String queryString = "FROM iq.ven.showdown.database.HeroArchetypeEntity W WHERE W.name = :archname";
        Query query = session.createQuery(queryString);
        query.setParameter("archname", archname);
        List<HeroArchetypeEntity> results = query.list();
        session.close();
        if (results.size() >= 1) {
            return results.get(0);
        }

        return null;
    }

}
