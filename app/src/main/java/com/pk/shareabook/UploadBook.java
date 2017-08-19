package com.pk.shareabook;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.pk.shareabook.Network.END_POINTS;
import com.squareup.picasso.Picasso;

public class UploadBook extends AppCompatActivity {

    ImageView ivBookCover;
    EditText etBookTitle;
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
                return false;
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
