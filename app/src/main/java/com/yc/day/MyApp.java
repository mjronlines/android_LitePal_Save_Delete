package com.yc.day;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePal;

/**
 * Created by King on 2018/4/19.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {

        super.onCreate();
        LitePal.initialize(this);
        SQLiteDatabase db = LitePal.getDatabase();
    }
}
