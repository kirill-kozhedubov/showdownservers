package iq.ven.showdown.server.impl;

import iq.ven.showdown.client.impl.InitialDataForServerObject;
import iq.ven.showdown.client.impl.SuccessfulRegistrationObject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by User on 15.04.2017.
 */
public class ObjectFromClientParser {
    private static final Logger logger = LogManager.getLogger(AbstractThreadClient.class);

    void parseInputObject(Object object) {
        if (object instanceof SuccessfulRegistrationObject) {
            logger.log(Level.DEBUG, "Got SuccessfulRegistrationObject from client", object);
        } else if (object instanceof InitialDataForServerObject) {
            logger.log(Level.DEBUG, "Got InitialDataForServerObject from client", object);
        }
    }


}
