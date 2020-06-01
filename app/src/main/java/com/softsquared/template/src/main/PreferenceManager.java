package com.softsquared.template.src.main;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softsquared.template.src.BaseActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferenceManager {
    private static final boolean DEFAULT_VALUE_BOOLEAN = true;

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("event_pref", Context.MODE_PRIVATE);
    }
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
        return value;
    }
    public static ArrayList<BaseActivity.Place> getBookMarkList(Context context){
        SharedPreferences sharedPrefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("bookMark", "");
        Type type = new TypeToken<ArrayList<BaseActivity.Place>>() {
        }.getType();
        ArrayList<BaseActivity.Place> arrayList = gson.fromJson(json, type);
        if(arrayList == null) arrayList = new ArrayList<>();
        return arrayList;
    }

    public static void saveBookMarkList(Context context, ArrayList<BaseActivity.Place> bookMarkList){
        SharedPreferences pref = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookMarkList);
        editor.putString("bookMark", json);
        editor.commit();
    }
}
