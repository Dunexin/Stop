package com.xin.stop.chatManager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * Created by xin on 2014/10/13.
 */
public class ChatManagerControl {

    private Context mContext = null;
    final static int SEND_MESSAGE_TO_FRIEND = 1;
    final static int SEND_MESSAGE_TO_USER = 0;
    final static String threadName = "Service Chat Thread";
    private XMPPTCPConnection connection = null;
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    public ChatManagerControl(Context context){

        mContext = context;
    }

    private  final class ServiceHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

        }

        public ServiceHandler(Looper looper) {
            super(looper);
        }
    }
}
