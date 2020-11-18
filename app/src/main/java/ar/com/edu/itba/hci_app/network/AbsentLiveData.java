package ar.com.edu.itba.hci_app.network;

import androidx.lifecycle.LiveData;

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }
    public static <T> LiveData<T> create() {

        return new AbsentLiveData();
    }
}