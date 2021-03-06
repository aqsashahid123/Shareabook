package com.pk.shareabook.Activities;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Adapters.BooksCardItemsAdapter;
import com.pk.shareabook.GeneralMethods;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySharedBooks extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    GeneralMethods gm;
    RecyclerView recyclerView;
    DrawerLayout drawerLayout;

    HashMap<String, String> map;
    List<HashMap<String, String>> mapList;
    BooksCardItemsAdapter adapter;

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shared_books);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        id = preferences.getString("id", "");
        mapList = new ArrayList<>();

        /////////////////////////////////////
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        gm = new GeneralMethods();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setTitle("Shared Books");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case (R.id.openMenu):
                        drawerLayout.openDrawer(Gravity.RIGHT);
                        break;

                }

                return true;
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case (R.id.nav_profile):

                        gm.openActivity(getApplicationContext(), ProfileInfo.class);
                        finish();

                        break;
                    case (R.id.nav_dashboard):
                        gm.openActivity(getApplicationContext(), Dashboard.class);
                     //   finish();
                        break;

                    case (R.id.nav_uploaded_Books):
                        gm.openActivity(getApplicationContext(), UploadedBooks.class);
                        finish();
                        break;
                    case (R.id.nav_upload_Books):
                        gm.openActivity(getApplicationContext(), UploadBook.class);
                        finish();
                        break;
                    case (R.id.nav_requested_books):
                        gm.openActivity(getApplicationContext(),RequestedBooks.class);
                        finish();
                     //   gm.showToast(getApplicationContext(), "REQUESTED BOOKS");
                        break;
                    case (R.id.nav_sharing_requests):
                       // gm.showToast(getApplicationContext(), "Sharing Request");
                        gm.openActivity(getApplicationContext(), SharingRequest.class);
                        finish();
                        break;
                    case (R.id.nav_shareed_books):
                        gm.openActivity(getApplicationContext(), MySharedBooks.class);
                        finish();
                        //gm.showToast(getApplicationContext(), "Shared BOOKS");
                        break;
                    case (R.id.nav_recievedBooks):
                        //gm.showToast(getApplicationContext(), "Recieved Books");
                        gm.openActivity(getApplicationContext(), RecievedBooks.class);
                        finish();
                        break;
                    case (R.id.nav_logOut):

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        preferences.edit().clear().apply();
                        gm.openActivity(getApplicationContext(), MainActivity.class);
                        break;
                    case (R.id.nav_search):
                        gm.openActivity(getApplicationContext(), MainScreen.class);
                        finish();
                        break;


                }


                return true;
            }
        });


        prepareHashMap();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    public void prepareHashMap() {

        final ProgressDialog pd = new ProgressDialog(MySharedBooks.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GET_SHARED_BOOKS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            //    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("0")) {

                        Toast.makeText(getApplicationContext(), "No Details found", Toast.LENGTH_SHORT).show();

                    } else {

                        JSONArray array = object.getJSONArray("sharedBooks");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject obj = array.getJSONObject(i);
                            map = new HashMap<>();
                            map.put("book_id", obj.getString("id"));
                            map.put("bookName", obj.getString("title"));
                            map.put("authorName", obj.getString("author"));

                            //bookNames.add(obj.getString("title"));
                            map.put("image", obj.getString("logo"));

                            map.put("owner_id", obj.getString("owner_id"));
                            mapList.add(map);


                        }

                        adapter = new BooksCardItemsAdapter(mapList, getApplicationContext());
                        recyclerView.setAdapter(adapter);


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

                Toast.makeText(MySharedBooks.this, "Network Error", Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId",id);
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(MySharedBooks.this);
        requestQueue.add(request);


    }
}
