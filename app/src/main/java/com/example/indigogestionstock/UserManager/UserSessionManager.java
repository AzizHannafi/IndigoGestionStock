package com.example.indigogestionstock.UserManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.indigogestionstock.LoginActivity;

import java.util.HashMap;

public class UserSessionManager {
    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE=0;

    private static final String PREFER_NAME = "LoginPref";

    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_USERNAME = "username";

    public static final String KEY_ID = "id";

    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String id, String nom){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing id in pref
        editor.putString(KEY_ID, id);

        // Storing name in pref
        editor.putString(KEY_USERNAME, nom);

        // commit changes
        editor.commit();
    }

    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){


            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user id
        user.put(KEY_ID, pref.getString(KEY_ID, null));

        // user email id
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        // return user
        return user;
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
