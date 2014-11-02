package com.xin.stop.chatManager;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by xin on 2014/10/13.
 */
public class ConnectionSingleton extends  XMPPTCPConnection{

    private  static String IP = "192.168.41.57";
    public static ConnectionConfiguration connConfig = null;
    public volatile static ConnectionSingleton connection = null;
    private ConnectionSingleton(){
        super(connConfig);
    };
    public static ConnectionSingleton getXMPPTCPConnection(){

        if(connection == null){
            synchronized (ConnectionSingleton.class){
                if(connection == null){
                    connConfig = new ConnectionConfiguration(IP, 5222);
                    SmackConfiguration.DEBUG_ENABLED = true;
                    connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
                    connection = new ConnectionSingleton();
                }
            }
        }
        return connection;
    }
    static public void setIp(String ip){

        IP = ip;
    }
}
