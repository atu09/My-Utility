package atirek.pothiwala.utility.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;


/**
 * Created by Atirek Pothiwala on 10/17/2018.
 */

public class Session {

    private final SharedPreferences sharedPreferences;

    public Session(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public JSONObject getJson(String key) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(sharedPreferences.getString(key, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    public String get(String key) {
        return sharedPreferences.getString(key, "")
    }
    
    public void set(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    
    public boolean exists(String key) {
        return sharedPreferences.contains(key);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
