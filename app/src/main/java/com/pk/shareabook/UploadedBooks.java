package com.pk.shareabook;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    ArrayList<HashMap<String,String>> mapList;
    HashMap<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_books);
        lvMyUploadedBooks = (ListView) findViewById(R.id.lvMyUploadedBooks);
        map = new HashMap<>();
        mapList = new ArrayList<>();


         pd = new ProgressDialog(UploadedBooks.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GET_MY_UPLOADEBOOKS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("0")){
                        Toast.makeText(getApplicationContext(),"No Books Uploaded",Toast.LENGTH_SHORT).show();

                    }
                    //    String regions =   object.get("regions").toString();

                    else {
                        JSONArray regionArray = object.getJSONArray("myUploadedBooks");

                        for (int i = 0; i < regionArray.length(); i++) {

                            JSONObject obj = new JSONObject(regionArray.getString(i));

                            map.put("id",obj.getString("id"));
                            map.put("title",obj.getString("title"));
                            map.put("author",obj.getString("author"));
                            map.put("logo",obj.getString("logo"));
                            map.put("institute",obj.getString("institute"));
                            map.put("city_id",obj.getString("city_id"));
                            map.put("region_id",obj.getString("region_id"));
                            map.put("owner_id",obj.getString("owner_id"));
                            map.put("status",obj.getString("status"));
                            map.put("shared_with",obj.getString("shared_with"));
                            map.put("updated_at",obj.getString("updated_at"));
                            map.put("created_at",obj.getString("created_at"));

                            mapList.add(map);



//                            String abc = obj.getString("region_id");
//                            String lmn = obj.getString("region_name");
                        //    regionMap.put(abc, lmn);
                            //regionsMapList.add(regionMap);
                           // spinnerDataCountry.add(lmn);
                        }

                    }

                    MyUploadedBooksAdapter adapter = new MyUploadedBooksAdapter(getApplicationContext(), mapList);

                    lvMyUploadedBooks.setAdapter(adapter);


                    lvMyUploadedBooks.setLongClickable(true);
                    lvMyUploadedBooks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            Toast.makeText(getApplicationContext(),mapList.get(position).get("id"),Toast.LENGTH_SHORT).show();

                            return true;
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
