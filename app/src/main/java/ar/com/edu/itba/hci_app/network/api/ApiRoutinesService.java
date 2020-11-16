package ar.com.edu.itba.hci_app.network.api;

import androidx.lifecycle.LiveData;

import ar.com.edu.itba.hci_app.network.api.model.Cycle;
import ar.com.edu.itba.hci_app.network.api.model.Exercise;
import ar.com.edu.itba.hci_app.network.api.model.PagedList;
import ar.com.edu.itba.hci_app.network.api.model.Rating;
import ar.com.edu.itba.hci_app.network.api.model.Routine;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutinesService {
    @GET("routines")
    LiveData<ApiResponse<PagedList<Routine>>> getRoutines(@Query("difficulty") String difficulty, @Query("page") Integer page,
                                                          @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("user/current/routines/")
    LiveData<ApiResponse<PagedList<Routine>>> getCurrentUserRoutines(@Query("difficulty") String difficulty, @Query("page") Integer page,
                                                                     @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("user/{userID}/routines/")
    LiveData<ApiResponse<PagedList<Routine>>> getUserRoutines(@Path("userID") Integer userID, @Query("difficulty") String difficulty, @Query("page") Integer page,
                                                              @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("user/current/routines/favourites")
    LiveData<ApiResponse<PagedList<Routine>>> getCurrentUserFavourites(@Query("page") Integer page,
                                                                       @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("user/current/routines/ratings")
    LiveData<ApiResponse<PagedList<Routine>>> getCurrentUserRoutinesRatings(@Query("page") Integer page,
                                                                            @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines/{routineID}")
    LiveData<ApiResponse<Routine>> getRoutineByID(@Path("routineID") Integer routineID);

    @GET("routines/{routineID}/ratings")
    LiveData<ApiResponse<PagedList<Rating>>> getRoutineRating(@Path("routineID") Integer routineID, @Query("page") Integer page,
                                                   @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines/{routineID/cycles")
    LiveData<ApiResponse<PagedList<Cycle>>> getRoutineCycles(@Path("routineID") Integer routineID, @Query("page") Integer page,
                                                             @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines/{routineID}/cycles/{cycleID}")
    LiveData<ApiResponse<Cycle>> getRoutineCycleByID(@Path("routineID") Integer routineID, @Path("cycleID") Integer cycleID);

    @GET("routines/{routineID}/cycles/{cycleID}/exercises")
    LiveData<ApiResponse<PagedList<Exercise>>> getCycleExercises(@Path("routineID") Integer routineID, @Path("cycleID") Integer cycleID, @Query("page") Integer page,
                                                                 @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines/{routineID}/cycles/{cycleID}/exercises/{exerciseID}")
    LiveData<ApiResponse<Exercise>> getExerciseByID(@Path("routineID") Integer routineID, @Path("cycleID") Integer cycleID,
                                                    @Path("exerciseID") Integer exerciseID);

    @POST("user/current/routines/{routineID}/favourites")
    LiveData<ApiResponse<Void>> addToFavourites(@Path("routineID") Integer routineID);

    @POST("routines/{routineID}/ratings")
    LiveData<ApiResponse<Rating>> addRatingToRoutine(@Path("routineID") Integer routineID, @Body Rating rating);

    @DELETE("user/current/routines/{routineID}/favourites")
    LiveData<ApiResponse<Void>> removeFromFavourites(@Path("routineID") Integer routineID);

}
