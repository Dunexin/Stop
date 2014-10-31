package com.xin.stop;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.xin.stop.chatManager.ConnectionSingleton;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

public class ChatMangerService extends Service {

    final static int SEND_MESSAGE_TO_FRIEND = 1;
    final static int SEND_MESSAGE_TO_USER = 0;
    final static String threadName = "Service Chat Thread";
    private XMPPTCPConnection connection = null;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private Handler mServiceMainHandler;
    private ChatManager mChatManager;
    private Chat mChat;
    private ChatRoomCallBack mChatRoomCallBack = null;

    public ChatMangerService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createThreadHandler();
        mServiceMainHandler = new ServiceMainHandler();
        Log.i("why", "service create");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mServiceHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    connection.disconnect();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.i("why", "service destroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("why", "service restart");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i("why", "service binder");
        return  new workBinder();

    }

    public void setChatRoomCallBack(ChatRoomCallBack chatRoomCallBack){

        this.mChatRoomCallBack = chatRoomCallBack;
    }

    public void SendMessageToFriend(final String mes){

        org.jivesoftware.smack.packet.Message xMeg = new org.jivesoftware.smack.packet.Message(mes, org.jivesoftware.smack.packet.Message.Type.chat);
        Message msg = new Message();
        msg.obj = mes;
        msg.what = SEND_MESSAGE_TO_FRIEND;
        mServiceHandler.sendMessage(msg);
    }

    public void createUserChat(final String friendName){

        mServiceHandler.post(new Runnable() {
            @Override
            public void run() {
                mChat = mChatManager.createChat(friendName, null);
            }
        });
    }
    public void closeChat(){
        mChat.close();
        mChat = null;
    }
    public void connectService(String mip){

        if(mip != null && mip != "")
            ConnectionSingleton.setIp(mip);
        connection = ConnectionSingleton.getXMPPTCPConnection();

        mServiceHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    connection.connect();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void unConnectService(){

        mServiceHandler.post(new Runnable() {
            @Override
            public void run() {

                try {
                    connection.disconnect();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void loginService(final String name, final String password){

        mServiceHandler.post(new Runnable() {
            @Override
            public void run() {

                try {
                    if(connection.isConnected() && !connection.isAuthenticated()) {
                        connection.login(name, password);
                    }
                    if(connection.isAuthenticated() && mChatManager == null){
                        mChatManager =  ChatManager.getInstanceFor(connection);
                        mChatManager.addChatListener(new ChatManagerListener() {
                            @Override
                            public void chatCreated(Chat chat, boolean createdLocally) {
                            chat.addMessageListener(new MessageListener() {
                                @Override
                                public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {

                                    Message msg = new Message();
                                    msg.what = SEND_MESSAGE_TO_USER;
                                    msg.obj = message.getBody();
                                    mServiceMainHandler.sendMessage(msg);
                                }
                            });
                            }
                        });
                    }

                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private  final class ServiceHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case SEND_MESSAGE_TO_FRIEND: {
                    try {
                        mChat.sendMessage((String) msg.obj);
                        Log.i("why", (String) msg.obj);
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        public ServiceHandler(Looper looper) {
            super(looper);
        }
    }
    class ServiceMainHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case SEND_MESSAGE_TO_USER:
                    mChatRoomCallBack.sendMessageToUser((String) msg.obj);
                    break;
            }
        }
    }
    private void createThreadHandler(){
        HandlerThread thread = new HandlerThread(threadName, Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }
    class workBinder extends Binder {
           public ChatMangerService getChatManagerService(){
              return ChatMangerService.this;
           }
    }
}
