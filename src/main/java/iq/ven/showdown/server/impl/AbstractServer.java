package iq.ven.showdown.server.impl;

import iq.ven.showdown.server.model.Server;

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
    protected List<AbstractThreadClient> clientList = new ArrayList<AbstractThreadClient>();


    public AbstractServer(int port) {
        this.port = port;
    }


    public void startServer() {
        int clientCount = 0;
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Created server socket");
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                System.out.println("Waiting for client...");
                socket = serverSocket.accept();
                ++clientCount;
                System.out.println("Got " + clientCount + "'st client");
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            ThreadClientImpl newClient = new ThreadClientImpl(socket);
            clientList.add(newClient);
            newClient.start();
        }
    }
}



