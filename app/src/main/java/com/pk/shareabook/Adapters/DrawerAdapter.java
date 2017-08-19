package com.pk.shareabook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pk.shareabook.Pojo.DrawerPojo;
import com.pk.shareabook.R;
import com.pk.shareabook.Activities.UploadBook;
import com.pk.shareabook.Activities.UploadedBooks;

import java.util.List;

/**
 * Created by AQSA SHaaPARR on 8/8/2017.
 */

public class DrawerAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    int layoutResources;
    List<DrawerPojo> drawerList;


    public DrawerAdapter(Context context,List<DrawerPojo> list, int layoutResources){

        this.context = context;
        this.drawerList = list;
        this.layoutResources= layoutResources;
        //this.inflater = inf

    }


    @Override
    public int getCount() {
        return drawerList.size();
    }

    @Override
    public Object getItem(int position) {
        return drawerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder drawerHolder;
        View view = convertView;
        if (convertView==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new ViewHolder();
            convertView = inflater.inflate(layoutResources,parent,false);
            drawerHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

            convertView.setTag(drawerHolder);
        }

        else {
            drawerHolder = (ViewHolder) convertView.getTag();

        }

        final DrawerPojo dItem = (DrawerPojo) this.drawerList.get(position);
        drawerHolder.tvTitle.setText(dItem.getItemName());

        drawerHolder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (dItem.getItemName()){

                    case "Details" :

                        Toast.makeText(context,"1",Toast.LENGTH_SHORT).show();

                        break;

                    case "My Uploaded Books" :
                       // Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();

                        Intent intent2 = new Intent(context.getApplicationContext(), UploadedBooks.class);
                        context.startActivity(intent2);

                        break;

                    case "Requested Books" :
                        Toast.makeText(context,"3",Toast.LENGTH_SHORT).show();
                        break;

                    case "Sharing Requests" :
                        Toast.makeText(context,"4",Toast.LENGTH_SHORT).show();
                        break;
                    case "Shared Books" :
                        Toast.makeText(context,"5",Toast.LENGTH_SHORT).show();
                        break;

                    case "Received Books" :
                        Toast.makeText(context,"6",Toast.LENGTH_SHORT).show();
                        break;
                    case "Upload Book" :

                        Intent intent = new Intent(context.getApplicationContext(), UploadBook.class);
                        context.startActivity(intent);

                        break;
                    case "Logout" :

                        Toast.makeText(context,"Log out",Toast.LENGTH_SHORT).show();

                        break;


                }


            }
        });





        return convertView;
    }


    private class ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;
    }



}
