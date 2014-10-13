package com.xin.stop.chatManager;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * Created by Administrator on 2014/10/13.
 */
public class ChatLinkControl {
    static final private XMPPTCPConnection connection = ConnectionSingleton.getXMPPTCPConnection();
    static public void chatLogin(final String username, final String password, final String resource){
        new Thread(){
            @Override
            public void run() {
                try {
                    connection.login(username, password, resource);
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();
    }
    static public void af(){
        new Thread(){
            @Override
            public void run() {

                try {
                    connection.disconnect();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
