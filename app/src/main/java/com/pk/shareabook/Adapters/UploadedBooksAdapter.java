package com.pk.shareabook.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Activities.BookDetail;
import com.pk.shareabook.Activities.UploadBook;
import com.pk.shareabook.Activities.UploadedBooks;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AQSA SHaaPARR on 8/26/2017.
 */

public class UploadedBooksAdapter extends RecyclerView.Adapter<UploadedBooksAdapter.ViewHolderUploadBooks> {

    Activity activity;
    List<HashMap<String,String>> mapList;
    HashMap<String,String> SingleMap;


    public UploadedBooksAdapter(List<HashMap<String,String>> mapList,Activity activity){

        this.mapList = mapList;
        this.activity = activity;

    }

    @Override
    public ViewHolderUploadBooks onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.card_item_with_menu_icon,parent,false);

        return new ViewHolderUploadBooks(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderUploadBooks holder, final int position) {

        SingleMap = new HashMap<>();
        SingleMap = mapList.get(position);
        holder.tvBookName.setText(SingleMap.get("title"));
        holder.tvAuthorName.setText(SingleMap.get("author"));
        // holder.ivBookCover.setImageBitmap();;
        String imgLogo = SingleMap.get("logo");

        Picasso.with(activity.getApplicationContext()).load(END_POINTS.GET_BOOK_LOGO + imgLogo).into(holder.ivBookCover);


        holder.tvBookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), BookDetail.class);
                SingleMap = mapList.get(position);
                intent.putExtra("bookId" , SingleMap.get("bookId"));
                // SingleMap = new HashMap<>();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);

            }
        });
        holder.tvAuthorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), BookDetail.class);
                SingleMap = mapList.get(position);
                intent.putExtra("bookId" , SingleMap.get("bookId"));
                //SingleMap = new HashMap<>();

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });
        holder.ivBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), BookDetail.class);
                SingleMap = mapList.get(position);
                intent.putExtra("bookId" , SingleMap.get("bookId"));
                //  SingleMap = new HashMap<>();

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });

        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleMap = mapList.get(position);

                Context wrapper = new ContextThemeWrapper(activity.getApplicationContext(), R.style.MyPopupMenu);

                final PopupMenu popup = new PopupMenu(wrapper,  holder.ivMenu);
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

                                Intent intent = new Intent( activity.getApplicationContext(), UploadBook.class);
                                intent.putExtra("bookTitle",SingleMap.get("title"));
                                intent.putExtra("bookAuthor",SingleMap.get("author"));
                                intent.putExtra("bookCover",SingleMap.get("logo"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.getApplicationContext().startActivity(intent);



                                break;
                            case R.id.delete:


                                SingleMap = mapList.get(position);
                                Toast.makeText(activity,"delete",Toast.LENGTH_SHORT).show();
//
                                LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
                                View view=  inflater.inflate(R.layout.custom_alert_edit_deletebooks,null);
                                final AlertDialog editDeleteDialo = new AlertDialog.Builder(activity.getApplicationContext()).create();
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

                                        Toast.makeText(activity.getApplicationContext(),SingleMap.get("id"),Toast.LENGTH_SHORT).show();
                                        deleteBooks(SingleMap.get("id"));
                                  //      removeAt(p);
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


    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }

    public class ViewHolderUploadBooks extends RecyclerView.ViewHolder{


        public TextView tvAuthorName,tvBookName;
        CardView card_view;
        ImageView ivBookCover,ivMenu;
        RelativeLayout laCardView;

        public ViewHolderUploadBooks(View itemView) {
            super(itemView);
            tvAuthorName = (TextView) itemView.findViewById(R.id.bookAuthor);
            tvBookName = (TextView) itemView.findViewById(R.id.bookName);
            ivBookCover = (ImageView) itemView.findViewById(R.id.imageView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            laCardView = (RelativeLayout) itemView.findViewById(R.id.laCardView);
            ivMenu = (ImageView) itemView.findViewById(R.id.ivEditDelete);

        }
    }


    public void deleteBooks(final String id){

        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.DELETE_BOOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //   pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");


                    if (success.equals("0")){


                        Toast.makeText(activity.getApplicationContext(),"Book not Deleted",Toast.LENGTH_SHORT).show();
                    }
                    if (success.equals("1")){


                        Toast.makeText(activity.getApplicationContext(),"Book Deleted",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent( activity.getApplicationContext(), UploadedBooks.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.getApplicationContext().startActivity(intent);


                    }









                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        requestQueue.add(request);


    }



}
