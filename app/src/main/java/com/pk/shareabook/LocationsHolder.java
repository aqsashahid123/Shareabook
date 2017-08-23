package com.pk.shareabook;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.PublicKey;

/**
 * Created by AQSA SHaaPARR on 8/21/2017.
 */

public class LocationsHolder extends RecyclerView.ViewHolder {

    public TextView tvLocation;
    public Button btnSubLoc;

    public LocationsHolder(View itemView) {
        super(itemView);

        tvLocation = (TextView) itemView.findViewById(R.id.tvLoc);
        btnSubLoc = (Button) itemView.findViewById(R.id.btnSubLocation);



    }
}
