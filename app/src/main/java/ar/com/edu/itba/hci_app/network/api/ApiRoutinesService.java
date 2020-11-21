package ar.com.edu.itba.hci_app.network.api;

import androidx.lifecycle.LiveData;

import ar.com.edu.itba.hci_app.network.api.model.CategoryModel;
import ar.com.edu.itba.hci_app.network.api.model.CycleModel;
import ar.com.edu.itba.hci_app.network.api.model.ExerciseModel;
import ar.com.edu.itba.hci_app.network.api.model.PagedList;
import ar.com.edu.itba.hci_app.network.api.model.RatingModel;
import ar.com.edu.itba.hci_app.network.api.model.RoutineModel;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRoutinesService {
    @GET("routines")
    LiveData<ApiResponse<PagedList<RoutineModel>>> getRoutines(@Query("difficulty") String difficulty, @Query("page") Integer page,
                                                               @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines")
    LiveData<ApiResponse<PagedList<RoutineModel>>> searchRoutines(@Query("search") String query,@Query("page") Integer page,@Query("size") Integer size);

    @GET("user/current/routines/")
    LiveData<ApiResponse<PagedList<RoutineModel>>> getCurrentUserRoutines(@Query("difficulty") String difficulty, @Query("page") Integer page,
                                                                          @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("user/{userID}/routines/")
    LiveData<ApiResponse<PagedList<RoutineModel>>> getUserRoutines(@Path("userID") Integer userID, @Query("difficulty") String difficulty, @Query("page") Integer page,
                                                                   @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("user/current/routines/favourites")
    LiveData<ApiResponse<PagedList<RoutineModel>>> getCurrentUserFavourites(@Query("page") Integer page,
                                                                            @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("user/current/routines/ratings")
    LiveData<ApiResponse<PagedList<RatingModel>>> getCurrentUserRoutinesRatings(@Query("page") Integer page,
                                                                                @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines/{routineID}")
    LiveData<ApiResponse<RoutineModel>> getRoutineByID(@Path("routineID") Integer routineID);

    @GET("routines/{routineID}/ratings")
    LiveData<ApiResponse<PagedList<RatingModel>>> getRoutineRating(@Path("routineID") Integer routineID, @Query("page") Integer page,
                                                                   @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines/{routineID}/cycles")
    LiveData<ApiResponse<PagedList<CycleModel>>> getRoutineCycles(@Path("routineID") Integer routineID, @Query("page") Integer page,
                                                                  @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines/{routineID}/cycles/{cycleID}")
    LiveData<ApiResponse<CycleModel>> getRoutineCycleByID(@Path("routineID") Integer routineID, @Path("cycleID") Integer cycleID);

    @GET("routines/{routineID}/cycles/{cycleID}/exercises")
    LiveData<ApiResponse<PagedList<ExerciseModel>>> getCycleExercises(@Path("routineID") Integer routineID, @Path("cycleID") Integer cycleID, @Query("page") Integer page,
                                                                      @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("routines/{routineID}/cycles/{cycleID}/exercises/{exerciseID}")
    LiveData<ApiResponse<ExerciseModel>> getExerciseByID(@Path("routineID") Integer routineID, @Path("cycleID") Integer cycleID,
                                                         @Path("exerciseID") Integer exerciseID);

    @GET("categories")
    LiveData<ApiResponse<PagedList<CategoryModel>>> getCategories(@Query("page") Integer page,
                                                                  @Query("size") Integer size, @Query("orderBy") String orderBy, @Query("direction") String direction);

    @GET("categories/{categoryID}")
    LiveData<ApiResponse<CategoryModel>> getCategoryById(@Path("categoryID") int categoryID);

    @POST("user/current/routines/{routineID}/favourites")
    LiveData<ApiResponse<Void>> addToFavourites(@Path("routineID") Integer routineID);

    @POST("routines/{routineID}/ratings")
    LiveData<ApiResponse<RatingModel>> addRatingToRoutine(@Path("routineID") Integer routineID, @Body RatingModel rating);

    @DELETE("user/current/routines/{routineID}/favourites")
    LiveData<ApiResponse<Void>> removeFromFavourites(@Path("routineID") Integer routineID);

}
