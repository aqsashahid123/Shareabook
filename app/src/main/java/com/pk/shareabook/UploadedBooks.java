package com.pk.shareabook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Activities.ProfileInfo;
import com.pk.shareabook.Adapters.DrawerAdapter;
import com.pk.shareabook.Adapters.MyUploadedBooksAdapter;
import com.pk.shareabook.Network.END_POINTS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UploadedBooks extends AppCompatActivity {

    ListView lvMyUploadedBooks;
    ProgressDialog pd;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_books);
        lvMyUploadedBooks = (ListView) findViewById(R.id.lvMyUploadedBooks);


            toolbar = (Toolbar) findViewById(R.id.appBar);

        toolbar.setTitle("MY BOOKS");

        toolbar.inflateMenu(R.menu.toolbar_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new
         NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 return false;
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

                            map.put("id",bookDetail.getString("id"));
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

                        MyUploadedBooksAdapter adapter = new MyUploadedBooksAdapter(getApplicationContext(), mapList);

                        lvMyUploadedBooks.setAdapter(adapter);



                    }


//                    lvMyUploadedBooks.setLongClickable(true);
//                    lvMyUploadedBooks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                        @Override
//                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            Toast.makeText(getApplicationContext(),mapList.get(position).get("id"),Toast.LENGTH_SHORT).show();
//
//                            return true;
//                        }
//                    });

                    lvMyUploadedBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getApplicationContext(),mapList.get(position).get("id"),Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(),BookDetail.class);
                            intent.putExtra("bookId",mapList.get(position).get("id"));
                            startActivity(intent);



                        }
                    });


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
                preferences.getString("id","");

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
