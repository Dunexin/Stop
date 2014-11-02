package com.xin.stop;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class LoginActivity extends Activity {

    private Intent ServiceIntent;
    private Intent chatRoomActivity = null;
    public ChatMangerService mService = null;
    public boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        ServiceIntent = new Intent(this, ChatMangerService.class);
        bindService(ServiceIntent, connection, Context.BIND_AUTO_CREATE);
        initWidget();
        chatRoomActivity = new Intent(this, ChatRoomActivity.class);
    }

    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("why", "connected");
            mBound = true;
            mService = ((ChatMangerService.workBinder)service).getChatManagerService();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mBound){
            unbindService(connection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
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

    class LoginOnClickListener implements View.OnClickListener{

        String IP = null;
        @Override
        public void onClick(View v) {

            switch (v.getId()){

                case R.id.login_button:
                    if(mBound){
                        if(iEditText.length() >= 1){
                            IP = iEditText.getText().toString();
                        }
                        if(uEditText.length() < 1){
                            Toast.makeText(LoginActivity.this, getString(R.string.emptyUserName), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(pEditText.length() < 1){
                            Toast.makeText(LoginActivity.this, getString(R.string.emptyPassCode), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mService.connectService(IP);
                        mService.loginService(uEditText.getText().toString(), pEditText.getText().toString());
                        startActivity(chatRoomActivity);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    private void initWidget(){

        uEditText  = (EditText) findViewById(R.id.user_name_editText);
        pEditText = (EditText) findViewById(R.id.pass_word_editText);
        iEditText = (EditText) findViewById(R.id.ip_editText);
        sButton = (Button) findViewById(R.id.login_button);
        sButton.setOnClickListener(new LoginOnClickListener());
    }
    private EditText uEditText;
    private EditText pEditText;
    private EditText iEditText;
    private Button sButton;
}
