package com.pk.shareabook.Activities;

import android.app.ProgressDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Adapters.DrawerAdapter;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.Pojo.DrawerPojo;
import com.pk.shareabook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainScreen extends AppCompatActivity {

    Spinner spinnerRegions, spinnerCities;
    HashMap<String, String> regionMap, citiesMap;
    String cityKey,regionKey;
    List<String> spinnerDataCountry, spinnerDataCity;
////////////////////////DRAWER LAYOUT/////////////////////////
List<DrawerPojo> drawerList;
    DrawerLayout drawerLayout;
    ListView mDrawerList;
    DrawerAdapter drawerAdapter;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        spinnerCities = (Spinner) findViewById(R.id.getCity);
        spinnerRegions = (Spinner) findViewById(R.id.getRegions);

        spinnerDataCity = new ArrayList<>();
        spinnerDataCountry = new ArrayList<>();
        regionMap = new HashMap<>();
        citiesMap = new HashMap<>();
        getRegionsData();

        toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle("MY DETAILS");
        toolbar.inflateMenu(R.menu.toolbar_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                switch (id){

                    case R.id.openMenu:

                        drawerLayout.openDrawer(Gravity.RIGHT);
                        //     openDrawer();
                        break;

                }


                return true;
            }
        });


        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // drawerLayout.openDrawer(Gravity.RIGHT);
        drawerList = new ArrayList<>();
        openDrawer();
        //////////////////DRAWER////////////////////


        drawerList.add(new DrawerPojo("Details"));
        drawerList.add(new DrawerPojo("My Uploaded Books"));
        drawerList.add(new DrawerPojo("Requested Books"));
        drawerList.add(new DrawerPojo("Sharing Requests"));
        drawerList.add(new DrawerPojo("Shared Books"));
        drawerList.add(new DrawerPojo("Received Books"));
        drawerList.add(new DrawerPojo("Upload Book"));
        drawerList.add(new DrawerPojo("Logout"));





        spinnerRegions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCityData(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    public void  openDrawer(){
        drawerAdapter = new DrawerAdapter(this,drawerList,R.layout.drawer_list_item);
        mDrawerList.setAdapter( drawerAdapter);

    }


    /////////////////
    public void getRegionsData() {

        final ProgressDialog pd = new ProgressDialog(MainScreen.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GETREGIONS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    //    String regions =   object.get("regions").toString();

                    JSONArray regionArray = object.getJSONArray("regions");

                    for (int i = 0; i < regionArray.length(); i++) {

                        JSONObject obj = new JSONObject(regionArray.getString(i));

                        String abc = obj.getString("region_id");
                        String lmn = obj.getString("region_name");
                        regionMap.put(abc, lmn);
                        //regionsMapList.add(regionMap);
                        spinnerDataCountry.add(lmn);
                    }

                    ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(MainScreen.this, R.layout.bg_spinner_item, spinnerDataCountry);

                    spinnerRegions.setAdapter(regionAdapter);


                    // regionsMapList.add()


                    String status = object.get("success").toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", email);
//                params.put("password", password);
//
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }


    public void getCityData(int pos) {


        for (HashMap.Entry<String, String> e : regionMap.entrySet()) {

            String key = e.getKey();
            String val = e.getValue();
            if (val == spinnerRegions.getSelectedItem()) {

                regionKey = key;
                Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
                getCities(key);
            }

        }


    }


    public void getCities(String Key) {


        citiesMap.clear();
        //citiesMapList.clear();
        spinnerDataCity.clear();
        regionKey = Key;

        final ProgressDialog pd = new ProgressDialog(MainScreen.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GETCITIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    pd.dismiss();
                    JSONObject object = new JSONObject(response);
                    //    String regions =   object.get("regions").toString();

                    JSONArray CitiesArray = object.getJSONArray("cities");

                    for (int i = 0; i < CitiesArray.length(); i++) {

                        JSONObject obj = new JSONObject(CitiesArray.getString(i));

                        String abc = obj.getString("city_id");
                        String lmn = obj.getString("city_name");
                        citiesMap.put(abc, lmn);
                        //citiesMapList.add(citiesMap);
                        spinnerDataCity.add(lmn);
                        //  spinnerDataCountry.add(lmn);
                    }

                    ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(MainScreen.this, R.layout.bg_spinner_item, spinnerDataCity);

                    spinnerCities.setAdapter(citiesAdapter);


                    for (HashMap.Entry<String, String> e : citiesMap.entrySet()) {

                        String key = e.getKey();
                        String val = e.getValue();
                        if (val == spinnerCities.getSelectedItem()) {

                            cityKey = key;
                            Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
                            //   getCities(key);
                        }

                    }


                    // regionsMapList.add()


                    //  String status = object.get( "success").toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Volley Error", Toast.LENGTH_SHORT).show();

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("region_id", regionKey);
//                params.put("password", password);
//
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }
}