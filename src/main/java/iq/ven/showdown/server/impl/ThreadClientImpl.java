package iq.ven.showdown.server.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by User on 21.03.2017.
 */
public class ThreadClientImpl extends AbstractThreadClient {

    public ThreadClientImpl(Socket clientSocket) {
        super(clientSocket);
    }


}
