package atirek.pothiwala.utility.components;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication application;

    public static MyApplication shared() {
        if (application == null) {
            application = new MyApplication();
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
