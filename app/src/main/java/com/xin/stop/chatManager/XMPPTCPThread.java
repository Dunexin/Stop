package com.xin.stop.chatManager;

import android.os.Handler;
import android.util.Log;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by xin on 2014/10/13.
 */
public class XMPPTCPThread extends Thread{

    private  XMPPTCPConnection connection = null;
    Handler charHandler = null;
    public XMPPTCPThread(Handler chatHandler){
        this.connection = ConnectionSingleton.getXMPPTCPConnection();
        this.charHandler = chatHandler;
    }

    @Override
    public void run() {
        try {
            Log.i("why", String.valueOf(connection.isAuthenticated()));
            Log.i("why", String.valueOf(connection.isConnected()));
            connection.connect();
            Log.i("why", String.valueOf(connection.isAuthenticated()));
            Log.i("why", String.valueOf(connection.isConnected()));

            if(connection.isConnected() && !connection.isAuthenticated()) {
                connection.login("xin", "xin", "Android-client");

                android.os.Message msg = new android.os.Message();
                msg.obj = connection.getUser();
                charHandler.sendMessage(msg);
            }
            Log.i("why", String.valueOf(connection.getUser()));
            Roster roster = connection.getRoster();
            Collection<RosterEntry> entries = roster.getEntries();

            for(Iterator<RosterEntry> entryIterator = entries.iterator(); entryIterator.hasNext();){

                RosterEntry entry =  entryIterator.next();
                Presence presence = roster.getPresence(entry.getUser());
                Log.i("xin", String.valueOf(presence.getStatus()));
                Log.i("xin", entry.getUser());
            }
            ChatManager chatManager = ChatManager.getInstanceFor(connection);

            chatManager.addChatListener(new ChatManagerListener() {
                @Override
                public void chatCreated(Chat chat, boolean createdLocally) {
                    chat.addMessageListener(new MessageListener() {
                        @Override
                        public void processMessage(Chat chat, Message message) {
                            Log.v("xin", "msg");
                            Log.v("xin", message.getFrom());
                            Log.v("xin", message.getBody());
                            try {
                                chat.sendMessage("sfasd");
                            } catch (XMPPException e) {
                                e.printStackTrace();
                            } catch (SmackException.NotConnectedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
