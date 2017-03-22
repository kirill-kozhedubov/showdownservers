package iq.ven.showdown.server.impl;

import java.net.Socket;

/**
 * Created by User on 21.03.2017.
 */
public class ThreadClientImpl extends AbstractThreadClient {

    public ThreadClientImpl(Socket clientSocket) {
        super(clientSocket);
    }

}
