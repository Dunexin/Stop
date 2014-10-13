package com.xin.stop.adapterView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xin.stop.R;


/**
 * Created by Administrator on 2014/10/12.
 */
public class ActiveBarListViewAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;

    public ActiveBarListViewAdapter(Context context){

        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static final  class ViewHolder{

        public ImageView imageView;
        public TextView TVInfo;
        public TextView TVTile;
        public View view;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){

            Log.i("xin","ConvertView init");
            convertView =layoutInflater.inflate(R.layout.list_view_item, null);
            holder = new ViewHolder();

            holder.imageView = (ImageView) convertView.findViewById(R.id.img);
            holder.TVInfo = (TextView) convertView.findViewById(R.id.info);
            holder.TVTile = (TextView) convertView.findViewById(R.id.title);
            holder.view = convertView.findViewById(R.id.list_item_click);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setBackgroundResource(R.drawable.adsense_for_search);
        holder.TVInfo.setText("Ting" + String.valueOf(position));
        holder.TVTile.setText("Stop");

        holder.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    default:
                        break;
                }
                return false;
            }
        });

        Log.v("xin", String.valueOf(position));
        return convertView;
    }
}
