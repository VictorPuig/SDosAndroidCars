package com.example.admin.sdosandroidcars.api.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

public class SessionManager {
    static SharedPreferences pref;

    static SharedPreferences.Editor editor;

    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "PrefFile";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "name";

    public static final String KEY_PASSWORD = "password";

    // Constructor
    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static void createLoginSession(String name, String password){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing password in pref
        editor.putString(KEY_PASSWORD, password);

        // commit changes
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user password id
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // return user
        return user;
    }

    public String getuserName () {
        return pref.getString(KEY_NAME, null);
    }

    public static boolean isLoggedIn () {
        return pref.getBoolean(IS_LOGIN,false);
    }

    public void checkLogin() {
        if (!isLoggedIn()) {
            Toast.makeText(context, "Login first!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }
}
