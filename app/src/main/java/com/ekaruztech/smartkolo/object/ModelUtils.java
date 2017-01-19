package com.ekaruztech.smartkolo.object;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ekaruztech.smartkolo.ui.activities.signups.LoginActivity;
import com.ekaruztech.smartkolo.util.LauncherUtil;
import com.ekaruztech.smartkolo.util.Logger;
import com.ekaruztech.smartkolo.util.MyUtils;
import com.google.gson.Gson;

/**
 * Created by Emmanuel on 11/16/2016.
 */

public class ModelUtils {

    public static boolean saveObject(Object object, Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        Logger.write("saving --> "+json);
        Logger.write("data_string --> "+object.getClass().getSimpleName());
        prefsEditor.putString(object.getClass().getSimpleName(), json);
        prefsEditor.apply();
        return true;
    }

    public static Object getObject(Class tClass, Context context) {
        Gson gson = new Gson();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = mPrefs.getString(tClass.getSimpleName(), "");
        return gson.fromJson(json, User.class);
    }

    public static void logOut(Context context) {
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(User.TAG, null);
        prefsEditor.apply();
        MyUtils.startActivity(context, LoginActivity.class);
    }

    public static void logIn(User user, Context context, Class a_class) {
        boolean saved = saveObject(user, context);
        if (saved) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.apply();
            LauncherUtil.LaunchClass(context, a_class, null, true);
        }
    }
}
