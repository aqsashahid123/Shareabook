package com.pk.shareabook;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by AQSA SHaaPARR on 8/19/2017.
 */

public class GeneralMethods {

    public void openActivity(Context context, Class<?> calledActivity){

        Intent intent = new Intent(context.getApplicationContext(),calledActivity);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);



    }

    public void showToast(Context context, String message){

        Toast.makeText(context.getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }

}
