package iq.ven.showdown.database.setup;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by User on 21.03.2017.
 */

@Configuration
public class DBSetupSessionFactory {
    private static final Logger logger = Logger.getLogger(DBSetupSessionFactory.class);

    @Bean(name = "sessionFactoryBean")
    SessionFactory sessionFactory() {
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure("/config.xml/hibernate.cfg.xml").buildSessionFactory();
        logger.log(Level.INFO, "DBSetupSessionFactory.sessionFactory Session factory set up complete." + sessionFactory);
        return sessionFactory;
    }

}
