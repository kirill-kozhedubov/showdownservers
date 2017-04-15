package iq.ven.showdown.server.impl;

import iq.ven.showdown.client.impl.InitialDataForServerObject;
import iq.ven.showdown.client.impl.LogInErrorObject;
import iq.ven.showdown.client.impl.OnlyOnePlayerInLobbyErrorObject;
import iq.ven.showdown.client.impl.SuccessfulRegistrationObject;
import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.database.setup.DBAuthorizeClient;
import iq.ven.showdown.fighting.impl.Lobby;
import iq.ven.showdown.fighting.impl.ThreadFight;
import iq.ven.showdown.fighting.model.Fight;
import iq.ven.showdown.server.service.FightStarter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by User on 21.03.2017.
 */
public abstract class AbstractThreadClient extends Thread {
    private static final Logger logger = LogManager.getLogger(AbstractThreadClient.class);

    protected Socket socket;
    protected ClientEntity clientEntity;
    protected ObjectOutputStream out = null;
    protected ObjectInputStream in = null;
    protected Lobby lobby;
    protected Fight fight;


    public AbstractThreadClient(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("-------Client dropped out-------");
            e.printStackTrace();
        }

        Object inputObject;
        try {
            Object initialObject = null;
            try {
                while (!tryAuthorize(initialObject)) ;
                //!TODO init entity

            } catch (IOException e) {
                System.out.println("IO WHILE USER TRYS TOM LOGIN");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while (true) {
                try {
                    inputObject = in.readObject();
                    if ((inputObject == null) || inputObject.toString().equalsIgnoreCase("QUIT")) {
                        socket.close();
                        this.interrupt();
                        return;
                    } else {//STARTING POINT OF CLIENT-SERVER REALTIONS

                        System.out.println("I got the package with " + inputObject.toString());
                        System.out.println("Send it back? y/n");


                        Scanner scanner = new Scanner(System.in);
                        if (scanner.nextLine().equalsIgnoreCase("y")) {
                            out.writeObject(inputObject);
                            out.flush();
                            System.out.println("Object sent, waiting");
                        } else {
                            socket.close();
                        }


                    }//ENDING POINT OF CLIENT-SERVER REALTIONS
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.out.println("IOE in thread " + this);
                    try {
                        socket.close();
                        this.interrupt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                    System.out.println("CLASS NOT FOUIND in thread " + this);
                    try {
                        socket.close();
                        this.interrupt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Client error before in while");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                this.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param initialObject - should be null
     */

    protected boolean tryAuthorize(Object initialObject) throws IOException, ClassNotFoundException {

        System.out.println("trying to get data object from client");
        logger.log(Level.DEBUG, "AbstractThreadClient.tryAuthorize started authorize try", initialObject);
        DBAuthorizeClient dbAuthorizeClient = new DBAuthorizeClient();
        initialObject = in.readObject();
        if (initialObject instanceof InitialDataForServerObject) {
            logger.log(Level.DEBUG, "AbstractThreadClient.tryAuthorize got InitialDataForServerObject", initialObject);
            InitialDataForServerObject initdata = (InitialDataForServerObject) initialObject;
            clientEntity = dbAuthorizeClient.authorize(initdata.getUsername(), initdata.getPassword());
            if (clientEntity != null) {
                logger.log(Level.DEBUG, "AbstractThreadClient.tryAuthorize sending ClientEntity to client", clientEntity);
                out.writeObject(clientEntity);
                return true;
            } else {
                logger.log(Level.ERROR, "AbstractThreadClient.tryAuthorize sending LogInErrorObject to client");
                out.writeObject(new LogInErrorObject());
                return false;
            }
        } else if (initialObject instanceof SuccessfulRegistrationObject) {
            logger.log(Level.DEBUG, "AbstractThreadClient.tryAuthorize got SuccessfulRegistrationObject");
            SuccessfulRegistrationObject successfulRegistrationObject = (SuccessfulRegistrationObject) initialObject;
            clientEntity = dbAuthorizeClient.registerAndAuthorize(successfulRegistrationObject);
            logger.log(Level.DEBUG, "AbstractThreadClient.tryAuthorize sending ClientEntity to client after successful registration", clientEntity);
            out.writeObject(clientEntity);
            return true;
        }
        logger.log(Level.ERROR, "AbstractThreadClient.tryAuthorize fatal error, method got rekt");
        return false;
    }


    protected void startFight() throws IOException {
        if (lobby.getClient1() != null && lobby.getClient2() != null) {
            FightStarter fightStarter = new FightStarter(lobby);
            fight = fightStarter.startFight();
            logger.log(Level.DEBUG, "AbstractThreadClient.startFight fight started", fight);
        } else {
            //!TODO show error only 1 player in lobby
            logger.log(Level.ERROR, "AbstractThreadClient.startFight fight didnt started couse only 1 player in lobby OnlyOnePlayerInLobbyErrorObject");
            out.writeObject(new OnlyOnePlayerInLobbyErrorObject());
        }
    }

    protected Lobby createLobby() {
        Lobby lobby = new Lobby(clientEntity, this);
        this.lobby = lobby;
        logger.log(Level.DEBUG, "AbstractThreadClient.createLobby created lobby", lobby);
        return lobby;
    }

    protected void connectToLobby(Lobby lobby) {
        lobby.clientConnect(clientEntity, this);
        this.lobby = lobby;
        logger.log(Level.DEBUG, "AbstractThreadClient.connectToLobby " + clientEntity + " joined lobby", lobby);
    }

    protected void sendClientFight() {
        try {
            out.writeObject(fight);
            logger.log(Level.DEBUG, "AbstractThreadClient.sendClientFight client " + clientEntity.getUsername() + " got the fight", fight);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FAILED TO SEND CLIENT FIGHT");
            logger.log(Level.FATAL, "AbstractThreadClient.sendClientFight IOException happened", e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
    }
}
