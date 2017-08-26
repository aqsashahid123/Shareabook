package com.pk.shareabook.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Adapters.MyUploadedBooksAdapter;
import com.pk.shareabook.Adapters.UploadedBooksAdapter;
import com.pk.shareabook.GeneralMethods;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UploadedBooks extends AppCompatActivity {

  //  RecyclerView recyclerView;
    ProgressDialog pd;
    Toolbar toolbar;

    RecyclerView recyclerView;

    DrawerLayout drawerLayout;
    GeneralMethods gm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_books);
      //  recyclerView = (RecyclerView) findViewById(R.id.lvMyUploadedBooks);



        gm = new GeneralMethods();

            toolbar = (Toolbar) findViewById(R.id.appBar);

        toolbar.setTitle("MY BOOKS");

        toolbar.inflateMenu(R.menu.toolbar_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new
         NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){

                     case (R.id.nav_profile):

                         gm.openActivity(getApplicationContext(), ProfileInfo.class);

                         break;
                     case (R.id.nav_dashboard):
                         gm.openActivity(getApplicationContext(), Dashboard.class);
                         break;

                     case (R.id.nav_uploaded_Books):
                         gm.openActivity(getApplicationContext(),UploadedBooks.class);
                         break;
                     case (R.id.nav_upload_Books):
                         gm.openActivity(getApplicationContext(),UploadBook.class);
                         break;
                     case (R.id.nav_requested_books):
                    //     gm.showToast(getApplicationContext(),"REQUESTED BOOKS");
                         gm.openActivity(getApplicationContext(),RequestedBooks.class);

                         break;
                     case (R.id.nav_sharing_requests):
                      //   gm.showToast(getApplicationContext(),"Sharing Request");
                         gm.openActivity(getApplicationContext(),SharingRequest.class);
                         break;
                     case (R.id.nav_shareed_books):
                        // gm.showToast(getApplicationContext(),"Shared BOOKS");
                         gm.openActivity(getApplicationContext(), MySharedBooks.class);

                         break;
                     case (R.id.nav_recievedBooks):
                     //    gm.showToast(getApplicationContext(),"Received Books");
                         gm.openActivity(getApplicationContext(), RecievedBooks.class);

                         break;
                     case (R.id.nav_logOut):
                         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                         preferences.edit().clear().apply();
                         gm.openActivity(getApplicationContext(), MainActivity.class);

                         break;
                     case (R.id.nav_search):
                         gm.openActivity(getApplicationContext(), MainScreen.class);
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
                switch (item.getItemId())
                {
                    case R.id.openMenu:

                        drawerLayout.openDrawer(Gravity.RIGHT);
                        break;


                }

                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.lvMyUploadedBooks);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(UploadedBooks.this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

         pd = new ProgressDialog(UploadedBooks.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GET_MY_UPLOADEBOOKS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              final   ArrayList<HashMap<String,String>> mapList = new ArrayList<>();


                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("0")){
                        Toast.makeText(getApplicationContext(),"No Books Uploaded",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        JSONArray myUploadedBooks = object.getJSONArray("myUploadedBooks");

                        for (int i = 0; i < myUploadedBooks.length(); i++) {

                            HashMap<String,String> map = new HashMap<>();


                            JSONObject bookDetail =new JSONObject( myUploadedBooks.getString(i));

                            map.put("bookId",bookDetail.getString("id"));
                            map.put("title",bookDetail.getString("title"));
                            map.put("author",bookDetail.getString("author"));
                            map.put("logo",bookDetail.getString("logo"));
                            map.put("institute",bookDetail.getString("institute"));
                            map.put("city_id",bookDetail.getString("city_id"));
                            map.put("region_id",bookDetail.getString("region_id"));
                            map.put("owner_id",bookDetail.getString("owner_id"));
                            map.put("status",bookDetail.getString("status"));
                            map.put("shared_with",bookDetail.getString("shared_with"));
                            map.put("updated_at",bookDetail.getString("updated_at"));
                            map.put("created_at",bookDetail.getString("created_at"));

                            mapList.add(map);
                        }





                        UploadedBooksAdapter adapter = new UploadedBooksAdapter( mapList, UploadedBooks.this);

                        recyclerView.setAdapter(adapter);



                    }


//                    lvMyUploadedBooks.setLongClickable(true);
//                    lvMyUploadedBooks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                        @Override
//                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long bookId) {
//
//                            Toast.makeText(getApplicationContext(),mapList.get(position).get("bookId"),Toast.LENGTH_SHORT).show();
//
//                            return true;
//                        }
//                    });

//                    lvMyUploadedBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Toast.makeText(getApplicationContext(),mapList.get(position).get("bookId"),Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(getApplicationContext(),BookDetail.class);
//                            intent.putExtra("bookId",mapList.get(position).get("bookId"));
//                            startActivity(intent);
//
//
//
//                        }
  //                  });


                    // regionsMapList.add()


               //     String status = object.get( "success").toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
               // preferences.getString("bookId","");

                params.put("userId", preferences.getString("id",""));
//                params.put("password", password);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);




    }
}
