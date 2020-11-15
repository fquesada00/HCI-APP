package ar.com.edu.itba.hci_app.sharedPreferences;

import android.content.SharedPreferences;

public class SharePreferenceStringLiveData extends SharedPreferenceLiveData<String> {


    public SharePreferenceStringLiveData(SharedPreferences prefs, String key, String defValue) {
        super(prefs, key, defValue);
    }

    @Override
    String getValueFromPreferences(String key, String defValue) {
        return sharedPrefs.getString(key, defValue);
    }

}
