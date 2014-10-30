package com.xin.stop.chatMesage;

/**
 * Created by Administrator on 2014/10/30.
 */
public class ChatMessage {

    public final static  int CHAT_TYPE_USER = 0;
    public final static  int CHAT_TYPE_FRIEND = 1;

    public final static  int CHAT_TYPE_COUNT = 2;

    private int mType;
    private String mMessage;
    public ChatMessage(int chat_type, String message){

        mType = chat_type;
        mMessage = message;
    }

    public String getMessage(){
        return this.mMessage;
    }

    public int getType(){
        return mType;
    }
}
