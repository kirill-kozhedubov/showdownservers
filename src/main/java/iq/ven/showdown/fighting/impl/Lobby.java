package iq.ven.showdown.fighting.impl;

import iq.ven.showdown.database.ClientEntity;
import iq.ven.showdown.server.impl.AbstractThreadClient;

import java.io.Serializable;

/**
 * Created by User on 25.03.2017.
 */
public class Lobby extends Thread implements Serializable {

    private ClientEntity client1;
    private ClientEntity client2;
    private AbstractThreadClient client1Thread;
    private AbstractThreadClient client2Thread;


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
}