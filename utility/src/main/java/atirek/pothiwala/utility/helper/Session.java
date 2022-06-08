package atirek.pothiwala.utility.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;


/**
 * Created by Atirek Pothiwala on 10/17/2018.
 */

public class Session {

    private final SharedPreferences sharedPreferences;
    private String sessionKey;

    private Session(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public void set(String json) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sessionKey, json);
        editor.apply();
    }

    public String get(String key) {
        try {
            JSONObject jsonObject = new JSONObject(sharedPreferences.getString(sessionKey, ""));
            return jsonObject.optString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean exists() {
        return sharedPreferences.contains(sessionKey);
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
