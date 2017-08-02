package com.pk.shareabook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ProfileInfo extends AppCompatActivity {


    Spinner spinnerRegion,spinnerCity;
    ArrayList<String> spinnerDataCity, spinnerDataCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        spinnerDataCity = new ArrayList<>();
        spinnerDataCountry = new ArrayList<>();

        spinnerDataCountry.add("NewYork");
        spinnerDataCountry.add("California");
    //    spinnerDataCountry.add("");

        spinnerDataCity.add("Mexico");
        spinnerDataCity.add("Chicago");
        spinnerDataCity.add("Barcelona");
        spinnerDataCity.add("Manchester");



        spinnerRegion = (Spinner) findViewById(R.id.region);
        spinnerCity = (Spinner) findViewById(R.id.city);

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(ProfileInfo.this, R.layout.bg_spinner_item,spinnerDataCountry);

        spinnerRegion.setAdapter(regionAdapter);

    //    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ProfileInfo.this, android.R.layout.simple_spinner_item,spinnerDataCity);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ProfileInfo.this, R.layout.bg_spinner_item,spinnerDataCity);


        spinnerCity.setAdapter(cityAdapter);


    }
}
