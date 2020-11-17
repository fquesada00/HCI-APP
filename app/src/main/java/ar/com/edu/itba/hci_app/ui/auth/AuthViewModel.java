package ar.com.edu.itba.hci_app.ui.auth;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.Objects;

import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.api.model.Credentials;
import ar.com.edu.itba.hci_app.network.api.model.Token;
import ar.com.edu.itba.hci_app.network.api.model.User;
import ar.com.edu.itba.hci_app.repository.UserRepository;

public class AuthViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private MutableLiveData<LiveData<Resource<Token>>> _loginResponse  = new MutableLiveData<>();;


    public LiveData<Resource<Token>> getLoginResponse() {
        if(_loginResponse.getValue() == null){
            login("","");
        }
        return _loginResponse.getValue();
    }

    public AuthViewModel(Application application,UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
    }

    public LiveData<Resource<Token>> login(String username, String password){
        Credentials credentials = new Credentials(username,password);
        LiveData<Resource<Token>> tok =  userRepository.login(credentials);
        Log.d("RESPONSE", "login: "+ Objects.requireNonNull(tok.getValue()));
        _loginResponse.setValue(tok);
        return getLoginResponse();
    }

    public LiveData<Resource<User>> register(String username,String password, String fullname, String gender, Date birthdate,
                                             String email){
        //TODO check input
        return userRepository.register(username,password,fullname,gender,birthdate,email);

    }
}
