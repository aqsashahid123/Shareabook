package com.pk.shareabook;

import android.content.Context;
import android.content.Intent;

/**
 * Created by AQSA SHaaPARR on 8/19/2017.
 */

public class GeneralMethods {

    public void openActivity(Context context, Class<?> calledActivity){

        Intent intent = new Intent(context.getApplicationContext(),calledActivity);
        context.startActivity(intent);


    }

}
