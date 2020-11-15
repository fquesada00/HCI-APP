package ar.com.edu.itba.hci_app.repository;


import android.content.Context;

public abstract class BaseRepository {
    private static UserRepository userRepository;

    static public UserRepository getUserRepository(Context context) {
        if(userRepository == null)
            userRepository = new UserRepository(context);
        return userRepository;
    }

}
