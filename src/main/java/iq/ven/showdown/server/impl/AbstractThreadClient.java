package iq.ven.showdown.server.impl;

import iq.ven.showdown.client.impl.InitialDataForServerObject;
import iq.ven.showdown.client.impl.LogInErrorObject;
import iq.ven.showdown.client.impl.SuccessfulRegistrationObject;
import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.database.setup.DBAuthorizeClient;
import iq.ven.showdown.fighting.impl.Lobby;
import iq.ven.showdown.fighting.model.Fight;
import iq.ven.showdown.server.service.FightStarter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.03.2017.
 */
public abstract class AbstractThreadClient extends Thread implements Serializable {
    private static final Logger logger = Logger.getLogger(AbstractThreadClient.class);

    public static List<Lobby> lobbyList = new ArrayList<>();
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
            logger.log(Level.FATAL, "AbstractThreadClient.run  error in getting output and input streams");
            this.interrupt();
        }
        ObjectFromClientParser objectFromClientParser = new ObjectFromClientParser(out, in, this);
        try {
            while (true) {
                logger.log(Level.INFO, "AbstractThreadClient.run WAITING FOR CLIENT INPUTS");
                Object inputObject = in.readObject();
                logger.log(Level.INFO, "AbstractThreadClient.run SERVER GOT OBJECT " + inputObject);
                objectFromClientParser.parseInputObject(inputObject);
            }
        } catch (IOException e) {
            logger.log(Level.FATAL, "AbstractThreadClient.run  IO Error in loop of server-client things");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "AbstractThreadClient.run  ClassNotFound Error in loop of server-client things");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                logger.log(Level.FATAL, "AbstractThreadClient.run-finally  socket.close() ok");
            } catch (IOException e) {
                logger.log(Level.FATAL, "AbstractThreadClient.run-finally  socket.close() exception");
                e.printStackTrace();
            }
        }

    }

    /**
     * @param initialObject - should be null
     */

    public boolean tryAuthorize(Object initialObject) throws IOException, ClassNotFoundException {
        logger.log(Level.INFO, "AbstractThreadClient.tryAuthorize started authorize try " + initialObject);
        DBAuthorizeClient dbAuthorizeClient = new DBAuthorizeClient();
        if (initialObject instanceof InitialDataForServerObject) {
            logger.log(Level.INFO, "AbstractThreadClient.tryAuthorize got InitialDataForServerObject " + initialObject);
            InitialDataForServerObject initdata = (InitialDataForServerObject) initialObject;
            clientEntity = dbAuthorizeClient.authorize(initdata.getUsername(), initdata.getPassword());
            if (clientEntity != null) {
                logger.log(Level.INFO, "AbstractThreadClient.tryAuthorize sending ClientEntity to client " + clientEntity);
                out.writeObject(clientEntity);
                return true;
            } else {
                logger.log(Level.ERROR, "AbstractThreadClient.tryAuthorize sending LogInErrorObject to client");
                out.writeObject(new LogInErrorObject());
                return false;
            }
        } else if (initialObject instanceof SuccessfulRegistrationObject) {
            logger.log(Level.INFO, "AbstractThreadClient.tryAuthorize got SuccessfulRegistrationObject");
            SuccessfulRegistrationObject successfulRegistrationObject = (SuccessfulRegistrationObject) initialObject;
            clientEntity = dbAuthorizeClient.registerAndAuthorize(successfulRegistrationObject);
            logger.log(Level.INFO, "AbstractThreadClient.tryAuthorize sending ClientEntity to client after successful registration " + clientEntity);
            out.writeObject(clientEntity);
            return true;
        }
        logger.log(Level.ERROR, "AbstractThreadClient.tryAuthorize fatal error, method got rekt");
        return false;
    }


    public void startFight() throws IOException {
        // if (lobby.getClient1() != null && lobby.getClient2() != null) {
        FightStarter fightStarter = new FightStarter(lobby);
        fight = fightStarter.startFight();
        logger.log(Level.INFO, "AbstractThreadClient.startFight fight started " + fight);
        lobby.getClient1Thread().getOut().writeObject(fight);
        logger.log(Level.INFO, "AbstractThreadClient.startFight fight sent to  " + lobby.getClient1().getUsername());
        lobby.getClient2Thread().getOut().writeObject(fight);
        logger.log(Level.INFO, "AbstractThreadClient.startFight fight started " + lobby.getClient2().getUsername());
       /* } else {
            //!TODO show error only 1 player in lobby
            logger.log(Level.ERROR, "AbstractThreadClient.startFight fight didnt started couse only 1 player in lobby OnlyOnePlayerInLobbyErrorObject");
            out.writeObject(new OnlyOnePlayerInLobbyErrorObject());
        }*/
    }

    public Lobby createLobby() {
        Lobby lobby = new Lobby(clientEntity, this);
        this.lobby = lobby;
        logger.log(Level.INFO, "AbstractThreadClient.createLobby created lobby " + lobby);
        lobbyList.add(lobby);
        return lobby;
    }

    public void connectToLobby(Lobby lobby) {
        lobby.clientConnect(clientEntity, this);
        this.lobby = lobby;
        logger.log(Level.INFO, "AbstractThreadClient.connectToLobby " + clientEntity + " joined lobby " + lobby);
    }

    protected void sendClientFight() {
        try {
            out.writeObject(fight);
            logger.log(Level.INFO, "AbstractThreadClient.sendClientFight client " + clientEntity.getUsername() + " got the fight " + fight);
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
