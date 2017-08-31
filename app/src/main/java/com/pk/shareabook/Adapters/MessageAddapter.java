package com.pk.shareabook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pk.shareabook.Pojo.MessagesPojo;
import com.pk.shareabook.R;

import java.util.ArrayList;

/**
 * Created by Harry on 8/23/2017.
 */

public class MessageAddapter extends ArrayAdapter<MessagesPojo> {

    ArrayList <MessagesPojo> messagesPojos;
    Context context;
    LayoutInflater layoutInflater;
    int layoutResources;

    public MessageAddapter(@NonNull Context context, @LayoutRes int resource, ArrayList <MessagesPojo> messagesPojos) {
        super(context, resource);

       this. context = context;
       this.messagesPojos = messagesPojos;
        layoutResources = resource;

    }


    @Override
    public int getCount() {
        return messagesPojos.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        convertView = inflater.inflate(layoutResources,parent,false);

        TextView message = (TextView) convertView.findViewById(R.id.message);
        TextView Sender = (TextView) convertView.findViewById(R.id.sender);
        LinearLayout MessageLaout = (LinearLayout) convertView.findViewById(R.id.txtLayout);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);


        MessagesPojo messagesPojo = messagesPojos.get(position);

        if(messagesPojos.get(position).getChat_sender_id().equals("23"))
        {
            MessageLaout.setGravity(Gravity.RIGHT);
        }

        message.setText(messagesPojo.getMessage());
        Sender.setText(messagesPojo.getChat_reciever());


        return convertView;

    }



}
