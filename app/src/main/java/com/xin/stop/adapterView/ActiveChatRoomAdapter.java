package com.xin.stop.adapterView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xin.stop.R;
import com.xin.stop.chatMesage.ChatMessage;

import java.util.List;

/**
 * Created by Administrator on 2014/10/30.
 */
public class ActiveChatRoomAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    List<ChatMessage> mMessageData;
    public ActiveChatRoomAdapter() {
        super();
    }


    public ActiveChatRoomAdapter(Context context, List<ChatMessage> messageData){

        this.layoutInflater = LayoutInflater.from(context);
        this.mMessageData = messageData;
    }
    @Override
    public int getItemViewType(int position) {
        return mMessageData.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return ChatMessage.CHAT_TYPE_COUNT;
    }

    @Override
    public int getCount() {
        return mMessageData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if(convertView == null) {

            holder = new ViewHolder();
            switch (getItemViewType(position)) {
                case ChatMessage.CHAT_TYPE_USER:

                    Log.i("xin", "ConvertView init - 0");
                    convertView = layoutInflater.inflate(R.layout.chat_view_user_item, null);
                    holder.imageView = (ImageView) convertView.findViewById(R.id.userImage);
                    holder.textView = (TextView) convertView.findViewById(R.id.user_list_active_title);
                    break;
                case ChatMessage.CHAT_TYPE_FRIEND:
                    Log.i("xin", "ConvertView init - 1");
                    convertView = layoutInflater.inflate(R.layout.chat_view_friend_item, null);
                    holder.imageView = (ImageView) convertView.findViewById(R.id.friendImage);
                    holder.textView = (TextView) convertView.findViewById(R.id.friend_list_active_title);
                    break;
            }

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();


        holder.textView.setText(mMessageData.get(position).getMessage() + position);
        return convertView;
    }
    public static final  class ViewHolder{

        ImageView imageView;
        TextView textView;
    }
}
