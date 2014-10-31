package com.xin.stop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class BaseActivity extends ActionBarActivity {

    private boolean backKeyClickNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                Log.i("why", "home");
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
      @Override
    public void onBackPressed() {
        if(backKeyClickNum == false){
            Toast.makeText(this, R.string.back_key_Confirmation, Toast.LENGTH_SHORT).show();
            backKeyClickNum = !backKeyClickNum;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    backKeyClickNum = !backKeyClickNum;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }
}
