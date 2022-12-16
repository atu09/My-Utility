package atirek.pothiwala.utility.helper;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class Session {

    private final SharedPreferences sharedPreferences;

    public Session(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public String get(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void set(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void set(JSONObject json) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                editor.putString(key, json.getString(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    public JSONObject get() {
        JSONObject jsonObject = new JSONObject();
        Map<String, ?> keys = sharedPreferences.getAll();
        try {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public boolean exists(String key) {
        return sharedPreferences.contains(key);
    }

    public boolean exists() {
        return !sharedPreferences.getAll().isEmpty();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}

