package ar.com.edu.itba.hci_app.repository;


import android.content.Context;

import ar.com.edu.itba.hci_app.network.api.ApiUserService;

public abstract class BaseRepository {
    protected static UserRepository userRepository;
    protected static RoutineRepository routineRepository;


    static public UserRepository getUserRepository(Context context) {
        if(userRepository == null)
            userRepository = new UserRepository(context);
        return userRepository;
    }

    static public RoutineRepository getRoutineRepository(Context context){
        if(routineRepository == null)
            routineRepository = new RoutineRepository(context);
        return routineRepository;
    }




}
