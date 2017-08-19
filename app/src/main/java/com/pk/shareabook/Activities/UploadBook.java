package com.pk.shareabook.Activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pk.shareabook.GeneralMethods;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.squareup.picasso.Picasso;

public class UploadBook extends AppCompatActivity {

    ImageView ivBookCover;
    EditText etBookTitle;
    Button uploadBookLogo;
    Toolbar toolbar;
    EditText etAuthor;
    DrawerLayout drawerLayout;
    String bookTitle,bookCover,bookAuthor;
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




    }
}
