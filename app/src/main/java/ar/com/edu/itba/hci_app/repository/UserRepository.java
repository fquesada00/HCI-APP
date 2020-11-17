package ar.com.edu.itba.hci_app.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Date;

import ar.com.edu.itba.hci_app.network.NetworkBoundResource;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.api.ApiClient;
import ar.com.edu.itba.hci_app.network.api.ApiResponse;
import ar.com.edu.itba.hci_app.network.api.ApiUserService;
import ar.com.edu.itba.hci_app.network.api.model.Credentials;
import ar.com.edu.itba.hci_app.network.api.model.Token;
import ar.com.edu.itba.hci_app.network.api.model.User;
import ar.com.edu.itba.hci_app.network.api.model.UserRegistration;

public class UserRepository extends BaseRepository {
    private final ApiUserService apiService;
    public UserRepository(Context context) {
        this.apiService = ApiClient.create(context, ApiUserService.class);
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                Log.d("send", "createCall: sending to api" + credentials);
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return new NetworkBoundResource<User, User>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> register(String username,String password, String fullname, String gender, Date birthdate,
                                             String email){
        return new NetworkBoundResource<User,User>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return apiService.register(new UserRegistration(password,birthdate,gender,fullname,email,username));
            }
        }.asLiveData();
    }
}