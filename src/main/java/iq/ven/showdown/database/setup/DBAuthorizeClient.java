package iq.ven.showdown.database.setup;

import iq.ven.showdown.client.impl.SuccessfulRegistrationObject;
import iq.ven.showdown.database.*;
import iq.ven.showdown.fighting.model.HeroArchetype;
import iq.ven.showdown.server.main.Main;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;


/**
 * Created by User on 24.03.2017.
 */
public class DBAuthorizeClient {
    private SessionFactory sessionFactory = Main.getDBSetupDatabase().getSessionFactory();

    public ClientEntity authorize(String username, String password) {
        Session session = sessionFactory.openSession();
        String queryString = "FROM iq.ven.showdown.database.ClientEntity C WHERE C.username = :username AND C.password = :password";
        Query query = session.createQuery(queryString);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<ClientEntity> results = query.list();
        session.close();
        if (results.size() == 1) {

            return results.get(0);
        }

        return null;
    }

    public ClientEntity registerAndAuthorize(SuccessfulRegistrationObject successfulRegistrationObject) {
        ClientEntity clientEntity = new ClientEntity();
        DBEntityLoader dbEntityLoader = new DBEntityLoader();


        ArmorEntity armorEntity = dbEntityLoader.loadArmorFromName(successfulRegistrationObject.getHeroArmorName());
        WeaponEntity weaponEntity = dbEntityLoader.loadWeaaponFromName(successfulRegistrationObject.getHeroWeaponName());
        HeroArchetypeEntity heroArchetypeEntity = dbEntityLoader.loadHeroArchetypeFromName(successfulRegistrationObject.getHeroArchName());

        HeroEntity heroEntity = new HeroEntity();
        heroEntity.setHeroArchetype(heroArchetypeEntity);
        heroEntity.setName(successfulRegistrationObject.getUsername());
        heroEntity.setArmor(armorEntity);
        heroEntity.setWeapon(weaponEntity);


        clientEntity.setUsername(successfulRegistrationObject.getUsername());
        clientEntity.setPassword(successfulRegistrationObject.getPassword());
        clientEntity.setHero(heroEntity);


        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(heroEntity);
            session.save(clientEntity);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return clientEntity;
    }

}
