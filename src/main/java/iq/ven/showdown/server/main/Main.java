package iq.ven.showdown.server.main;


import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.database.setup.DBAuthorizeClient;
import iq.ven.showdown.database.setup.DBSetupDatabase;
import iq.ven.showdown.database.setup.DBSetupSessionFactory;
import iq.ven.showdown.server.impl.ServerImpl;
import iq.ven.showdown.server.service.PropertyInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by User on 21.03.2017.
 */
public class Main {
    private static DBSetupDatabase dbSetupDatabase = new DBSetupDatabase();

    public static void main(String[] args) {
        /*ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PropertyInitializer.class);
        PropertyInitializer propertyInitializer = (PropertyInitializer) applicationContext.getBean("serverInfoBean");
        System.out.println("Server data established. port:" + propertyInitializer.getPort() + " ip: " + propertyInitializer.getServer());


        ServerImpl server = new ServerImpl(propertyInitializer.getPort());
        server.startServer();*/

        getDBSetupDatabase().setUpDatabaseColumns();


/*        DBAuthorizeClient dbAuthorizeClient = new DBAuthorizeClient();
        ClientEntity clientEntity = dbAuthorizeClient.authorize("name", "password");
*/




    }

    public static DBSetupDatabase getDBSetupDatabase() {
        return dbSetupDatabase;
    }
}
