package iq.ven.showdown.server.impl;

import iq.ven.showdown.client.impl.InitialDataForServerObject;
import iq.ven.showdown.client.impl.LogInErrorObject;
import iq.ven.showdown.client.impl.OnlyOnePlayerInLobbyErrorObject;
import iq.ven.showdown.client.impl.SuccessfulRegistrationObject;
import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.database.setup.DBAuthorizeClient;
import iq.ven.showdown.fighting.impl.Lobby;
import iq.ven.showdown.fighting.model.Fight;

import javax.persistence.Lob;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by User on 21.03.2017.
 */
public abstract class AbstractThreadClient extends Thread {
    protected Socket socket;
    protected ClientEntity clientEntity;
    protected ObjectOutputStream out = null;
    protected ObjectInputStream in = null;
    protected Lobby lobby;
    protected Fight fight;


    public AbstractThreadClient(Socket clientSocket) {
        this.socket = clientSocket;
    }

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

        } catch (
                Exception e)

        {
            System.out.println("Client error before in while");
            e.printStackTrace();
        } finally

        {
            try {
                socket.close();
                this.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean tryAuthorize(Object initialObject) throws IOException, ClassNotFoundException {
        System.out.println("trying to get data object from client");
        DBAuthorizeClient dbAuthorizeClient = new DBAuthorizeClient();
        initialObject = in.readObject();
        if (initialObject instanceof InitialDataForServerObject) {
            InitialDataForServerObject initdata = (InitialDataForServerObject) initialObject;
            clientEntity = dbAuthorizeClient.authorize(initdata.getUsername(), initdata.getPassword());
            if (clientEntity != null) {
                System.out.println("okok client passed");
                out.writeObject(clientEntity);
                return true;
            } else {
                out.writeObject(new LogInErrorObject());
                return false;
            }
        } else if (initialObject instanceof SuccessfulRegistrationObject) {
            SuccessfulRegistrationObject successfulRegistrationObject = (SuccessfulRegistrationObject) initialObject;
            clientEntity = dbAuthorizeClient.registerAndAuthorize(successfulRegistrationObject);
            out.writeObject(clientEntity);
            return true;
        }
        return false;
    }


    protected void startFight() throws IOException {
        if (lobby.getClient1() != null && lobby.getClient2() != null) {
            FightStarter fightStarter = new FightStarter(lobby);
            fight = fightStarter.startFight();
        } else {
            //!TODO show error only 1 player in lobby
            out.writeObject(new OnlyOnePlayerInLobbyErrorObject());
        }
    }

    protected Lobby createLobby() {
        Lobby lobby = new Lobby(clientEntity, this);
        this.lobby = lobby;
        return lobby;
        //send lobby to user;
    }

    protected void connectToLobby(Lobby lobby) {
        lobby.clientConnect(clientEntity, this);
        this.lobby = lobby;
        //send lobby to user;
    }

    protected void sendClientFight() {
        try {
            out.writeObject(fight);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FAILED TO SEND CLIENT FIGHT");
        }
    }


}
