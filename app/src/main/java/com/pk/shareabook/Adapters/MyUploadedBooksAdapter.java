package com.pk.shareabook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.pk.shareabook.Activities.UploadBook;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AQSA SHaaPARR on 8/13/2017.
 */

public class MyUploadedBooksAdapter extends BaseAdapter {


    HashMap<String,String> mmmm;
    Context context;
    ArrayList<HashMap<String,String>> Lmap;
    int p;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        mmmm = new HashMap<>();
        mmmm = Lmap.get(position);
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        p = position;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_my_uploaded_books,parent,false);

        }

       // result = Lmap.get(position);
//        if (position<Lmap.size()){
//            position++;
//        }

//        else {
//            result = convertView;
//        }




        TextView tvAuthorName,tvBookName;
        final ImageView ivBookCover, ivEditDelete;


        tvAuthorName = (TextView)convertView.findViewById(R.id.tvAuthorName);
        tvBookName = (TextView)convertView.findViewById(R.id.tvBookTitle);
        ivBookCover = (ImageView)convertView.findViewById(R.id.imageCover);
        ivEditDelete = (ImageView) convertView.findViewById(R.id.ivEditDelete);


        Picasso.with(context).load(END_POINTS.GET_BOOK_LOGO + mmmm.get("logo")).into(ivBookCover);

        tvAuthorName.setText( mmmm.get("author"));

         tvBookName.setText( mmmm.get("title"));
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

//                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//
//                                SharedPreferences.Editor editor = preferences.edit();
//                                editor.remove("bookTitle");
//                                editor.remove("bookAuthor");
//                                editor.remove("bookCover");
//                                editor.putString("bookTitle",mmmm.get("title"));
//                                editor.putString("bookAuthor",mmmm.get("author"));
//                                editor.putString("bookCover",mmmm.get("logo"));
//                                editor.apply();

                                Intent intent = new Intent(context, UploadBook.class);
                                intent.putExtra("bookTitle",mmmm.get("title"));
                                intent.putExtra("bookAuthor",mmmm.get("author"));
                                intent.putExtra("bookCover",mmmm.get("logo"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.getApplicationContext().startActivity(intent);



                                break;
                            case R.id.delete:


                                mmmm = Lmap.get(position);
                                Toast.makeText(context,"delete",Toast.LENGTH_SHORT).show();
//
                           LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
                              View view=  inflater.inflate(R.layout.custom_alert_edit_deletebooks,null);
                                final AlertDialog editDeleteDialo = new AlertDialog.Builder(context.getApplicationContext()).create();
                                editDeleteDialo.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//
//    editDeleteDialo.setView(view);

             //                   android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                             //   View view = context.getApplicationContext()

                                TextView tvDelete = (TextView) view.findViewById(R.id.yes);
                                TextView tvNo = (TextView) view.findViewById(R.id.no);


                                tvDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                      //  Toast.makeText(context.getApplicationContext(),"Delete",Toast.LENGTH_SHORT).show();


                                      //  deleteBooks();
//                                        mmmm.clear();
//                                        mmmm = Lmap.get(position);

                                        Toast.makeText(context,mmmm.get("id"),Toast.LENGTH_SHORT).show();
                                        deleteBooks(mmmm.get("id"));
                                        removeAt(p);
                                        editDeleteDialo.dismiss();




                                    }
                                });

                                tvNo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//
                                      //  Toast.makeText(context.getApplicationContext(),"Delete",Toast.LENGTH_SHORT).show();
                                    editDeleteDialo.dismiss();

                                    }
                                });

                                editDeleteDialo.setView(view);
                                    editDeleteDialo.show();


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

    public void deleteBooks(final String id){

        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.DELETE_BOOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");











                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
           //     pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

               // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
             //   preferences.getString("id","");

          //      params.put("bookId", result.get("id"));

                params.put("bookId", id);

//                params.put("password", password);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);


    }
    public void removeAt(int position){

      //  Lmap.remove(position);
        Lmap.remove(mmmm);
        notifyDataSetChanged();

//        cardCommenList.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position,cardCommenList.size());

    }

}
