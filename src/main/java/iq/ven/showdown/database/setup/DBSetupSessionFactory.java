package iq.ven.showdown.database.setup;

import org.hibernate.SessionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by User on 21.03.2017.
 */

@Configuration
public class DBSetupSessionFactory {

    @Bean(name = "sessionFactoryBean")
    SessionFactory sessionFactory() {
        SessionFactory sessionFactory = new org.hibernate.cfg.Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
        return sessionFactory;
    }

}
