package ar.com.edu.itba.hci_app.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import ar.com.edu.itba.hci_app.R;

public class PreferenceManager {
    private SharePreferenceStringLiveData sharedPreferenceLiveData;

    public SharePreferenceStringLiveData getSharedPrefs(){
        return sharedPreferenceLiveData;
    }

    public void setSharedPreferences(String key, String value, Context context) {

        SharedPreferences userDetails = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();
        editor.putString(key, value);
        editor.apply();
        sharedPreferenceLiveData = new SharePreferenceStringLiveData(userDetails,key,value);
    }
}