package com.pk.shareabook.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

import com.pk.shareabook.GeneralMethods;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadBook extends AppCompatActivity {

    ImageView ivBookCover;
    EditText etBookTitle;
    Button uploadBookLogo;
    Toolbar toolbar;
    String b64;
    EditText etAuthor;
    public static final int RESULT_LOAD_IMAGE = 0;
    DrawerLayout drawerLayout;
    String bookTitle,bookCover,bookAuthor,formattedDate;

    GeneralMethods gm;
    NavigationView navigationView;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book);


        gm = new GeneralMethods();


//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//       String imagePath = preferences.getString("bookCover","");
//       String author= preferences.getString("bookAuthor","");
//        String title = preferences.getString("bookTitle","");

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

                       break;
                   case (R.id.nav_dashboard):
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




        }



    }



    public void postImage(){



    }


}
