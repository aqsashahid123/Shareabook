package com.pk.shareabook.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.pk.shareabook.Activities.RecievedBooks;
import com.pk.shareabook.Activities.SharingRequest;
import com.pk.shareabook.GeneralMethods;
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

public class SharingRequestRvAdapter extends RecyclerView.Adapter<SharingRequestRvAdapter.ViewHolderSharingRequest> {


    Activity activity;
    HashMap<String,String> SingleMap;
    List< HashMap<String,String>> mapList;
        ProgressDialog pd;
    GeneralMethods gm = new GeneralMethods();
    public SharingRequestRvAdapter(Activity activity,List< HashMap<String,String>> mapList){

        this.activity = activity;
        this.mapList = mapList;

    }

    @Override
    public ViewHolderSharingRequest onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.card_item_sharing_request,parent,false);

        return new ViewHolderSharingRequest(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderSharingRequest holder, final int position) {

        SingleMap = new HashMap<>();
        SingleMap = mapList.get(position);
        holder.tvBookName.setText(SingleMap.get("title"));
        holder.tvAuthorName.setText(SingleMap.get("author"));
        holder.tvMessage.setText(SingleMap.get("request_message"));
        holder.tvRequester.setText(SingleMap.get("display_name"));
        // holder.ivBookCover.setImageBitmap();;
        String imgLogo = SingleMap.get("logo");

        Picasso.with(activity.getApplicationContext()).load(END_POINTS.GET_BOOK_LOGO + imgLogo).into(holder.ivBookCover);




         String requestStatus= SingleMap.get("request_status");

        if (requestStatus.equals("1")){             // Accepted hai

            holder.ivMenu.setVisibility(View.VISIBLE);
        }


        if (requestStatus.equals("2")){   //2 wali reject hai

            holder.ivMenu.setVisibility(View.GONE);
        }

        if (requestStatus.equals("0")){    // ubhi kuch dcyd nahi hua iska
            holder.ivMenu.setVisibility(View.VISIBLE);

        }


        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleMap = mapList.get(position);
                String reqStatus =   SingleMap.get("request_status");



                if (reqStatus.equals("0")) {

                    Context wrapper = new ContextThemeWrapper(activity.getApplicationContext(), R.style.MyPopupMenu);

                    final PopupMenu popup = new PopupMenu(wrapper, holder.ivMenu);
                    popup.getMenuInflater().inflate(R.menu.popup_accept_reject_request, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.accept:

                                    SingleMap = mapList.get(position);
                                    AcceptRequest(SingleMap.get("request_id"));


                                    break;
                                case R.id.reject:


                                    SingleMap= mapList.get(position);
                                    String bookId = SingleMap.get("request_book_id");
                                    String reqId = SingleMap.get("request_id");
                                    RejectRequest(reqId, bookId);


                                    break;

                            }


                            return true;
                        }
                    });


                    popup.show();
                }

                if (reqStatus.equals("1")){

                    Context wrapper = new ContextThemeWrapper(activity.getApplicationContext(), R.style.MyPopupMenu);

                    final PopupMenu popup = new PopupMenu(wrapper, holder.ivMenu);
                    popup.getMenuInflater().inflate(R.menu.popup_shared_message_reject, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.markAsShared:

                                    SingleMap = mapList.get(position);
                                    MarkAsShared(SingleMap.get("request_book_id"));


                                    break;
                                case R.id.message:

//
//                                    mmmm = Lmap.get(position);
//                                    String bookId = mmmm.get("request_book_id");
//                                    String reqId = mmmm.get("request_id");
//                                    RejectRequest(reqId, bookId);


                                    break;
                                case R.id.reject:
                                    //
                                    SingleMap = mapList.get(position);
                                    String bookId = SingleMap.get("request_book_id");
                                    String reqId = SingleMap.get("request_id");
                                    RejectRequest(reqId, bookId);

                                    break;

                            }


                            return true;
                        }
                    });


                    popup.show();


                }



            }
        });



    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }

    public class ViewHolderSharingRequest extends RecyclerView.ViewHolder{


        public TextView tvAuthorName,tvBookName,tvMessage,tvRequester;
        CardView card_view;
        ImageView ivBookCover,ivMenu;
        RelativeLayout laCardView;

        public ViewHolderSharingRequest(View itemView) {
            super(itemView);
            tvAuthorName = (TextView) itemView.findViewById(R.id.bookAuthor);
            tvBookName = (TextView) itemView.findViewById(R.id.bookName);
            tvMessage = (TextView) itemView.findViewById(R.id.senderMessage);
            tvRequester = (TextView) itemView.findViewById(R.id.bookRequesterName);
            ivBookCover = (ImageView) itemView.findViewById(R.id.imageView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            laCardView = (RelativeLayout) itemView.findViewById(R.id.laCardView);
            ivMenu = (ImageView) itemView.findViewById(R.id.ivEditDelete);

        }
    }



    public void AcceptRequest(final String id){
        pd = new ProgressDialog(activity.getApplicationContext());
        pd.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        pd.setMessage("loading");
        pd.show();
        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.ACCEPT_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                pd.dismiss();

              //  Toast.makeText(activity.getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    String message = object.getString("message");
                    if (success.equals("0")){

                        Toast.makeText(activity.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }
                    if (success.equals("1")){

                        Toast.makeText(activity.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        gm.openActivity(activity.getApplicationContext(), SharingRequest.class);
                        //  notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();


                params.put("requestId", id);
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        requestQueue.add(request);


    }

    public void RejectRequest(final String requestId, final String bookId){

        pd = new ProgressDialog(activity.getApplicationContext());
        pd.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.REJECT_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();

              //  Toast.makeText(activity.getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    String message = object.getString("message");
                    if (success.equals("0")){

                        Toast.makeText(activity.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }
                    if (success.equals("1")){

                        Toast.makeText(activity.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        // notifyDataSetChanged();
                        gm.openActivity(activity.getApplicationContext(), SharingRequest.class);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("requestId", requestId);
                params.put("bookId",bookId);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        requestQueue.add(request);





    }


    public void  MarkAsShared(final String bookId){
        pd = new ProgressDialog(activity.getApplicationContext());
        pd.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.MARKED_AS_SHARED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();

             //   Toast.makeText(activity.getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    String message = object.getString("message");
                    if (success.equals("0")){

                    //    Toast.makeText(activity.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        Toast.makeText(activity.getApplicationContext(),"Book is not marked as received by the Receiver yet." ,Toast.LENGTH_SHORT).show();
                    }
                    if (success.equals("1")){

                        Toast.makeText(activity.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        gm.openActivity(activity.getApplicationContext(), SharingRequest.class);
                        //   notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //  params.put("requestId", requestId);
                params.put("bookId",bookId);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        requestQueue.add(request);




    }



}
