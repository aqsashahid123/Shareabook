package com.pk.shareabook.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUp extends AppCompatActivity {


    String result;
    Button btnSignUp;
    EditText etEmail,etPassword,etConfirmPassword;
    String email,password,confirmPassword;
    String emailPattern ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";




         //   "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.btnUpdateProfile);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);




        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                password= etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();




                Patterns.EMAIL_ADDRESS.matcher(email).matches();

//                if ( Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.equals(confirmPassword) && password.length() > 7){
//
//                    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
//                }


                if ( !Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_SHORT).show();

                }
                if (!(password.equals(confirmPassword))){

                    Toast.makeText(getApplicationContext(),"Password and Confirmation Password should be same",Toast.LENGTH_SHORT).show();


                }

                if (password.length()<7){
                    Toast.makeText(getApplicationContext(),"Password too short",Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();


                    StringRequest postRequest = new StringRequest(Request.Method.POST, END_POINTS.SIGN_UP,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String res =   jsonObject.get("message").toString();
                                        result = jsonObject.get("success").toString();
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();


                                        if (result.equals("0")){

                                            Intent intent = new Intent(SignUp.this,MainActivity.class);
                                            startActivity(intent);


                                        }
                                        if (result.equals("1")){

//                                            Intent intent = new Intent(SignUp.this,ProfileInfo.class);
//                                            startActivity(intent);


                                            Intent intent = new Intent(SignUp.this,MainActivity.class);
                                            startActivity(intent);


                                        }



                                        Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();




                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("reg_email", email);
                            params.put("reg_password", password);

                            return params;
                        }
                    };
                    //queue.add(postRequest);
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(postRequest);


                }

            }
        });

    }
}
