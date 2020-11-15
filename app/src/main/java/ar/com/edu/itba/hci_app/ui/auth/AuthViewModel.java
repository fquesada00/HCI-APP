package ar.com.edu.itba.hci_app.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.api.model.Credentials;
import ar.com.edu.itba.hci_app.network.api.model.Token;
import ar.com.edu.itba.hci_app.repository.UserRepository;

public class AuthViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<LiveData<Resource<Token>>> _loginResponse  = new MutableLiveData<>();;


    public LiveData<Resource<Token>> getLoginResponse() {
        if(_loginResponse.getValue() == null){
            login("","");
        }
        return _loginResponse.getValue();
    }

    public AuthViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Resource<Token>> login(String username, String password){
        Credentials credentials = new Credentials(username,password);
        LiveData<Resource<Token>> tok =  userRepository.login(credentials);
        Log.d("RESPONSE", "login: "+ Objects.requireNonNull(tok.getValue()));
        _loginResponse.setValue(tok);
        return getLoginResponse();
    }
}
