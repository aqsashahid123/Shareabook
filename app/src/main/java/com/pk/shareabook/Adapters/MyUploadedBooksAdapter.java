package com.pk.shareabook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.pk.shareabook.UploadBook;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 8/13/2017.
 */

public class MyUploadedBooksAdapter extends BaseAdapter {



    Context context;
    ArrayList<HashMap<String,String>> Lmap;
    HashMap<String, String> result = new HashMap<String, String>();
    LayoutInflater inflater;

public MyUploadedBooksAdapter(Context mContext,ArrayList<HashMap<String,String>> Mmap){

    this.context=mContext;
    this.Lmap=Mmap;





}



    @Override
    public int getCount() {
        return Lmap.size() ;
    }

    @Override
    public Object getItem(int position) {
        return Lmap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView tvAuthorName,tvBookName;
        final ImageView ivBookCover, ivEditDelete;
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_my_uploaded_books,parent,false);
        result = Lmap.get(position);

        tvAuthorName = (TextView)convertView.findViewById(R.id.tvAuthorName);
        tvBookName = (TextView)convertView.findViewById(R.id.tvBookTitle);
        ivBookCover = (ImageView)convertView.findViewById(R.id.imageCover);
        ivEditDelete = (ImageView) convertView.findViewById(R.id.ivEditDelete);


        Picasso.with(context).load(END_POINTS.GET_BOOK_LOGO + result.get("logo")).into(ivBookCover);

        tvAuthorName.setText( result.get("author"));

         tvBookName.setText( result.get("title"));
        ivEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context wrapper = new ContextThemeWrapper(context, R.style.MyPopupMenu);

                final PopupMenu popup = new PopupMenu(wrapper, ivEditDelete);
                popup.getMenuInflater().inflate(R.menu.popup_edit_delete,popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.edit:

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("bookTitle");
                                editor.remove("bookAuthor");
                                editor.remove("bookCover");
                                editor.putString("bookTitle",result.get("title"));
                                editor.putString("bookAuthor",result.get("author"));
                                editor.putString("bookCover",result.get("logo"));
                                editor.apply();

                                Intent intent = new Intent(context, UploadBook.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.getApplicationContext().startActivity(intent);



                                break;
                            case R.id.delete:

                                Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();

                                break;

                        }


                        return true;
                    }
                });


                popup.show();
            }
        });



      //  final PopupMenu popup = new PopupMenu(v.getContext(), holder.CommentMenu);


        return convertView;
    }
}
