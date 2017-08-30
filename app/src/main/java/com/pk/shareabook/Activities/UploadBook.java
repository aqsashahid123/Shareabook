package com.pk.shareabook.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pk.shareabook.Adapters.LocationsAdapter;
import com.pk.shareabook.GeneralMethods;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadBook extends AppCompatActivity {

    ImageView ivBookCover;
    EditText etBookTitle,etLocations;
    Button uploadBookLogo;
    Toolbar toolbar;
    String b64;
    LocationsAdapter adapter;
    String id;
    EditText etAuthor, etInstitute;
    String imageName;
    public static final int RESULT_LOAD_IMAGE = 0;
    DrawerLayout drawerLayout;
    String bookTitle,bookCover,bookAuthor,formattedDate,locations;

    public static int p = 1;
    ArrayList<String> tmpLocationsArray;
    String[] locationsArray;
    Button btnAddDataInArray,uploadBook;
    TextView tvOne,tv2,tv3,tv4,tv5;
    GeneralMethods gm;
    NavigationView navigationView;
    LocationsAdapter adapt;

    List<String> list;
    RecyclerView rv;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book);

        list = new ArrayList<>();

        gm = new GeneralMethods();
        uploadBook = (Button) findViewById(R.id.uploadBook);
        rv = (RecyclerView) findViewById(R.id.rvLocations);
        btnAddDataInArray = (Button) findViewById(R.id.addDataLocation);
        tmpLocationsArray = new ArrayList<>();
        etLocations = (EditText) findViewById(R.id.etLocations);
        etInstitute = (EditText) findViewById(R.id.etInstitute);


//        tvOne = (TextView) findViewById(R.bookId.tvOne);
//        tv2 = (TextView) findViewById(R.bookId.tvTwo);
//        tv3 = (TextView) findViewById(R.bookId.tvThree);
//        tv4 = (TextView) findViewById(R.bookId.tvFour);
//        tv5 = (TextView) findViewById(R.bookId.tvFive);
//        locationsArray = new String[5];
    //p =0;
        btnAddDataInArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    //addDataInComments();


                if(p<=5){
                    p = p+1;
                    addDataInComments();
                }
                else {
                    gm.showToast(getApplicationContext(),"You can enter only 5 values");
                }




            }
        });


        uploadBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postImage();
                //uploadBook();
            }
        });


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        id =  preferences.getString("id","");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        uploadBookLogo = (Button) findViewById(R.id.uploadImage);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){

                    case(R.id.openMenu):
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

               switch (item.getItemId()){

                   case (R.id.nav_profile):

                       gm.openActivity(getApplicationContext(), ProfileInfo.class);
                       finish();

                       break;
                   case (R.id.nav_dashboard):
                       gm.openActivity(getApplicationContext(), Dashboard.class);
                    //   finish();
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
                     //  gm.showToast(getApplicationContext(),"REQUESTED BOOKS");
                       gm.openActivity(getApplicationContext(),RequestedBooks.class);
                       finish();
                       break;
                   case (R.id.nav_sharing_requests):
                       //gm.showToast(getApplicationContext(),"Sharing Request");
                       gm.openActivity(getApplicationContext(),SharingRequest.class);
                       finish();
                       break;
                   case (R.id.nav_shareed_books):
                       //gm.showToast(getApplicationContext(),"Shared BOOKS");
                       gm.openActivity(getApplicationContext(), MySharedBooks.class);
                       finish();
                       break;
                   case (R.id.nav_recievedBooks):
                       //gm.showToast(getApplicationContext(),"Recieved Books");
                       gm.openActivity(getApplicationContext(), RecievedBooks.class);
                       finish();
                       break;
                   case (R.id.nav_logOut):
                       MainActivity.Flag = false;
                       SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                       preferences.edit().clear().apply();

                       gm.openActivity(getApplicationContext(), MainActivity.class);
                        finish();
                       break;

                   case (R.id.nav_search):
                       gm.openActivity(getApplicationContext(), MainScreen.class);
                       finish();
                       break;

               }




                return true;
            }
        });

        ivBookCover = (ImageView)findViewById(R.id.bookFront);
        etAuthor = (EditText) findViewById(R.id.etBookAuthor) ;
        etBookTitle = (EditText) findViewById(R.id.etBookTitle);

        bookTitle = getIntent().getStringExtra("bookTitle");
        bookAuthor = getIntent().getStringExtra("bookAuthor");
        bookCover = getIntent().getStringExtra("bookCover");

//
        etBookTitle.setText(bookTitle);
        etAuthor.setText(bookAuthor);
        Picasso.with(getApplicationContext()).load(END_POINTS.GET_BOOK_LOGO + bookCover).into(ivBookCover);

        uploadBookLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(UploadBook.this);
                View view = inflater.inflate(R.layout.custom_alert_gallery,null);

             final   AlertDialog dialog =new AlertDialog.Builder(UploadBook.this).create();

                LinearLayout gallery = (LinearLayout) view.findViewById(R.id.gallery);
                LinearLayout camera = (LinearLayout) view.findViewById(R.id.linearLayoutCamera);

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                        dialog.dismiss();
                    }
                });

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                        dialog.dismiss();


                    }
                });


                dialog.setView(view);
                dialog.show();

            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();
            //  File imageFile = new File(selectedImage.toString());

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            //  rotateImage(bitmap,selectedImage.toString());


          ivBookCover.setImageBitmap(bitmap);


            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate = df.format(c.getTime());

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bao);
            byte[] ba = bao.toByteArray();
            b64 = Base64.encodeToString(ba,Base64.NO_WRAP);
//            if (!b64.equals("")){
//                postImage();
//            }

        }

        /////////////GALLERY//////////////////
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            getPathFromURI(selectedImage);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate = df.format(c.getTime());


            //  ImageView imageView = (ImageView) findViewById(R.bookId.imgView);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ivBookCover.setImageBitmap(bitmap);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bao);
            byte[] ba = bao.toByteArray();
            b64 = Base64.encodeToString(ba,Base64.NO_WRAP);
//            if (!b64.equals("")){
          //  postImage();
//            }
        }



    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void postImage(){


        final ProgressDialog pd = new ProgressDialog(UploadBook.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.UPLOAD_BOOK_LOGO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();
                if (response!= null){

                    imageName = response;
                    uploadBook();
                }

                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UploadBook.this,"Network Error",Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                // SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                //   preferences.getString("bookId","");

                //      params.put("bookId", result.get("bookId"));

                params.put("image", b64);
                params.put("name",formattedDate);


//                params.put("password", password);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(UploadBook.this);
        requestQueue.add(request);



    }


    public void uploadBook(){


        final ProgressDialog pd = new ProgressDialog(UploadBook.this);
        pd.setMessage("loading");
        pd.show();


        StringRequest request = new StringRequest(Request.Method.POST, END_POINTS.UPLOAD_BOOK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pd.dismiss();
//                if (response!= null){
//
//                    imageName = response;
//
//                }

                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UploadBook.this,"Network Error",Toast.LENGTH_SHORT).show();
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
                 String cityId=  preferences.getString("city_id","");
                String regionId = preferences.getString("region_id","");
                //      params.put("bookId", result.get("bookId"));

                params.put("userId", id);
                params.put("book_title",etBookTitle.getText().toString());
                params.put("book_author",etAuthor.getText().toString());
                params.put("uploaded_book_logo_name",imageName);
                params.put("book_institute",etInstitute.getText().toString());
                params.put("book_city_id",cityId);
                params.put("book_region_id",regionId);

            //    locationsArray = list.toArray(new String[list.size()]);
//                String k;
//                String r;

               String s = android.text.TextUtils.join(",", list);

//                int i =0;
//                for(String s: locationsArray) {

                  //  k = s+"," + r;
//                    params.put("location["+(i++)+"] ", s);
//                    //r = s;
//                }

                params.put("location", s);
//
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(UploadBook.this);
        requestQueue.add(request);



    }
    public void addDataInComments(){


       // fabm.setVisibility(View.VISIBLE);

//        List<> cardCommentData = new CardCommentData();
//        cardCommentData.setCardCommentid("Id Ov Person ");
//        cardCommentData.setCardName(CardHeading);
     //  List<String> cardCommentDataetComment.getText().toString());
        list.add(etLocations.getText().toString());


        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new LocationsAdapter(list, getApplicationContext());
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}
