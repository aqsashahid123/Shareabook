package com.pk.shareabook.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tvSignUp;


    public static boolean Flag;

    EditText etEmail, etPassword;


    String email,password,success,isActive,id,city_id,display_name, firstName,LastName,displayPic,institute,regionId;

    Button btnSubmit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Flag){
            Intent intent = new Intent(MainActivity.this,Dashboard.class);
            startActivity(intent);
            finish();

        }


        tvSignUp = (TextView)findViewById(R.id.tvSignUp);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSubmit = (Button) findViewById(R.id.btnUpdateProfile);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();


                if (email.length()==0|| password.length()==0){


                    Toast.makeText(getApplicationContext(),"Email and Password are required",Toast.LENGTH_SHORT).show();
                }

                else {


                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading");
                    progressDialog.show();

                    StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.LOGIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();
                            try {
                                JSONObject object = new JSONObject(response);
                              success   =  object.get("success").toString();
                             //   String message = object.get("message").toString();

                                if (success.equals("0")){

                                   Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();

                                }
                            if (success.equals("0.5")){

                                id = object.get("id").toString();
                                isActive = object.get("isActive").toString();

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("id", id);
                                editor.putString("isActive", isActive);
                                editor.apply();


                                Intent intent = new Intent(MainActivity.this, ProfileInfo.class);
                                startActivity(intent);




                            }

                                if (success.equals("1")) {
                                    id = object.get("id").toString();
                                    isActive = object.get("isActive").toString();

                                    //   if (isActive.equals(1)){
                                    city_id = object.get("city_id").toString();
                                    regionId = object.get("region_id").toString();
                                    firstName = object.get("first_name").toString();
                                    LastName = object.get("last_name").toString();
                                    institute = object.get("institute").toString();
                                    //  displayPic = object.get().toString();
                                    display_name = object.get("display_name").toString();


                                    //      }


                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("id", id);
                                    editor.putString("isActive", isActive);
                                    editor.putString("city_id", city_id);
                                    editor.putString("region_id", regionId);
                                    editor.putString("first_name", firstName);
                                    editor.putString("last_name", LastName);
                                    editor.putString("institute", institute);
                                    editor.putString("display_name", display_name);
                                    editor.apply();


                                    if (Integer.valueOf(isActive) == 0.5) {

                                        Intent intent = new Intent(MainActivity.this, ProfileInfo.class);
                                        startActivity(intent);

                                    }
                                    if (Integer.valueOf(isActive) == 1) {
                                        Flag = true;
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }

//                                if ()



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // object.get("");

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Volley Error",Toast.LENGTH_SHORT).show();

                        }
                    }
                    ){
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("email", email);
                            params.put("password", password);

                            return params;
                        }



                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);




                }



            }
        });



        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);

            }
        });

    }
}
