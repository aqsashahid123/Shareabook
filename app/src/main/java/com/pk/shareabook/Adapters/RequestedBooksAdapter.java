package com.pk.shareabook.Adapters;

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
import com.pk.shareabook.Activities.RequestedBooks;
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

public class RequestedBooksAdapter extends RecyclerView.Adapter<RequestedBooksAdapter.ViewHolderMenuIcon>{

    HashMap<String,String> SingleMap;
    List<HashMap<String,String>> mapList;

    Context mContext;


    public RequestedBooksAdapter(List<HashMap<String,String>> mapList, Context context){

        this.mapList = mapList;
        this.mContext = context;

    }


    @Override
    public ViewHolderMenuIcon onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_with_menu_icon,parent,false);

        return new ViewHolderMenuIcon(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderMenuIcon holder, final int position) {
        SingleMap = new HashMap<>();
        SingleMap = mapList.get(position);
        holder.tvBookName.setText(SingleMap.get("bookName"));
        holder.tvAuthorName.setText(SingleMap.get("authorName"));
        // holder.ivBookCover.setImageBitmap();;
        String imgLogo = SingleMap.get("image");

        Picasso.with(mContext.getApplicationContext()).load(END_POINTS.GET_BOOK_LOGO + imgLogo).into(holder.ivBookCover);




        holder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleMap = new HashMap<String, String>();
                SingleMap = mapList.get(position);
             String reqStatus =   SingleMap.get("request_status");
                if (reqStatus.equals("1")){

                    Context wrapper = new ContextThemeWrapper(mContext.getApplicationContext(), R.style.MyPopupMenu);

                    final PopupMenu popup = new PopupMenu(wrapper,holder.ivMenu );
                    popup.getMenuInflater().inflate(R.menu.popup_accepted_message, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.accepted:


                                    break;
                                case R.id.message:



                                    break;
                                case R.id.bookRecieved:
                                    SingleMap = mapList.get(position);
                             String requestedBookId =   SingleMap.get("book_id");
                                    bookReceived(requestedBookId);
                                    break;

                            }


                            return true;
                        }
                    });


                    popup.show();




                }  //Accepted
                if (reqStatus.equals("0")){

                    Toast.makeText(mContext.getApplicationContext(),"Request is Pending",Toast.LENGTH_SHORT).show();
            //REquest Is pending

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }

    public class ViewHolderMenuIcon extends RecyclerView.ViewHolder{


        public TextView tvAuthorName,tvBookName;
        CardView card_view;
        ImageView ivBookCover,ivMenu;
        RelativeLayout laCardView;

        public ViewHolderMenuIcon(View itemView) {
            super(itemView);
            tvAuthorName = (TextView) itemView.findViewById(R.id.bookAuthor);
            tvBookName = (TextView) itemView.findViewById(R.id.bookName);
            ivBookCover = (ImageView) itemView.findViewById(R.id.imageView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            laCardView = (RelativeLayout) itemView.findViewById(R.id.laCardView);
            ivMenu = (ImageView) itemView.findViewById(R.id.ivEditDelete);

        }
    }


    public  void bookReceived(final String bookId){


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.MARKED_AS_RECEIVED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(mContext.getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                //   pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    if (success.equals("1")){

                        Intent intent = new Intent(mContext.getApplicationContext(), RequestedBooks.class);
                        //SingleMap = new HashMap<>();

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);

                    }











                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mContext.getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                //     pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
                String userId =   preferences.getString("id","");

                //      params.put("bookId", result.get("id"));
                params.put("userId",userId);
                params.put("bookId",bookId );


//                params.put("password", password);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        requestQueue.add(request);




    }

}
