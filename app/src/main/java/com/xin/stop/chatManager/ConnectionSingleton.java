package com.xin.stop.chatManager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by Administrator on 2014/10/13.
 */
public class ConnectionSingleton extends  XMPPTCPConnection{

    public static final ConnectionConfiguration connConfig = new ConnectionConfiguration("192.168.41.57", 5222);
    public volatile static ConnectionSingleton connection = null;
    static {
        SmackConfiguration.DEBUG_ENABLED = true;
        connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
    }
    private ConnectionSingleton(){
        super(connConfig);
    };
    public static ConnectionSingleton getXMPPTCPConnection(){

        if(connection == null){
            synchronized (ConnectionSingleton.class){
                if(connection == null){
                    connection = new ConnectionSingleton();
                }
            }
        }
        return connection;
    }
}