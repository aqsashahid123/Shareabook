package com.pk.shareabook.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.pk.shareabook.Activities.SharingRequest;
import com.pk.shareabook.Activities.UploadBook;
import com.pk.shareabook.Activities.UploadedBooks;
import com.pk.shareabook.GeneralMethods;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AQSA SHaaPARR on 8/22/2017.
 */

public class SharingRequestAdapter extends BaseAdapter {


    HashMap<String,String> mmmm;
    Context context;
    ArrayList<HashMap<String,String>> Lmap;
    ProgressDialog pd;
    int p;
    HashMap<String, String> result = new HashMap<String, String>();
    GeneralMethods gm = new GeneralMethods();
    LayoutInflater inflater;

    public SharingRequestAdapter(Context mContext,ArrayList<HashMap<String,String>> Mmap){

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
    public View getView(final int position,  View convertView, ViewGroup parent) {

        mmmm = new HashMap<>();
        mmmm = Lmap.get(position);
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        p = position;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_sharing_request,parent,false);

        }




        TextView tvAuthorName,tvBookName,tvMessage,tvRequesterName;
        final ImageView ivBookCover, ivEditDelete;


        tvAuthorName = (TextView)convertView.findViewById(R.id.tvAuthorName);
        tvBookName = (TextView)convertView.findViewById(R.id.tvBookTitle);
        ivBookCover = (ImageView)convertView.findViewById(R.id.imageCover);
        ivEditDelete = (ImageView) convertView.findViewById(R.id.ivEditDelete);
        tvMessage = (TextView) convertView.findViewById(R.id.tvMessage);
        tvRequesterName = (TextView) convertView.findViewById(R.id.tvRequesterName);



        Picasso.with(context).load(END_POINTS.GET_BOOK_LOGO + mmmm.get("logo")).into(ivBookCover);

        tvAuthorName.setText( mmmm.get("author"));

        tvBookName.setText( mmmm.get("title"));
        tvRequesterName.setText(mmmm.get("display_name"));
        tvMessage.setText(mmmm.get("request_message"));

        final String requestStatus= mmmm.get("request_status");

        if (requestStatus.equals("1")){             // Accepted hai

            ivEditDelete.setVisibility(View.VISIBLE);
        }


        if (requestStatus.equals("2")){   //2 wali reject hai

            ivEditDelete.setVisibility(View.GONE);
        }

        if (requestStatus.equals("0")){    // ubhi kuch dcyd nahi hua iska
            ivEditDelete.setVisibility(View.VISIBLE);

        }


        ivEditDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (requestStatus.equals("0")) {

                    Context wrapper = new ContextThemeWrapper(context, R.style.MyPopupMenu);

                    final PopupMenu popup = new PopupMenu(wrapper, ivEditDelete);
                    popup.getMenuInflater().inflate(R.menu.popup_accept_reject_request, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.accept:

                                    mmmm = Lmap.get(position);
                                    AcceptRequest(mmmm.get("request_id"));


                                    break;
                                case R.id.reject:


                                    mmmm = Lmap.get(position);
                                    String bookId = mmmm.get("request_book_id");
                                    String reqId = mmmm.get("request_id");
                                    RejectRequest(reqId, bookId);


                                    break;

                            }


                            return true;
                        }
                    });


                    popup.show();
                }

                if (requestStatus.equals("1")){

                    Context wrapper = new ContextThemeWrapper(context, R.style.MyPopupMenu);

                    final PopupMenu popup = new PopupMenu(wrapper, ivEditDelete);
                    popup.getMenuInflater().inflate(R.menu.popup_shared_message_reject, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.markAsShared:

                                    mmmm = Lmap.get(position);
                                    MarkAsShared(mmmm.get("request_book_id"));


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
                                    mmmm = Lmap.get(position);
                                    String bookId = mmmm.get("request_book_id");
                                    String reqId = mmmm.get("request_id");
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



        //  final PopupMenu popup = new PopupMenu(v.getContext(), holder.CommentMenu);

        return convertView;
    }

    public void AcceptRequest(final String id){

        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.ACCEPT_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd = new ProgressDialog(context.getApplicationContext());
                pd.setMessage("loading");
                pd.show();


                Toast.makeText(context.getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                try {
                    JSONObject object = new JSONObject(response);
                   String success = object.getString("success");
                    String message = object.getString("message");
                    if (success.equals("0")){

                    Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                    }
                    if (success.equals("1")){

                        Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        gm.openActivity(context.getApplicationContext(), SharingRequest.class);
                      //  notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(request);


    }


            public void RejectRequest(final String requestId, final String bookId){

                pd = new ProgressDialog(context.getApplicationContext());
                pd.setMessage("loading");
                pd.show();


                StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.REJECT_REQUEST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.dismiss();

                        Toast.makeText(context.getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject object = new JSONObject(response);
                            String success = object.getString("success");
                            String message = object.getString("message");
                            if (success.equals("0")){

                                Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                            }
                            if (success.equals("1")){

                                Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                               // notifyDataSetChanged();
                                gm.openActivity(context.getApplicationContext(), SharingRequest.class);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
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
                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                requestQueue.add(request);





            }


            public void  MarkAsShared(final String bookId){

                pd = new ProgressDialog(context.getApplicationContext());
                pd.setMessage("loading");
                pd.show();


                StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.MARKED_AS_SHARED, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    pd.dismiss();

                        Toast.makeText(context.getApplicationContext(),response,Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject object = new JSONObject(response);
                            String success = object.getString("success");
                            String message = object.getString("message");
                            if (success.equals("0")){

                                Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

                            }
                            if (success.equals("1")){

                                Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                             //   notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
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
                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                requestQueue.add(request);




            }


}
