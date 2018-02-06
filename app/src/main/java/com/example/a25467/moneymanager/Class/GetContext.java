package com.example.a25467.moneymanager.Class;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by 25467 on 2018/2/6.
 */

public class GetContext extends Application {
    private static Context context;


    @Override
    public void onCreate(){
        super.onCreate();
        context =getApplicationContext();
        LitePal.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
}
