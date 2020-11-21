package ar.com.edu.itba.hci_app;

import android.app.Application;

import androidx.room.Room;

import ar.com.edu.itba.hci_app.db.MyDatabase;
import ar.com.edu.itba.hci_app.network.api.ApiClient;
import ar.com.edu.itba.hci_app.network.api.ApiRoutinesService;
import ar.com.edu.itba.hci_app.network.api.ApiUserService;
import ar.com.edu.itba.hci_app.repository.AppExecutors;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.utils.Constants;

public class MyApplication extends Application {
    AppExecutors appExecutors;
    AppPreferences preferences;
    UserRepository userRepository;
    RoutineRepository routineRepository;

    public AppPreferences getPreferences() {
        return preferences;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        appExecutors = new AppExecutors();

        ApiUserService userService = ApiClient.create(this, ApiUserService.class);
        ApiRoutinesService sportService = ApiClient.create(this, ApiRoutinesService.class);

        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, Constants.DATABASE_NAME).build();

        userRepository = new UserRepository(appExecutors, userService, database);

        routineRepository = new RoutineRepository(appExecutors, sportService, database);
    }

}
