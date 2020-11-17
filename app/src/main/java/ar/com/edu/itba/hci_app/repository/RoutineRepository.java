package ar.com.edu.itba.hci_app.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.network.NetworkBoundResource;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.api.ApiClient;
import ar.com.edu.itba.hci_app.network.api.ApiResponse;
import ar.com.edu.itba.hci_app.network.api.ApiRoutinesService;
import ar.com.edu.itba.hci_app.network.api.model.Cycle;
import ar.com.edu.itba.hci_app.network.api.model.Exercise;
import ar.com.edu.itba.hci_app.network.api.model.PagedList;
import ar.com.edu.itba.hci_app.network.api.model.Rating;
import ar.com.edu.itba.hci_app.network.api.model.Routine;
import ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter;

public class RoutineRepository extends BaseRepository{
    private final ApiRoutinesService apiService;
    public RoutineRepository(Context context) {
        apiService = ApiClient.create(context, ApiRoutinesService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getRoutine(@Nullable String difficulty, @Nullable Integer page,
                                                             @Nullable Integer size, @Nullable String orderBy,
                                                             @Nullable String direction, @NotNull RoutinePagedListGetter selector){
        switch (selector){
            case ALL:
                return new NetworkBoundResource<PagedList<Routine>,PagedList<Routine>>(){

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                        return apiService.getRoutines(difficulty,page,size,orderBy,direction);
                    }
                }.asLiveData();
            case CURRENT:
                return new NetworkBoundResource<PagedList<Routine>,PagedList<Routine>>(){

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                        return apiService.getCurrentUserRoutines(difficulty,page,size,orderBy,direction);
                    }
                }.asLiveData();
            case FAVOURITES:
                return new NetworkBoundResource<PagedList<Routine>,PagedList<Routine>>(){

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                        return apiService.getCurrentUserFavourites(page,size,orderBy,direction);
                    }
                }.asLiveData();
        }
        throw new IllegalArgumentException("Bad api call in repo");
    }

    public LiveData<Resource<PagedList<Rating>>> getCurrentUserRatings(@Nullable Integer page,
                                                                       @Nullable Integer size, @Nullable String orderBy,
                                                                       @Nullable String direction){
        return new NetworkBoundResource<PagedList<Rating>,PagedList<Rating>>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Rating>>> createCall() {
                return apiService.getCurrentUserRoutinesRatings(page,size,orderBy,direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Routine>>> getRoutinesFromUser(@NonNull Integer userID,@Nullable String difficulty, @Nullable Integer page,
                                                                      @Nullable Integer size, @Nullable String orderBy,
                                                                      @Nullable String direction){
        return new NetworkBoundResource<PagedList<Routine>,PagedList<Routine>>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getUserRoutines(userID,difficulty,page,size,orderBy,direction);
            }
        }.asLiveData();
    }
    public LiveData<Resource<Routine>> getRoutineByID(@NonNull Integer routineID){
        return new NetworkBoundResource<Routine,Routine>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return apiService.getRoutineByID(routineID);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Rating>>> getRoutineRatings(@NonNull Integer routineID,@Nullable String difficulty, @Nullable Integer page,
                                                       @Nullable Integer size, @Nullable String orderBy,
                                                       @Nullable String direction){
        return new NetworkBoundResource<PagedList<Rating>,PagedList<Rating>>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Rating>>> createCall() {
                return apiService.getRoutineRating(routineID,page,size,orderBy,direction);
            }
        }.asLiveData();

    }

    public LiveData<Resource<PagedList<Cycle>>> getRoutineCycles(@NonNull Integer routineID,@Nullable String difficulty, @Nullable Integer page,
                                                                 @Nullable Integer size, @Nullable String orderBy,
                                                                 @Nullable String direction){
        return new NetworkBoundResource<PagedList<Cycle>,PagedList<Cycle>>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Cycle>>> createCall() {
                return apiService.getRoutineCycles(routineID,page,size,orderBy,direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Cycle>> getCycleByID(@NonNull Integer routineID,@NonNull Integer cycleID){
        return new NetworkBoundResource<Cycle,Cycle>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<Cycle>> createCall() {
                return apiService.getRoutineCycleByID(routineID,cycleID);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Exercise>>> getCycleExercises(@NonNull Integer routineID,@NonNull Integer cycleID, @Nullable Integer page,
                                                                     @Nullable Integer size, @Nullable String orderBy,
                                                                     @Nullable String direction){
        return new NetworkBoundResource<PagedList<Exercise>,PagedList<Exercise>>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Exercise>>> createCall() {
                return apiService.getCycleExercises(routineID,cycleID,page,size,orderBy,direction);
            }
        }.asLiveData();
    }
    public LiveData<Resource<Exercise>> getExerciseByID(@NonNull Integer routineID,@NonNull Integer cycleID,@NonNull Integer exerciseID){
        return new NetworkBoundResource<Exercise,Exercise>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<Exercise>> createCall() {
                return apiService.getExerciseByID(routineID,cycleID,exerciseID);
            }
        }.asLiveData();
    }
    public LiveData<Resource<Void>> addToFavourites(@NonNull Integer routineID){
        return new NetworkBoundResource<Void,Void>(){

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addToFavourites(routineID);
            }
        }.asLiveData();
    }
    public LiveData<Resource<Rating>> addRating(@NonNull Integer routineID,Rating rating){
        return new NetworkBoundResource<Rating,Rating>(){
            @NonNull
            @Override
            protected LiveData<ApiResponse<Rating>> createCall() {
                return apiService.addRatingToRoutine(routineID,rating);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> removeFromFavourites(@NonNull Integer routineID){
        return new NetworkBoundResource<Void,Void>(){
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.removeFromFavourites(routineID);
            }
        }.asLiveData();
    }


}
