package ar.com.edu.itba.hci_app.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ar.com.edu.itba.hci_app.db.MyDatabase;
import ar.com.edu.itba.hci_app.db.entity.UserEntity;
import ar.com.edu.itba.hci_app.domain.User;
import ar.com.edu.itba.hci_app.network.AbsentLiveData;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.api.ApiResponse;
import ar.com.edu.itba.hci_app.network.api.ApiUserService;
import ar.com.edu.itba.hci_app.network.api.model.CredentialsModel;
import ar.com.edu.itba.hci_app.network.api.model.TokenModel;
import ar.com.edu.itba.hci_app.network.api.model.UserModel;
import ar.com.edu.itba.hci_app.network.api.model.UserRegistrationModel;
import ar.com.edu.itba.hci_app.network.api.model.VerificationCodeModel;

public class UserRepository extends BaseRepository {
    private final ApiUserService apiService;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    public UserRepository(AppExecutors executors, ApiUserService service, MyDatabase myDatabase) {
        super(executors,myDatabase);
        this.apiService = service;
    }

    private User mapUserEntityToDomain(UserEntity userEntity) {
        return new User(userEntity.id, userEntity.username, userEntity.fullName, userEntity.gender, userEntity.birthdate, userEntity.email,
                userEntity.phone, userEntity.avatarUrl, userEntity.dateCreated, userEntity.dateLastActive, userEntity.deleted,
                userEntity.verified);
    }

    private UserEntity mapUserModelToEntity(UserModel userModel) {
        return new UserEntity(userModel.getId(), userModel.getUsername(), userModel.getFullName(), userModel.getGender(),
                userModel.getBirthdate(), userModel.getEmail(), userModel.getPhone(), userModel.getAvatarUrl(), userModel.getDateCreated(),
                userModel.getDateLastActive(), userModel.getVerified(), userModel.getDeleted());
    }

    private User mapUserModelToDomain(UserModel userModel) {
        return new User(userModel.getId(), userModel.getUsername(), userModel.getFullName(), userModel.getGender(), userModel.getBirthdate(),
                userModel.getEmail(), userModel.getPhone(), userModel.getAvatarUrl(), userModel.getDateCreated(), userModel.getDateLastActive(),
                userModel.getDeleted(), userModel.getVerified());
    }

    public LiveData<Resource<String>> login(CredentialsModel credentialsModel) {
        return new NetworkBoundResource<String, Void, TokenModel>(executors,null, null, model -> model.getToken()) {

            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable TokenModel model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TokenModel>> createCall() {
                return apiService.login(credentialsModel);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void,Void, Void>(executors,aVoid -> null, aVoid -> null, aVoid -> null)
        {
            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> getCurrentUser() {
        return new NetworkBoundResource<User, UserEntity, UserModel>(executors,
                this::mapUserEntityToDomain,
                this::mapUserModelToEntity,
                this::mapUserModelToDomain)
        {
            int entityId = -1;

            @Override
            protected void saveCallResult(@NonNull UserEntity entity) {
                entityId =entity.id;
                database.userDao().delete(entity);
                database.userDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity entity) {
                return entity == null || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY);
            }

            @Override
            protected boolean shouldPersist(@Nullable UserModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                if(entityId == -1)
                    return null;
                return database.userDao().getUserData(entityId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserModel>> createCall() {
                return apiService.getCurrentUser();
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> register(String username, String password, String fullname, String gender, Date birthdate,
                                                  String email){
        return new NetworkBoundResource<User,UserEntity, UserModel>(executors,
                this::mapUserEntityToDomain,
                this::mapUserModelToEntity,
                this::mapUserModelToDomain){

            @Override
            protected void saveCallResult(@NonNull UserEntity entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable UserEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable UserModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserEntity> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserModel>> createCall() {
                return apiService.register(new UserRegistrationModel(password,birthdate,gender,fullname,email,username));
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> verifyEmail(String email, String code) {
        return new NetworkBoundResource<Void,Void, Void>(executors,aVoid -> null, aVoid -> null, aVoid -> null)
        {
            @Override
            protected void saveCallResult(@NonNull Void entity) {
            }

            @Override
            protected boolean shouldFetch(@Nullable Void entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<Void> loadFromDb() {
                return AbsentLiveData.create();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.verifyEmail(new VerificationCodeModel(email,code));
            }
        }.asLiveData();
    }
}