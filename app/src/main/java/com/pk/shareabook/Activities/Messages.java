package com.pk.shareabook.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Adapters.MessageAddapter;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.Pojo.MessagesPojo;
import com.pk.shareabook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Messages extends AppCompatActivity {

    ListView mlistView;
    ArrayList<MessagesPojo> messagesPojos;
    EditText messageEDT;
    Button SendMessage;
    String messageEntered;
    MessageAddapter messageAddapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Intent  intent = getIntent();

        if(intent.hasExtra("update"))
        {
            getMessagesupdate();
        }

        mlistView = (ListView) findViewById(R.id.messageView);
        messageEDT = (EditText) findViewById(R.id.messageTXT);
        SendMessage = (Button) findViewById(R.id.sendmessage);

        mlistView.setStackFromBottom(true);
        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                messageEntered = messageEDT.getText().toString();

                if(messageEntered.equals(""))
                {
                    Toast.makeText(Messages.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
                else{
                    sendMessage();
                }

            }
        });

        getMessages();


    }


    public void getMessages() {
        final ProgressDialog progressDialog = new ProgressDialog(Messages.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GET_ALL_MESSAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                messagesPojos = new ArrayList<>();
                progressDialog.dismiss();
                try {

                    JSONObject object = new JSONObject(response);
                    String success = object.get("success").toString();
                    //   String message = object.get("message").toString();

                    if (success.equals("0")) {

                        Toast.makeText(getApplicationContext(), "No Messages Found", Toast.LENGTH_SHORT).show();
                    }
                    if (success.equals("1")) {

                        String json = object.getString("chat");
                        JSONArray jsonArray = new JSONArray(json);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            MessagesPojo messagesPojo = new MessagesPojo();

                            messagesPojo.setChat_reciever(jsonObject.getString("first_name") + " " + jsonObject.getString("last_name"));
                            messagesPojo.setChat_request_id(jsonObject.getString("chat_of_request_id"));
                            messagesPojo.setChat_sender_id(jsonObject.getString("chat_sender_id"));
                            messagesPojo.setDate(jsonObject.getString("chat_date_time"));
                            messagesPojo.setMessage(jsonObject.getString("chat_message"));
                            messagesPojo.setRecieverID(jsonObject.getString("chat_receiver_id"));
                            messagesPojo.setChatid(jsonObject.getString("chat_id"));

                            messagesPojos.add(messagesPojo);

                        }

                        messageAddapter = new MessageAddapter(Messages.this, R.layout.row_messages, messagesPojos);

                        mlistView.setAdapter(messageAddapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("userId", "12");
                params.put("chat_of_request_id", "6");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    public void getMessagesupdate() {

        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.GET_ALL_MESSAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                messagesPojos = new ArrayList<>();
               // progressDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.get("success").toString();
                    //   String message = object.get("message").toString();

                    if (success.equals("0")) {

                        Toast.makeText(getApplicationContext(), "No Messages Found", Toast.LENGTH_SHORT).show();
                    }
                    if (success.equals("1")) {

                        String json = object.getString("chat");
                        JSONArray jsonArray = new JSONArray(json);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            MessagesPojo messagesPojo = new MessagesPojo();

                            messagesPojo.setChat_reciever(jsonObject.getString("first_name") + " " + jsonObject.getString("last_name"));
                            messagesPojo.setChat_request_id(jsonObject.getString("chat_of_request_id"));
                            messagesPojo.setChat_sender_id(jsonObject.getString("chat_sender_id"));
                            messagesPojo.setDate(jsonObject.getString("chat_date_time"));
                            messagesPojo.setMessage(jsonObject.getString("chat_message"));
                            messagesPojo.setRecieverID(jsonObject.getString("chat_receiver_id"));
                            messagesPojo.setChatid(jsonObject.getString("chat_id"));

                            messagesPojos.add(messagesPojo);

                            messageAddapter.notifyDataSetChanged();

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("userId", "12");
                params.put("chat_of_request_id", "6");

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    public void sendMessage()
    {
        final ProgressDialog progressDialog = new ProgressDialog(Messages.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.SEND_MESSAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog.dismiss();

                getMessages();

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
                params.put("from", "23");
                params.put("chat_of_request_id", "6");
                params.put("to", "12");
                params.put("message", messageEntered);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }

}



