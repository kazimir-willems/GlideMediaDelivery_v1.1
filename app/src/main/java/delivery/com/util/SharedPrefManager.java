package delivery.com.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "GlideMediaPreference";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_CLOCK_STATUS = "clock_status";
    private static final String TAG_LUNCH_STATUS = "lunch_status";
    private static final String TAG_LOGGED_IN = "logged_in";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean savePassword(String password){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_PASSWORD, password);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getPassword(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_PASSWORD, null);
    }

    public boolean saveClockStatus(boolean status){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TAG_CLOCK_STATUS, status);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public boolean getClockStatus(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(TAG_CLOCK_STATUS, false);
    }

    public boolean saveLunchStatus(boolean status){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TAG_LUNCH_STATUS, status);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public boolean getLunchStatus(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(TAG_LUNCH_STATUS, false);
    }

    public boolean saveLoggedIn(boolean flag){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TAG_LOGGED_IN, flag);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public boolean getLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean(TAG_LOGGED_IN, false);
    }
}