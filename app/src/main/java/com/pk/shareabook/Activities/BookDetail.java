package com.pk.shareabook.Activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.GeneralMethods;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookDetail extends AppCompatActivity {

    TextView tvTitle,tvAuthor,tvOwner,tvCity,tvLocations,tvInstitute,tvRRegion;
    String displayName,author,title,city,institute,region,location,imgLogo;

    CollapsingToolbarLayout collapsingToolbarLayout;

    CircleImageView circularImageView;
    EditText etMessage;
    String senderId,recieverId,message;
    DrawerLayout drawerLayout;
    Button requestBook;
    List<String> list;

    GeneralMethods gm;
    String bookId,userId;
    String locations;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        gm =  new GeneralMethods();
        list = new ArrayList<>();
        requestBook  = (Button) findViewById(R.id.requestBook);


        bookId = getIntent().getStringExtra("bookId");
        etMessage = (EditText) findViewById(R.id.etMessage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        senderId = preferences.getString("id","");


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new
         NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){

                     case (R.id.nav_profile):

                         gm.openActivity(getApplicationContext(), ProfileInfo.class);
                         finish();
                         break;
                     case (R.id.nav_dashboard):
                         gm.openActivity(getApplicationContext(), Dashboard.class);
                        // finish();
                         break;

                     case (R.id.nav_uploaded_Books):
                         gm.openActivity(getApplicationContext(),UploadedBooks.class);
                         finish();
                         break;
                     case (R.id.nav_upload_Books):
                         gm.openActivity(getApplicationContext(),UploadBook.class);
                         finish();
                         break;
                     case (R.id.nav_requested_books):
                        // gm.showToast(getApplicationContext(),"REQUESTED BOOKS");
                         gm.openActivity(getApplicationContext(),RequestedBooks.class);
                         finish();
                         break;
                     case (R.id.nav_sharing_requests):
                      //   gm.showToast(getApplicationContext(),"Sharing Request");
                         gm.openActivity(getApplicationContext(),SharingRequest.class);
                         finish();
                         break;
                     case (R.id.nav_shareed_books):
                      //   gm.showToast(getApplicationContext(),"Shared BOOKS");
                         gm.openActivity(getApplicationContext(), MySharedBooks.class);
                         finish();
                         break;
                     case (R.id.nav_recievedBooks):
                        // gm.showToast(getApplicationContext(),"Recieved Books");
                         gm.openActivity(getApplicationContext(), RecievedBooks.class);
                         finish();

                         break;
                     case (R.id.nav_logOut):
                       preferences.edit().clear().apply();

                         gm.openActivity(getApplicationContext(), MainActivity.class);
                         finish();
                         break;
                     case (R.id.nav_search):
                         gm.openActivity(getApplicationContext(), MainScreen.class);
                         finish();
                         break;

//                   case ():
//                       break;


                 }




                 return true;
             }
         });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id){

                    case R.id.openMenu:

                        drawerLayout.openDrawer(Gravity.RIGHT);

                        break;

                }


                return true;
            }
        });






        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        circularImageView = (CircleImageView) findViewById(R.id.profile_image);


        final ProgressDialog pd = new ProgressDialog(BookDetail.this);
        pd.setMessage("loading");
        pd.show();


        tvAuthor = (TextView) findViewById(R.id.author);
        tvCity = (TextView) findViewById(R.id.city);
        tvInstitute = (TextView) findViewById(R.id.institute);
        tvLocations = (TextView) findViewById(R.id.location);
        tvOwner = (TextView) findViewById(R.id.owner);
        tvTitle = (TextView)findViewById(R.id.bookTitle);
        tvRRegion = (TextView) findViewById(R.id.tvRegion);




        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GET_BOOK_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                   pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("0")){

                        Toast.makeText(getApplicationContext(),"No Details found",Toast.LENGTH_SHORT).show();

                    }
                    else {

                        JSONObject obj = object.getJSONObject("bookDetails");

                        displayName = obj.getString("display_name");

                       recieverId = obj.getString("owner_id");

                        tvOwner.setText(displayName);

                        author = obj.getString("author");
                        tvAuthor.setText(author);
                        title = obj.getString("title");
                        collapsingToolbarLayout.setTitle(title);


                        toolbar.setTitle(title);
                        tvTitle.setText(title);
                        region = obj.getString("region_name");
                        tvRRegion.setText(region);



                        //location = obj.getString("bl_location");
//tvLocations.setText(location);
                        institute = obj.getString("institute");
                        tvInstitute.setText(institute);
                      String  city = obj.getString("city_name");
                        tvCity.setText(city);

                        imgLogo = obj.getString("logo");
                        Picasso.with(getApplicationContext()).load(END_POINTS.GET_BOOK_LOGO + imgLogo).into(circularImageView);


                        JSONArray arr = object.getJSONArray("bookLocations");

                   //   JSONObject loc = arr.getJSONObject(2);
                        for (int i = 0; i< arr.length() ; i++){

                        JSONObject obje = arr.getJSONObject(i);
                            location = obje.getString("bl_location");
                            list.add(location);



                        }
                        String s =  android.text.TextUtils.join(", ", list);
                        tvLocations.setText(s);

                    }










                } catch (JSONException e) {
                    e.printStackTrace();
                  //  Toast.makeText(getApplicationContext(),)
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(BookDetail.this,"Network Error",Toast.LENGTH_SHORT).show();
                     pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                //   preferences.getString("bookId","");

                //      params.put("bookId", result.get("bookId"));

                params.put("book_id", bookId);


//                params.put("password", password);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetail.this);
        requestQueue.add(request);




        requestBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             message=   etMessage.getText().toString();

                if (senderId.equals(recieverId) ) {
                    Toast.makeText(getApplicationContext(),"You can not send a book request to yourself",Toast.LENGTH_SHORT).show();

                }

                else {
                    sendRequest();
                }
            }
        });





    }


    public void sendRequest(){

        final ProgressDialog pd = new ProgressDialog(BookDetail.this);
        pd.setMessage("loading");
        pd.show();




        final StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.SEND_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();

              //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");
                    String message = object.getString("message");
                    if (success.equals("0")){

                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        gm.openActivity(getApplicationContext(),Dashboard.class);
                        finish();
                    }



                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(BookDetail.this,"Network Error",Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                //   preferences.getString("bookId","");

                //      params.put("bookId", result.get("bookId"));

                params.put("bookId", bookId);
                params.put("senderId",senderId);
                params.put("receiverId",recieverId);
                params.put("requestMessage",message);


                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetail.this);
        requestQueue.add(request);

    }


}
