package ar.com.edu.itba.hci_app.ui.auth;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.Objects;

import ar.com.edu.itba.hci_app.domain.User;
import ar.com.edu.itba.hci_app.network.AbsentLiveData;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.api.model.CredentialsModel;
import ar.com.edu.itba.hci_app.network.api.model.TokenModel;
import ar.com.edu.itba.hci_app.network.api.model.UserModel;
import ar.com.edu.itba.hci_app.repository.UserRepository;

public class AuthViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private MutableLiveData<LiveData<Resource<String>>> _loginResponse  = new MediatorLiveData<>();;


    public AuthViewModel(Application application,UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
    }

    public LiveData<Resource<String>> login(String username, String password){
        CredentialsModel credentialsModel = new CredentialsModel(username,password);
        LiveData<Resource<String>> tok =  userRepository.login(credentialsModel);
        _loginResponse.setValue(tok);
        return _loginResponse.getValue();
    }

    public LiveData<Resource<User>> register(String username, String password, String fullname, String gender, Date birthdate,
                                             String email){
        //TODO check input
        return userRepository.register(username,password,fullname,gender,birthdate,email);

    }

    public LiveData<Resource<Void>> verifyEmail(String email,String code){
       return userRepository.verifyEmail(email,code);
    }
}
