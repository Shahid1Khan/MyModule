package com.mymodule.utils;

import android.app.Activity;
import android.content.Intent;

import com.mymodule.activities.MainActivity;
import com.mymodule.activities.SplashActivity;

/**
 * Created by Hi on 04-01-2017.
 */

public class SplashTask {

    static Activity activity;

    public SplashTask(Activity activity) {
        this.activity = activity;

    }

    public static Thread movetoNext(final int time,final Class cls) {
        Thread thread = null;
        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                synchronized (this) {
                    try {
                        wait(time);
                        activity.startActivity(new Intent(activity, cls));
                        activity.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        return thread;
    }
}
