package iq.ven.showdown.server.impl;

import iq.ven.showdown.server.model.Server;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.03.2017.
 */
public abstract class AbstractServer implements Server {

    protected int port;
    protected List<AbstractThreadClient> clientList = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(AbstractServer.class);

    public AbstractServer(int port) {
        this.port = port;
    }


    public void startServer() {
        int clientCount = 0;
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
            logger.log(Level.INFO, "AbstractServer.startServer ServerSocket created (server started), port:" + port);
        } catch (IOException e) {
            logger.log(Level.FATAL, "AbstractServer.startServer ServerSocket io exception", e);
            e.printStackTrace();
        }

        while (true) {
            try {
                logger.log(Level.INFO, "AbstractServer.startServer Server started client waiting procedure");
                socket = serverSocket.accept();
                ++clientCount;
                System.out.println("Got " + clientCount + "'st client");
                logger.log(Level.INFO, "AbstractServer.startServer A client #" + clientCount + " connected to server");
            } catch (IOException e) {
                logger.log(Level.FATAL, "AbstractServer.startServer Error while trying to connect client happened", e);
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            ThreadClientImpl newClient = new ThreadClientImpl(socket);
            clientList.add(newClient);
            newClient.start();
            logger.log(Level.INFO, "AbstractServer.startServer Thread for client created and started " + newClient);
        }
    }
}



