package ar.com.edu.itba.hci_app.repository;


import android.content.Context;

import ar.com.edu.itba.hci_app.db.MyDatabase;
import ar.com.edu.itba.hci_app.network.api.ApiUserService;

public abstract class BaseRepository {

    protected AppExecutors executors;
    protected MyDatabase database;


    public BaseRepository(AppExecutors executors,MyDatabase database){
        this.database = database;
        this.executors=executors;
    }





}
