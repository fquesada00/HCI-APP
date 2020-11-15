package ar.com.edu.itba.hci_app;

import android.content.Context;

import ar.com.edu.itba.hci_app.sharedPreferences.PreferenceManager;
import ar.com.edu.itba.hci_app.sharedPreferences.SharedPreferenceLiveData;

public class AppPreferences {
    private final String AUTH_TOKEN = "auth_token";

    private PreferenceManager preferenceManager;
    private Context context;
    public AppPreferences(Context context) {
        preferenceManager = new PreferenceManager();
        this.context = context;
    }

    public void setAuthToken(String token) {
        preferenceManager.setSharedPreferences(AUTH_TOKEN,token,context);
    }

    public SharedPreferenceLiveData<String> getAuthToken() {
        setAuthToken(null);
        return preferenceManager.getSharedPrefs().getStringLiveData(AUTH_TOKEN, null);
    }

    public void clearToken(){
        preferenceManager.setSharedPreferences(AUTH_TOKEN,null,context);
    }
}