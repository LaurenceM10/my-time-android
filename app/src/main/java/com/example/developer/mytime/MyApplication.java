package com.example.developer.mytime;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by developer on 13/12/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //To init fresco
        Fresco.initialize(this);
    }
}
