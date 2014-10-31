package com.xin.stop;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by Administrator on 2014/10/31.
 */
public class BaseServiceConnection implements ServiceConnection {

    BaseServiceConnection(){

    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
