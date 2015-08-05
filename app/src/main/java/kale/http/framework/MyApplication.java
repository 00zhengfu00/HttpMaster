package kale.http.framework;

import com.google.gson.Gson;

import android.app.Application;

/**
 * @author Jack Tony
 * @date 2015/8/5
 */
public class MyApplication extends Application{
    
    private static Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        mGson = new Gson();
    }

    public static Gson getGson() {
        return mGson;
    }
}
