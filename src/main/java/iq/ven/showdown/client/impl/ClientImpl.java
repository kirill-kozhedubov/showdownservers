package iq.ven.showdown.client.impl;

import iq.ven.showdown.client.model.Client;
import iq.ven.showdown.database.ClientEntity;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by User on 22.03.2017.
 */
public class ClientImpl implements Client {
    private int port;
    private String serverIp;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
/*    public static void main(String[] args) {
        ClientImpl client = new ClientImpl();
        client.initServerData();
        client.startClient();
    }*/

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    private void initServerData() {
        this.port = 1488;
        this.serverIp = "127.0.0.1";
    }

    private void startClient() {

        //initial connect and shit...
        try {
            System.out.println("ip: " + serverIp + " port: " + port);
            InetAddress ipAddress = InetAddress.getByName(serverIp); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Trying to connect..");
            socket = new Socket(ipAddress, port); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just connected!.");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Any key to continue");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
        } catch (Exception x) {
            x.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //login or registration
        boolean logon = false;
        while (!logon) {
            try {
                logon = tryToLogin("username", "password");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }// end startClient()

    private boolean tryToLogin(String username, String password) throws IOException, ClassNotFoundException {
        InitialDataForServerObject sendDataToServer = new InitialDataForServerObject(username, password);
        out.writeObject(sendDataToServer);
        Object objectFromServer = in.readObject();
        if (objectFromServer instanceof LogInErrorObject) {
            return false;
        } else if (objectFromServer instanceof ClientEntity) {
            return true;
        }
        return false;
    }


}
