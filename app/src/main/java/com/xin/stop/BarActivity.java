package com.xin.stop;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.xin.stop.adapterView.ActiveBarListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BarActivity extends ActionBarActivity {

    private static Handler handler;
    private ActionBar actionBar;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        initActionBar();

        listView = (ListView) findViewById(R.id.list_view_bar);

        listView.setAdapter(new ActiveBarListViewAdapter(this));

    }
    private List<Map<String, Object>> getDate(){

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object>map = new HashMap<String, Object>();
        map.put("title", "Stop");
        map.put("info", "tingting");
        map.put("img", R.drawable.adsense_for_search);
        list.add(map);

        return list;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_about :
                Log.i("xin", "about");
                break;
            case R.id.action_settings:
                Log.i("xin", "setting");
                break;
            case android.R.id.home:
                Log.i("xin", "home");
                break;

        }
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initActionBar(){

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    class WordThread extends Thread{
        @Override
        public void run() {
            Log.i("xin", "Thread test - " + Thread.currentThread().getName());
            Message msg = new Message();
            msg.obj = "why---4";
            handler.sendMessageDelayed(msg, 4000);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("xin", "Thread test - " + Thread.currentThread().getName());
                }
            });
        }
    }
}
