package com.digvijaysingh.kiwi.Utils;

import android.app.Application;

import com.digvijaysingh.kiwi.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class KiWiApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Oswald-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
