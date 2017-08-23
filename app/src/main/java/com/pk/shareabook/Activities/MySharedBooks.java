package com.pk.shareabook.Activities;

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
import android.widget.Button;

import com.pk.shareabook.GeneralMethods;
import com.pk.shareabook.R;

public class MySharedBooks extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    GeneralMethods gm;
    DrawerLayout drawerLayout;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shared_books);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       id = preferences.getString("id","");

        /////////////////////////////////////
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        gm = new GeneralMethods();

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
                        gm.openActivity(getApplicationContext(), Dashboard.class);
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
                        gm.openActivity(getApplicationContext(),SharingRequest.class);
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





    }
}
