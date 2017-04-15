package iq.ven.showdown.database.setup;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by User on 21.03.2017.
 */

@Configuration
public class DBSetupSessionFactory {
    private static final Logger logger = LogManager.getLogger(DBSetupSessionFactory.class);

    @Bean(name = "sessionFactoryBean")
    SessionFactory sessionFactory() {
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
        logger.log(Level.TRACE, "DBSetupSessionFactory.sessionFactory Session factory set up complete.", sessionFactory);
        return sessionFactory;
    }

}
