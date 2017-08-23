package com.pk.shareabook.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Adapters.BooksCardItemsAdapter;
import com.pk.shareabook.FCM.RegistrationIntentService;
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

public class Dashboard extends AppCompatActivity {

    RecyclerView recyclerView;
   // List<CardItemPojo> cardList;

    HashMap<String,String> map;
    List<HashMap<String,String>> mapList;
    List<String> bookNames;
    EditText etSearch;
    GeneralMethods gm;
    DrawerLayout drawerLayout;

    BooksCardItemsAdapter adapter;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
       // cardList = new ArrayList<>();
        gm = new GeneralMethods();
        mapList = new ArrayList<>();
        bookNames = new ArrayList<>();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        etSearch= (EditText) toolbar.findViewById(R.id.etSearch);
        toolbar.inflateMenu(R.menu.menu_search_toolbar);


        Intent intent = new Intent(Dashboard.this,RegistrationIntentService.class);
        startService(intent);

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
                             gm.showToast(getApplicationContext(),"REQUESTED BOOKS");
                             break;
                         case (R.id.nav_sharing_requests):
                             gm.showToast(getApplicationContext(),"Sharing Request");
                             gm.openActivity(getApplicationContext(),SharingRequest.class);
                             break;
                         case (R.id.nav_shareed_books):
                             gm.showToast(getApplicationContext(),"Shared BOOKS");
                             break;
                         case (R.id.nav_recievedBooks):
                             gm.showToast(getApplicationContext(),"Recieved Books");
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

                switch(item.getItemId()){

                    case (R.id.search):
                        Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_SHORT).show();
                        if (etSearch.getVisibility()==View.VISIBLE){
                            etSearch.setVisibility(View.VISIBLE);
                        }
                        if (etSearch.getVisibility()==View.GONE){
                            etSearch.setVisibility(View.VISIBLE);

                        }

                        etSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String nameToSearch = etSearch.getText().toString();
                          List<HashMap<String,String>> mm = new ArrayList<HashMap<String, String>>();



                                for (HashMap<String, String> map : mapList) {
                                    for (Map.Entry<String, String> entry : map.entrySet()) {
                                          if (entry.getKey().equals("bookName") && entry.getValue().contains(nameToSearch))
//
                                        {
                                            mm.add( map);

                                        }
                                    }
                                }

                                adapter = new BooksCardItemsAdapter(mm,getApplicationContext());
                                recyclerView.setAdapter(adapter);




                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        break;

                    case (R.id.openMenu):


                        drawerLayout.openDrawer(Gravity.RIGHT);

                        Toast.makeText(getApplicationContext(),"Open Menu",Toast.LENGTH_SHORT).show();
                        break;

                }

                return true;
            }
        });



        prepareHashMap();




        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //adapter = new BooksCardItemsAdapter(cardList,getApplicationContext());

       // adapter.notifyDataSetChanged();
    }

   public void prepareHashMap(){

       final ProgressDialog pd = new ProgressDialog(Dashboard.this);
       pd.setMessage("loading");
       pd.show();


       StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GET_ALL_BOOKS, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
               pd.dismiss();
               try {
                   JSONObject object = new JSONObject(response);
                   String success = object.getString("success");

                   if (success.equals("0")){

                       Toast.makeText(getApplicationContext(),"No Details found",Toast.LENGTH_SHORT).show();

                   }
                   else {

                       JSONArray array = object.getJSONArray("allBooks");
                       for (int i=0;i<array.length();i++){

                           JSONObject obj = array.getJSONObject(i);
                           map = new HashMap<>();
                           map.put("authorName",obj.getString("author"));
                           map.put("bookName",obj.getString("title"));
                           bookNames.add(obj.getString("title"));
                           map.put("image",obj.getString("logo"));
                           map.put("book_id",obj.getString("id"));
                           map.put("owner_id",obj.getString("owner_id"));
                           mapList.add(map);



                       }

                       adapter = new BooksCardItemsAdapter(mapList,getApplicationContext());
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

               Toast.makeText(Dashboard.this,"Network Error",Toast.LENGTH_SHORT).show();
                    pd.dismiss();

           }
       }
       )
       {
           @Override
           protected Map<String, String> getParams()
           {
               Map<String, String>  params = new HashMap<String, String>();

               return params;
           }



       };
       RequestQueue requestQueue = Volley.newRequestQueue(Dashboard.this);
       requestQueue.add(request);








   }

}
