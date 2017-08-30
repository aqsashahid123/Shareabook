package com.pk.shareabook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pk.shareabook.Activities.BookDetail;
import com.pk.shareabook.Network.END_POINTS;
import com.pk.shareabook.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by AQSA SHaaPARR on 8/16/2017.
 */

public class BooksCardItemsAdapter extends RecyclerView.Adapter<BooksCardItemsAdapter.MyViewHolder> {

      //  List<CardItemPojo> cardDataList;

        HashMap<String,String> SingleMap;
        List<HashMap<String,String>> mapList;

        Context mContext;

        public BooksCardItemsAdapter(List<HashMap<String,String>> mapList,Context context){

           // this.SingleMap = SingleMap;
            this.mapList = mapList;
            this.mContext = context;

        }

//    public BooksCardItemsAdapter(List<CardItemPojo> CardDataList,Context context){
//
//        this.cardDataList = CardDataList;
//        this.mContext = context;
//    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_book,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //CardItemPojo cardItemPojo = cardDataList.get(position);
        SingleMap = new HashMap<>();
        SingleMap = mapList.get(position);
        holder.tvBookName.setText(SingleMap.get("bookName"));
        holder.tvAuthorName.setText(SingleMap.get("authorName"));
       // holder.ivBookCover.setImageBitmap();;
        String imgLogo = SingleMap.get("image");

        Picasso.with(mContext.getApplicationContext()).load(END_POINTS.GET_BOOK_LOGO + imgLogo).into(holder.ivBookCover);


//        holder.tvBookName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext.getApplicationContext(), BookDetail.class);
//                SingleMap = mapList.get(position);
//                intent.putExtra("bookId" , SingleMap.get("book_id"));
//               // SingleMap = new HashMap<>();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//
//            }
//        });
//        holder.tvAuthorName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext.getApplicationContext(), BookDetail.class);
//                SingleMap = mapList.get(position);
//                intent.putExtra("bookId" , SingleMap.get("book_id"));
//                //SingleMap = new HashMap<>();
//
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//            }
//        });
//        holder.ivBookCover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext.getApplicationContext(), BookDetail.class);
//                SingleMap = mapList.get(position);
//                intent.putExtra("bookId" , SingleMap.get("book_id"));
//              //  SingleMap = new HashMap<>();
//
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
//            }
//        });







    }

    @Override
    public int getItemCount() {
      //  return cardDataList.size();
    return mapList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        public TextView tvAuthorName,tvBookName;
        CardView card_view;
        ImageView ivBookCover;
        RelativeLayout laCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvAuthorName = (TextView) itemView.findViewById(R.id.bookAuthor);
            tvBookName = (TextView) itemView.findViewById(R.id.bookName);
            ivBookCover = (ImageView) itemView.findViewById(R.id.imageView);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            laCardView = (RelativeLayout) itemView.findViewById(R.id.laCardView);

        }
    }
}
