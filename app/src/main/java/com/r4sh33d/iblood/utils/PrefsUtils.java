package com.r4sh33d.iblood.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;


public class PrefsUtils {

    public static final String GLOBAL_SHARED_PREFS = "global_shared_prefs";

    private Context context;

    public  PrefsUtils(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GLOBAL_SHARED_PREFS,
                Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        getSharedPreferences().edit()
                .putString(key, value)
                .apply();
    }

    public void putInt(String key, int value) {
        getSharedPreferences().edit()
                .putInt(key, value)
                .apply();
    }

    public void putBoolean(String key, boolean value) {
        getSharedPreferences().edit()
                .putBoolean(key, value)
                .apply();
    }

    public void putFloat(String key, float value) {
        getSharedPreferences().edit()
                .putFloat(key, value)
                .apply();
    }

    public void putStringSet(String key, Set<String> values) {
        getSharedPreferences().edit()
                .putStringSet(key, values)
                .apply();
    }

    public void putLong(String key, Long value) {
        getSharedPreferences().edit()
                .putLong(key, value)
                .apply();
    }

    public Boolean doesContain(String Key) {
        return getSharedPreferences().contains(Key);
    }

    public String getString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return getSharedPreferences().getStringSet(key, defaultValue);
    }

    public void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    public void remove(String... keys) {
        for (String key: keys) {
            remove(key);
        }
    }

    public <T> void putList(String key , ArrayList<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        putString(key, json);
    }

    public <T> ArrayList<T> getList(String key , Type type) {
        Gson gson = new Gson();
        return gson.fromJson(getString(key , "") , type);
    }

    public <T> void putObject(String key, T object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        putString(key, json);
    }

    public <T> T getPrefAsObject(String key, Class<T> clazz) {
        String json = getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
}