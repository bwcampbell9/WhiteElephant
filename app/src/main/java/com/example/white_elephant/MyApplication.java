package com.example.white_elephant;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.setContext(getApplicationContext());
    }

    public static void setContext(Context c) { MyApplication.context = c; }
    public static Context getAppContext() {
        return MyApplication.context;
    }
}
