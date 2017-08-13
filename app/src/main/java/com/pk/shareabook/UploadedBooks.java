package com.pk.shareabook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class UploadedBooks extends AppCompatActivity {

    ListView lvMyUploadedBooks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_books);
        lvMyUploadedBooks = (ListView) findViewById(R.id.lvMyUploadedBooks);



    }
}
