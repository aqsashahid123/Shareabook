package com.pk.shareabook.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pk.shareabook.Activities.UploadBook;
import com.pk.shareabook.LocationsHolder;
import com.pk.shareabook.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 8/21/2017.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationsHolder> {

    LayoutInflater inflater;
    String[] arr;
    List<String> cardCommenList= Collections.emptyList();
    Context context;

   public LocationsAdapter(List<String> mList,Context context){

        this.context = context;
        this.cardCommenList = mList;
    }
//public LocationsAdapter( String[] Arr,Context context){
//
//    this.context = context;
//    this.arr = Arr;
//}




    @Override
    public LocationsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listitem_locations,parent,false);
        LocationsHolder holder = new LocationsHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(LocationsHolder holder, final int position) {
      //  holder.tvLocation.setText(arr[position]);


        holder.tvLocation.setText(cardCommenList.get(position));
//
        holder.btnSubLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cardCommenList.size();
    }

    public void removeAt(int position){

        UploadBook.p = UploadBook.p-1;
        cardCommenList.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position,cardCommenList.size());

    }

}

