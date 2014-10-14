package com.xin.stop;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xin.stop.chatManager.ConnectionSingleton;
import com.xin.stop.chatManager.XMPPTCPThread;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    private Button Stop = null;
    private Handler chatHandler = null;
    private TextView txUser;
    private boolean backKeyClickNum = false;
    private static XMPPTCPConnection connection;
    private ChatMangerService mangerService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txUser = (TextView) findViewById(R.id.textViewUser);

        chatHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
               txUser.setText((CharSequence) msg.obj);
                return true;
            }
        });
        new XMPPTCPThread(chatHandler).start();

        final Intent barActivity = new Intent(this, BarActivity.class);
        Stop = (Button) findViewById(R.id.stop_button);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("xin", "button");
                startActivity(barActivity);
            }
        });
        Intent chatManagerService = new Intent(this, ChatMangerService.class);
        bindService(chatManagerService, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mangerService = ((ChatMangerService.workBinder)service).getChatManagerService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(backKeyClickNum == false){
            Toast.makeText(this,R.string.back_key_Confirmation,Toast.LENGTH_SHORT ).show();
            backKeyClickNum = !backKeyClickNum;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    backKeyClickNum = !backKeyClickNum;
                }
            }, 2000);
        } else {
            finish();
        }
    }

    @Override
    public void finish() {
        new Thread(){
            @Override
            public void run() {
                try {
                    ConnectionSingleton.getXMPPTCPConnection().disconnect();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
