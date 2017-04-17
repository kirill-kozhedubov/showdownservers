package iq.ven.showdownclient.view;

import iq.ven.showdown.client.impl.*;
import iq.ven.showdown.client.model.Client;
import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.fighting.impl.ThreadFight;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by User on 22.03.2017.
 */
public class ClientImpl extends Thread implements Client {
    private int port;
    private String serverIp;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ClientEntity clientEntity;
    private static final Logger logger = Logger.getLogger(ClientImpl.class);
    public static ThreadFight threadFight;

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

    public void initServerData() {
        try {
            File xmlFile = new File(this.getClass().getClassLoader().getResource("config.xml/clientserver.config.xml").toURI());
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("server");
            Node node = nodeList.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                this.port = Integer.parseInt(element.getElementsByTagName("port").item(0).getTextContent());
                this.serverIp = element.getElementsByTagName("ip").item(0).getTextContent();
            }
            logger.log(Level.INFO, "ClientImpl.initServerData initiated " + port + " " + serverIp);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "ClientImpl.initServerData exception in initializing server data ", e);
        }
    }

    @Override
    public void run() {
        //initial connect and shit...
        try {
            System.out.println("ip: " + serverIp + " port: " + port);
            InetAddress ipAddress = InetAddress.getByName(serverIp); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Trying to connect..");
            socket = new Socket(ipAddress, port); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just connected!.");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            logger.log(Level.INFO, "ClientImpl.startClient connection established!");
            Object inputObject = null;
            try {
                //  while (true) {
                //  tryToLogin("lul", "xd");
                //  inputObject = in.readObject();
                //   logger.log(Level.INFO, "ClientImpl.startClient got an object " + inputObject);
                //  }
            } catch (Exception e) {
                logger.log(Level.INFO, "ClientImpl.startClient Exception " + inputObject);
            }


        } catch (Exception x) {
            logger.log(Level.FATAL, "ClientImpl.startClient Exception ", x);
            x.printStackTrace();
        }
    }// end startClient()

    public boolean tryToLogin(String username, String password) throws IOException, ClassNotFoundException {
        InitialDataForServerObject sendDataToServer = new InitialDataForServerObject(username, password);
        out.writeObject(sendDataToServer);
        logger.log(Level.INFO, "ClientImpl.tryToLogin object sent " + sendDataToServer);
        Object objectFromServer = in.readObject();
        logger.log(Level.INFO, "ClientImpl.tryToLogin object got " + objectFromServer);
        if (objectFromServer instanceof LogInErrorObject) {
            return false;
        } else if (objectFromServer instanceof ClientEntity) {
            clientEntity = (ClientEntity) objectFromServer;
            return true;
        }
        return false;
    }

    public boolean registerAndLogin(String username, String password, String heroArchName, String heroArmorName, String heroWeaponName) throws IOException, ClassNotFoundException {
        SuccessfulRegistrationObject successfulRegistrationObject =
                new SuccessfulRegistrationObject(username, password, heroArchName, heroArmorName, heroWeaponName);
        out.writeObject(successfulRegistrationObject);
        logger.log(Level.INFO, "ClientImpl.registerAndLogin object sent " + successfulRegistrationObject);
        Object objectFromServer = in.readObject();
        logger.log(Level.INFO, "ClientImpl.tryToLogin object got " + objectFromServer);
        if (objectFromServer instanceof ClientEntity) {
            clientEntity = (ClientEntity) objectFromServer;
            return true;
        }
        return false;
    }

    public boolean joinLobby() {
        try {
            logger.log(Level.INFO, "ClientImpl.joinLobby sending JoinLobbyObject");
            out.writeObject(new JoinLobbyObject());
            logger.log(Level.INFO, "ClientImpl.joinLobby JoinLobbyObject sent!");
            Object object = in.readObject();
            logger.log(Level.INFO, "ClientImpl.joinLobby got " + object);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createLobby() {
        try {
            logger.log(Level.INFO, "ClientImpl.createLobby sending CreateLobbyObject");
            out.writeObject(new CreateLobbyObject());
            logger.log(Level.INFO, "ClientImpl.createLobby CreateLobbyObject sent!");
            Object object = in.readObject();
            logger.log(Level.INFO, "ClientImpl.createLobby got " + object);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ThreadFight startFight() {
         threadFight = null;
        try {
            logger.log(Level.INFO, "startFight.startFight sending StartFightObject");
            out.writeObject(new FightStartObject());
            logger.log(Level.INFO, "startFight.startFight StartFightObject sent! waiting for fight object");
            threadFight = (ThreadFight) in.readObject();
            logger.log(Level.INFO, "startFight.startFight got Fight object " + threadFight);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return threadFight;
    }


    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }
}
