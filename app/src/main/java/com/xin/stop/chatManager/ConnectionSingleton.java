package com.xin.stop.chatManager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by xin on 2014/10/13.
 */
public class ConnectionSingleton extends  XMPPTCPConnection{

    public static ConnectionConfiguration connConfig = new ConnectionConfiguration("10.9.1.172", 5222);
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
