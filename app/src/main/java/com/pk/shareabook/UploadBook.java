package com.pk.shareabook;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.pk.shareabook.Network.END_POINTS;
import com.squareup.picasso.Picasso;

public class UploadBook extends AppCompatActivity {

    ImageView ivBookCover;
    EditText etBookTitle;
    EditText etAuthor;

    String bookTitle,bookCover,bookAuthor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//       String imagePath = preferences.getString("bookCover","");
//       String author= preferences.getString("bookAuthor","");
//        String title = preferences.getString("bookTitle","");


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
