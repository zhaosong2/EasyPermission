package com.zs.easypermission;

import android.app.Application;
import android.content.Context;

/**
 * @version v1.0
 * @author: zhaosong
 * @date: 2017-05-04
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        init();
    }

    public static Context getContext(){
        return  context;
    }

    private void init() {

    }
}
