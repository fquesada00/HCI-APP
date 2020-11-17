package ar.com.edu.itba.hci_app.network.api;

import androidx.lifecycle.LiveData;

import ar.com.edu.itba.hci_app.network.api.model.Credentials;
import ar.com.edu.itba.hci_app.network.api.model.Token;
import ar.com.edu.itba.hci_app.network.api.model.User;
import ar.com.edu.itba.hci_app.network.api.model.UserRegistration;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("user/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("user/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    @POST("user")
    LiveData<ApiResponse<User>> register(@Body UserRegistration user);
}
