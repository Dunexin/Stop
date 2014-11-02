package com.xin.stop;

import android.app.ActionBar;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.xin.stop.adapterView.ActiveChatRoomAdapter;
import com.xin.stop.chatMesage.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ServiceIntent = new Intent(this, ChatMangerService.class);
        bindService(ServiceIntent, connection, Context.BIND_AUTO_CREATE);
        initActivity();
        createListView();

    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            mBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("why", "chat room connect");
            mBound = true;
            mService = ((ChatMangerService.workBinder)service).getChatManagerService();
            mService.createUserChat("stop@win7-20140419vw");
            mService.setChatRoomCallBack(new ChatRoomCallBack() {
                @Override
                public void sendMessageToUser(String msg) {

                    messageData.add(new ChatMessage(ChatMessage.CHAT_TYPE_FRIEND, msg));
                    chatRoomAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("why", "room destroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void createListView(){

        chatList = (ListView) findViewById(R.id.chat_room_list);
        chatRoomAdapter = new ActiveChatRoomAdapter(this, messageData);
        chatList.setAdapter(chatRoomAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.chat_room_send_Button:
                if(mBound){
                    messageData.add(new ChatMessage(ChatMessage.CHAT_TYPE_USER, String.valueOf(massageEditText.getText())));
                    mService.SendMessageToFriend(String.valueOf(massageEditText.getText()));
                    chatRoomAdapter.notifyDataSetChanged();
                    massageEditText.setText("");
                }
                break;
            default:
                break;
        }
    }


    private void initActivity(){

        sendButton = (Button) findViewById(R.id.chat_room_send_Button);
        massageEditText = (EditText) findViewById(R.id.chat_room_editText);
        sendButton.setOnClickListener(this);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        messageData = new ArrayList<ChatMessage>();
    }

    private ListView chatList = null;
    private ActiveChatRoomAdapter chatRoomAdapter = null;
    private ActionBar actionBar = null;
    private Button sendButton = null;
    private EditText massageEditText = null;
    private List<ChatMessage> messageData;

    private ChatMangerService mService = null;
    private Intent ServiceIntent = null;

    private boolean mBound = false;
}
