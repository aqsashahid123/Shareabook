package com.pk.shareabook.Activities;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
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
import com.pk.shareabook.GeneralMethods;
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

public class ProfileInfo extends AppCompatActivity {


    Spinner spinnerRegion,spinnerCity;
    ArrayList<String> spinnerDataCity, spinnerDataCountry;
    GeneralMethods gm;
    Toolbar toolbar;


    String f_name,l_name,i_name,d_name,cityId,regionId,institute;
    EditText etFname,etLname,etIname,etD_name;
    Button btnUpdateProfile;

    String id;
    String isActive;

    DrawerAdapter drawerAdapter;

    HashMap<String,String> regionMap;
    HashMap<String,String> citiesMap;

    String regionKey;
    String cityKey;
    SharedPreferences pref;
    private ListView mDrawerList;

    ///////////////DRAWER////////////////////
    List<DrawerPojo> drawerList;
        DrawerLayout drawerLayout;

 //   List<HashMap<String,String>> regionsMapList;
 //   List<HashMap<String,String>> citiesMapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);





        gm= new GeneralMethods();
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

                    //    drawerLayout.openDrawer(Gravity.RIGHT);
                   //     openDrawer();
                        break;

                }


                return true;
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////

        btnUpdateProfile = (Button)findViewById(R.id.btnUpdateProfile);
        etD_name= (EditText) findViewById(R.id.etDisplayName);
        etFname = (EditText) findViewById(R.id.etFname);
        etLname = (EditText) findViewById(R.id.etLname);
        etIname = (EditText) findViewById(R.id.etIname);
    //    mDrawerList = (ListView) findViewById(R.bookId.left_drawer);


        //////////////////////////////////DRAWER/////////////////////////
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

       // drawerLayout.openDrawer(Gravity.RIGHT);
        drawerList = new ArrayList<>();
        //openDrawer();
        //////////////////DRAWER////////////////////


//        drawerList.add(new DrawerPojo("Details"));
//        drawerList.add(new DrawerPojo("My Uploaded Books"));
//        drawerList.add(new DrawerPojo("Requested Books"));
//        drawerList.add(new DrawerPojo("Sharing Requests"));
//        drawerList.add(new DrawerPojo("Shared Books"));
//        drawerList.add(new DrawerPojo("Received Books"));
//        drawerList.add(new DrawerPojo("Upload Book"));
//        drawerList.add(new DrawerPojo("Logout"));

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
                    //     gm.showToast(getApplicationContext(),"REQUESTED BOOKS");
                         gm.openActivity(getApplicationContext(),RequestedBooks.class);
                         finish();
                         break;
                     case (R.id.nav_sharing_requests):
                      //   gm.showToast(getApplicationContext(),"Sharing Request");
                         gm.openActivity(getApplicationContext(),SharingRequest.class);
                         finish();
                         break;
                     case (R.id.nav_shareed_books):
                        // gm.showToast(getApplicationContext(),"Shared BOOKS");
                         gm.openActivity(getApplicationContext(), MySharedBooks.class);
                         finish();
                         break;
                     case (R.id.nav_recievedBooks):
                       //  gm.showToast(getApplicationContext(),"Recieved Books");
                         gm.openActivity(getApplicationContext(), RecievedBooks.class);
                         finish();
                         break;
                     case (R.id.nav_logOut):

                         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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




        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    id=    pref.getString("id","");
      f_name=  pref.getString("first_name","");
        l_name = pref.getString("last_name","");
       d_name = pref.getString("display_name","");
        isActive = pref.getString("isActive","");
        institute = pref.getString("institute","");

        etLname.setText(l_name);
        etFname.setText(f_name);
        etD_name.setText(d_name);
        etIname.setText(institute);


        spinnerDataCity = new ArrayList<>();
        spinnerDataCountry = new ArrayList<>();



        regionMap = new HashMap<>();
        citiesMap = new HashMap<>();

        getRegionsData();



        spinnerRegion = (Spinner) findViewById(R.id.region);
        spinnerCity = (Spinner) findViewById(R.id.city);



        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                getCityData(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(ProfileInfo.this, R.layout.bg_spinner_item,spinnerDataCountry);
//
//        spinnerRegion.setAdapter(regionAdapter);

    //    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ProfileInfo.this, android.R.layout.simple_spinner_item,spinnerDataCity);



        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                f_name = etFname.getText().toString();
                l_name = etLname.getText().toString();
                i_name = etIname.getText().toString();
                d_name = etD_name.getText().toString();
                isActive = pref.getString("isActive","");


//                regionId = regionKey;
//                cityId = cityKey;


                for (HashMap.Entry<String,String> e : citiesMap.entrySet() ){

                    String key = e.getKey();
                    String val = e.getValue();
                    if (val==spinnerCity.getSelectedItem()){

                        cityKey = key;
                        Toast.makeText(getApplicationContext(),key,Toast.LENGTH_SHORT).show();
                        //   getCities(key);
                    }

                }



                if (isActive.equals("1")) {

                    sendDataUserIsActive();
                }

                if (isActive.equals("0.5")){

                    sendDataUserInActive();

                }


            }
        });


    }

//    public void  openDrawer(){
//        drawerAdapter = new DrawerAdapter(this,drawerList,R.layout.drawer_list_item);
//        mDrawerList.setAdapter( drawerAdapter);
//
//    }
//


    public void getRegionsData(){

        final ProgressDialog pd = new ProgressDialog(ProfileInfo.this);
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

                    for (int i =0;i<regionArray.length();i++){

                        JSONObject obj = new JSONObject(regionArray.getString(i));

                        String abc = obj.getString("region_id");
                        String lmn = obj.getString("region_name");
                        regionMap.put(abc,lmn);
                        //regionsMapList.add(regionMap);
                        spinnerDataCountry.add(lmn);
                    }

                    ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(ProfileInfo.this, R.layout.bg_spinner_item,spinnerDataCountry);

                    spinnerRegion.setAdapter(regionAdapter);


                   // regionsMapList.add()


                    String status = object.get( "success").toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // object.get("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(),"Volley Error",Toast.LENGTH_SHORT).show();

            }
        }
        )
 {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
//                params.put("email", email);
//                params.put("password", password);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);




    }

        public void getCityData(int pos){


            for (HashMap.Entry<String,String> e : regionMap.entrySet() ){

                String key = e.getKey();
                String val = e.getValue();
                if (val==spinnerRegion.getSelectedItem()){

                    regionKey = key;
                    Toast.makeText(getApplicationContext(),key,Toast.LENGTH_SHORT).show();
                    getCities(key);
                }

            }




        }


        public void getCities(String Key){


    citiesMap.clear();
    //citiesMapList.clear();
    spinnerDataCity.clear();
    regionKey = Key;

    final ProgressDialog pd = new ProgressDialog(ProfileInfo.this);
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

                for (int i =0;i<CitiesArray.length();i++){

                    JSONObject obj = new JSONObject(CitiesArray.getString(i));

                    String abc = obj.getString("city_id");
                    String lmn = obj.getString("city_name");
                    citiesMap.put(abc,lmn);
                    //citiesMapList.add(citiesMap);
                    spinnerDataCity.add(lmn);





                    //  spinnerDataCountry.add(lmn);
                }

                ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(ProfileInfo.this, R.layout.bg_spinner_item,spinnerDataCity);

                spinnerCity.setAdapter(citiesAdapter);




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
            Toast.makeText(getApplicationContext(),"Volley Error",Toast.LENGTH_SHORT).show();

        }
    }
    )
    {
        @Override
        protected Map<String, String> getParams()
        {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("region_id", regionKey);
//                params.put("password", password);
//
            return params;
        }



    };
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(request);






}

public void sendDataUserIsActive(){



//    bookId =  pref.getString("bookId","");
//    f_name = pref.getString("first_name","");

    final ProgressDialog pd = new ProgressDialog(ProfileInfo.this);
    pd.setMessage("loading");
    pd.show();



    StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.UPDATE_PROFILE, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
       // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            try {
                JSONObject object = new JSONObject(response);
                pd.dismiss();

                 String abcf =   object.get("success").toString();
                String details = object.get("message").toString();

                if (abcf.equals("0")){
                    Toast.makeText(getApplicationContext(),details,Toast.LENGTH_SHORT).show();


                }

                else {

                    JSONObject userDetails = object.getJSONObject("user_details");

                    String fname = userDetails.get("first_name").toString();
                    String lname = userDetails.get("last_name").toString();
                    String iname = userDetails.get("institute").toString();
                    String dname = userDetails.get("display_name").toString();
                    String cityId = userDetails.get("city_id").toString();
                    String regionId = userDetails.get("region_id").toString();


                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    // editor.putString("","");

                    editor.putString("id", id);
                    editor.putString("isActive", isActive);
                    editor.putString("city_id", cityId);
                    editor.putString("region_id", regionId);
                    editor.putString("first_name", fname);
                    editor.putString("last_name", lname);
                    editor.putString("institute", iname);
                    editor.putString("display_name", dname);
                    editor.apply();

                    Toast.makeText(getApplicationContext(),details,Toast.LENGTH_SHORT).show();

                }
//                Toast.makeText(getApplicationContext(),abcf,Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // object.get("");

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            pd.dismiss();
            Toast.makeText(getApplicationContext(),"Volley Error",Toast.LENGTH_SHORT).show();

        }
    }
    )
    {
        @Override
        protected Map<String, String> getParams()
        {
            Map<String, String>  params = new HashMap<String, String>();


//            pref.getString("first_name","");
//            pref.getString("first_name","");
//            pref.getString("first_name","");
//            pref.getString("first_name","");


            params.put("userId", id);
            params.put("fName",f_name);
            params.put("lName",l_name);
            params.put("dName",d_name);
            params.put("iName",i_name);
            params.put("city_id", cityKey);
            params.put("region_id",regionKey);
//                params.put("password", password);
//
            return params;
        }



    };
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    requestQueue.add(request);




}
 public  void    sendDataUserInActive(){



     final ProgressDialog pd = new ProgressDialog(ProfileInfo.this);
     pd.setMessage("loading");
     pd.show();



     StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.UPDATE_PRO_USER_INACTIVE, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
             Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
             try {
                 JSONObject object = new JSONObject(response);
                 pd.dismiss();

                 String abcf =   object.get("success").toString();

                 if (abcf.equals("1")) {

                     JSONObject userDetails = object.getJSONObject("user_details");

                     String fname = userDetails.get("first_name").toString();
                     String lname = userDetails.get("last_name").toString();
                     String iname = userDetails.get("institute").toString();
                     String dname = userDetails.get("display_name").toString();
                     String cityId = userDetails.get("city_id").toString();
                     String regionId = userDetails.get("region_id").toString();


                     SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                     SharedPreferences.Editor editor = preferences.edit();
                     editor.clear();
                     // editor.putString("","");

                     editor.putString("bookId", id);
                     editor.putString("isActive", isActive);
                     editor.putString("city_id", cityId);
                     editor.putString("region_id", regionId);
                     editor.putString("first_name", fname);
                     editor.putString("last_name", lname);
                     editor.putString("institute", iname);
                     editor.putString("display_name", dname);
                     editor.apply();

                 }

                 if (abcf.equals("0")){

                     Toast.makeText(getApplicationContext(),"Profile Not updated",Toast.LENGTH_SHORT).show();
                 }


                 Toast.makeText(getApplicationContext(),abcf,Toast.LENGTH_SHORT).show();

             } catch (JSONException e) {
                 e.printStackTrace();
             }
             // object.get("");

         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {

             Toast.makeText(getApplicationContext(),"Volley Error",Toast.LENGTH_SHORT).show();

         }
     }
     )
     {
         @Override
         protected Map<String, String> getParams()
         {
             Map<String, String>  params = new HashMap<String, String>();


//            pref.getString("first_name","");
//            pref.getString("first_name","");
//            pref.getString("first_name","");
//            pref.getString("first_name","");


             params.put("userId", id);
             params.put("fName",f_name);
             params.put("lName",l_name);
             params.put("dName",d_name);
             params.put("iName",i_name);
             params.put("city_id", cityKey);
             params.put("region_id",regionKey);
//                params.put("password", password);
//
             return params;
         }



     };
     RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
     requestQueue.add(request);

 }


}
