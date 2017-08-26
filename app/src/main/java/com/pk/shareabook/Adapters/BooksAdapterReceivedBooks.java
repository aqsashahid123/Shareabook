package com.pk.shareabook.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.pk.shareabook.Activities.RecievedBooks;
import com.pk.shareabook.Activities.RequestedBooks;
import com.pk.shareabook.Activities.UploadBook;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AQSA SHaaPARR on 8/25/2017.
 */


public class BooksAdapterReceivedBooks extends RecyclerView.Adapter<BooksAdapterReceivedBooks.BookHolderReceivedBooks> {
        List<HashMap<String,String>> mapList;
        Activity activity;
    HashMap<String,String> SingleMap;


    public BooksAdapterReceivedBooks(Activity mActivity,List<HashMap<String,String>> mMapList){

        this.mapList = mMapList;
        this.activity = mActivity;

    }


    @Override
    public BookHolderReceivedBooks onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_with_menu_icon,parent);

        return new BookHolderReceivedBooks(view);
    }

    @Override
    public void onBindViewHolder(final BookHolderReceivedBooks holder, final int position) {
        SingleMap = new HashMap<>();
        SingleMap =  mapList.get(position);
        holder.tvBookName.setText(SingleMap.get("bookName"));
        holder.tvAuthorName.setText(SingleMap.get("authorName"));
        // holder.ivBookCover.setImageBitmap();;
        String imgLogo = SingleMap.get("image");

        Picasso.with(activity.getApplicationContext()).load(END_POINTS.GET_BOOK_LOGO + imgLogo).into(holder.ivBookCover);

        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Context wrapper = new ContextThemeWrapper(activity.getApplicationContext(), R.style.MyPopupMenu);

                final PopupMenu popup = new PopupMenu(wrapper, holder.ivMenu);
                popup.getMenuInflater().inflate(R.menu.received_books_menu,popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.markAsReceived:

                            SingleMap = mapList.get(position);


                              String bookId =  SingleMap.get("book_id");

                           //     bookReceived(bookId);


//                                SharedPreferences.Editor editor = preferences.edit();
//                                editor.remove("bookTitle");
//                                editor.remove("bookAuthor");
//                                editor.remove("bookCover");
//                                editor.putString("bookTitle",mmmm.get("title"));
//                                editor.putString("bookAuthor",mmmm.get("author"));
//                                editor.putString("bookCover",mmmm.get("logo"));
//                                editor.apply();


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

    public class BookHolderReceivedBooks extends RecyclerView.ViewHolder{

        public TextView tvAuthorName,tvBookName;
        CardView card_view;
        ImageView ivBookCover,ivMenu;
        RelativeLayout laCardView;


        public BookHolderReceivedBooks(View itemView) {
            super(itemView);
            tvAuthorName = (TextView) itemView.findViewById(R.id.bookAuthor);
            tvBookName = (TextView) itemView.findViewById(R.id.bookName);
            ivBookCover = (ImageView) itemView.findViewById(R.id.imageView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            laCardView = (RelativeLayout) itemView.findViewById(R.id.laCardView);
            ivMenu = (ImageView) itemView.findViewById(R.id.ivEditDelete);



        }
    }


    ///////////////////////////////////////////////

    public  void bookReceived(final String bookId){


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.MARKED_AS_RECEIVED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(activity.getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                //   pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    if (success.equals("1")){

                        Intent intent = new Intent(activity.getApplicationContext(), RecievedBooks.class);
                        //SingleMap = new HashMap<>();

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);

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

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                String userId =   preferences.getString("id","");

                //      params.put("bookId", result.get("id"));
                params.put("userId",userId);
                params.put("bookId",bookId );


//                params.put("password", password);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        requestQueue.add(request);




    }



}
