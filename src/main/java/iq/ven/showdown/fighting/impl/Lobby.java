package iq.ven.showdown.fighting.impl;

import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.server.impl.AbstractThreadClient;

import java.io.Serializable;
import java.net.Socket;

/**
 * Created by User on 25.03.2017.
 */
public class Lobby extends Thread implements Serializable {
    private ClientEntity client1;
    private ClientEntity client2;
    transient private AbstractThreadClient client1Thread;
    transient private AbstractThreadClient client2Thread;


    public Lobby(ClientEntity client1, AbstractThreadClient client1Thread) {
        this.client1 = client1;
        this.client1Thread = client1Thread;
    }

    public void clientConnect(ClientEntity client2, AbstractThreadClient client2Thread) {
        this.client2 = client2;
        this.client2Thread = client2Thread;
    }


    public ClientEntity getClient1() {
        return client1;
    }

    public ClientEntity getClient2() {
        return client2;
    }

    public AbstractThreadClient getClient1Thread() {
        return client1Thread;
    }

    public AbstractThreadClient getClient2Thread() {
        return client2Thread;
    }

    public Socket getClient1Socket() {
        return client1Thread.getSocket();
    }

    public Socket getClient2Socket() {
        return client2Thread.getSocket();
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "client1=" + client1 +
                ", client2=" + client2 +
                '}';
    }
}
