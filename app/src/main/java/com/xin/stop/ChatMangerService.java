package com.xin.stop;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.xin.stop.chatManager.ConnectionSingleton;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public class ChatMangerService extends Service {
    XMPPTCPConnection connection = null;
    public ChatMangerService() {
        connection = ConnectionSingleton.getXMPPTCPConnection();
        Log.i("why", "service init");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("why", "service create");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    class workBinder extends Binder{
           public ChatMangerService getChatManagerService(){
              return ChatMangerService.this;
           }
    }
}
