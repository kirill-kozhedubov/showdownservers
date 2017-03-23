package iq.ven.showdown.client.impl;

import iq.ven.showdown.client.model.Client;

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
    private  Socket socket;
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
        try {
            System.out.println(serverIp + " " + port);
            InetAddress ipAddress = InetAddress.getByName(serverIp); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Any of you heard of a socket with IP address " + serverIp + " and port " + port + "?");
            socket = new Socket(ipAddress, port); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just got hold of the program.");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.


            // Конвертируем потоки в другой тип, чтоб легче обрабатывать обьекты.
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());


            Scanner scanner = new Scanner(System.in);
            while (true) {
                String str = scanner.nextLine();

                TestObjToSend food = new TestObjToSend();
                out.writeObject(food);
                out.flush();
                System.out.println("Sending " + food);


                System.out.println("Geting it back from server");
                TestObjToSend foodIGet = (TestObjToSend) in.readObject();
                System.out.println("We got " + foodIGet);

                System.out.println("Waiting for next trade");
                System.out.println();

            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
