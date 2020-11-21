package ar.com.edu.itba.hci_app.network.api;

import androidx.lifecycle.LiveData;

import ar.com.edu.itba.hci_app.network.api.model.CredentialsModel;
import ar.com.edu.itba.hci_app.network.api.model.TokenModel;
import ar.com.edu.itba.hci_app.network.api.model.UserModel;
import ar.com.edu.itba.hci_app.network.api.model.UserRegistrationModel;
import ar.com.edu.itba.hci_app.network.api.model.VerificationCodeModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("user/login")
    LiveData<ApiResponse<TokenModel>> login(@Body CredentialsModel credentialsModel);

    @POST("user/logout")
    LiveData<ApiResponse<Void>> logout();

    @GET("user/current")
    LiveData<ApiResponse<UserModel>> getCurrentUser();

    @POST("user")
    LiveData<ApiResponse<UserModel>> register(@Body UserRegistrationModel user);

    @POST("user/verify_email")
    LiveData<ApiResponse<Void>> verifyEmail(@Body VerificationCodeModel verificationCodeModel);
}
