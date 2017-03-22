package iq.ven.showdown.server.impl;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Created by User on 21.03.2017.
 */
public abstract class AbstractThreadClient extends Thread {
    protected Socket socket;

    public AbstractThreadClient(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("-------Client dropped out-------");
            e.printStackTrace();
        }

        Object inputObject;
        while (true) {
            try {

                System.out.println("socket reah " + socket.getInetAddress().isReachable(1000));
                inputObject = in.readObject();
                if ((inputObject == null) || inputObject.toString().equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                    System.out.println("I got the package with " + inputObject.toString());
                    System.out.println("Send it back? y/n");

                    if (new Scanner(System.in).nextLine().equalsIgnoreCase("y")) {
                        out.writeObject(inputObject);
                        out.flush();
                        System.out.println("Object sent, waiting");
                    } else {
                       socket.close();
                    }
                }
            } catch (SocketException socketException) {
                socketException.printStackTrace();
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }
}
