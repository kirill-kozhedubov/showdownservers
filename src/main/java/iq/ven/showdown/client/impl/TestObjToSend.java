package iq.ven.showdown.client.impl;

import java.io.Serializable;

/**
 * Created by User on 22.03.2017.
 */
public class TestObjToSend implements Serializable {
    public int xfxd = 123;

    @Override
    public String toString() {
        return "TestObjToSend{" +
                "xfxd=" + xfxd +
                '}';
    }
}
