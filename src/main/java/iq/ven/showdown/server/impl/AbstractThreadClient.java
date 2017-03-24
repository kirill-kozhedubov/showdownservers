package iq.ven.showdown.server.impl;

import iq.ven.showdown.client.impl.InitialDataForServerObject;
import iq.ven.showdown.client.impl.LogInErrorObject;
import iq.ven.showdown.client.impl.SuccessfulRegistrationObject;
import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.database.setup.DBAuthorizeClient;

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
        initialObject = in.readObject();
        if (initialObject instanceof InitialDataForServerObject) {
            InitialDataForServerObject initdata = (InitialDataForServerObject) initialObject;
            DBAuthorizeClient dbAuthorizeClient = new DBAuthorizeClient();
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

        }
        return false;
    }
}
