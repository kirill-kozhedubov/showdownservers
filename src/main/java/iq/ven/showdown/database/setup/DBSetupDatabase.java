package iq.ven.showdown.database.setup;


import iq.ven.showdown.database.*;
import iq.ven.showdown.database.setup.DBSetupSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by User on 21.03.2017.
 */
public class DBSetupDatabase {

    private SessionFactory sessionFactory;

    public DBSetupDatabase() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DBSetupSessionFactory.class);
        sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactoryBean");
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setUpDatabaseColumns() {

        SessionFactory sessionFactory = getSessionFactory();

        ArmorEntity armor = getTestArmor();
        WeaponEntity weapon = getTestWeapon();
        HeroArchetypeEntity heroArch = getTestHeroArchetype();
        HeroEntity hero = getTestHero(armor, weapon, heroArch);
        ClientEntity client = getTestClientEntity(hero);



        Session session = null;
        try {


            session = sessionFactory.openSession();
            session.beginTransaction(); // begin transaction

            session.save(armor); //armor entity setup
            session.save(weapon); //weapon entity setup
            session.save(heroArch); //hero archetype entity setup
            session.save(hero); //hero entity setup
            session.save(client);//client entity setup


            session.getTransaction().commit();// commit transaction
        } finally {
            session.close(); // better use in finally block
            //System.exit(1488);
        }


    DBInitialDataSetup dbInitialDataSetup = new DBInitialDataSetup(sessionFactory);
    dbInitialDataSetup.createInstantialData();


    }

    private ArmorEntity getTestArmor() {
        ArmorEntity armor = new ArmorEntity();
        armor.setArmorPercent(10);
        armor.setHitPoints(100);
        armor.setName("THE GREAT ARMOR of THE IMMORTAL");
        return armor;
    }

    private WeaponEntity getTestWeapon() {
        WeaponEntity weapon = new WeaponEntity();
        weapon.setDmg(20);
        weapon.setName("Great weapon xd");
        return weapon;
    }


    private HeroEntity getTestHero(ArmorEntity armor, WeaponEntity weapon, HeroArchetypeEntity heroArchetype) {
        HeroEntity hero = new HeroEntity();
        hero.setName("name");
        hero.setArmor(armor);
        hero.setWeapon(weapon);
        hero.setHeroArchetype(heroArchetype);
        hero.heroInitialize();
        return hero;
    }

    private HeroArchetypeEntity getTestHeroArchetype() {
        HeroArchetypeEntity heroArchetypeEntity = new HeroArchetypeEntity();
        heroArchetypeEntity.setName("maurman");
        return heroArchetypeEntity;
    }

    private ClientEntity getTestClientEntity(HeroEntity hero) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setHero(hero);
        clientEntity.setUsername("name");
        clientEntity.setPassword("password");
        return clientEntity;
    }
}
