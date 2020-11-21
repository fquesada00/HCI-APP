package ar.com.edu.itba.hci_app.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import ar.com.edu.itba.hci_app.db.MyDatabase;
import ar.com.edu.itba.hci_app.db.entity.CategoryEntity;
import ar.com.edu.itba.hci_app.db.entity.CreatorEntity;
import ar.com.edu.itba.hci_app.db.entity.CycleEntity;
import ar.com.edu.itba.hci_app.db.entity.ExerciseEntity;
import ar.com.edu.itba.hci_app.db.entity.RatingCurrentEntity;
import ar.com.edu.itba.hci_app.db.entity.RatingEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineCurrentEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineEntity;
import ar.com.edu.itba.hci_app.db.entity.RoutineFavEntity;
import ar.com.edu.itba.hci_app.domain.Category;
import ar.com.edu.itba.hci_app.domain.Creator;
import ar.com.edu.itba.hci_app.domain.Cycle;
import ar.com.edu.itba.hci_app.domain.Exercise;
import ar.com.edu.itba.hci_app.domain.Rating;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.AbsentLiveData;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.api.ApiResponse;
import ar.com.edu.itba.hci_app.network.api.ApiRoutinesService;
import ar.com.edu.itba.hci_app.network.api.model.CategoryModel;
import ar.com.edu.itba.hci_app.network.api.model.CreatorModel;
import ar.com.edu.itba.hci_app.network.api.model.CycleModel;
import ar.com.edu.itba.hci_app.network.api.model.ExerciseModel;
import ar.com.edu.itba.hci_app.network.api.model.PagedList;
import ar.com.edu.itba.hci_app.network.api.model.RatingModel;
import ar.com.edu.itba.hci_app.network.api.model.RoutineModel;
import ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter;

public class RoutineRepository extends BaseRepository {

    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";
    private final ApiRoutinesService apiService;
    private RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public RoutineRepository(AppExecutors executors, ApiRoutinesService service, MyDatabase database) {
        super(executors, database);
        this.apiService = service;
    }


    private Creator mapCreatorEntityToDomain(CreatorEntity userEntity) {
        return new Creator(userEntity.dateLastActive, userEntity.dateCreated, userEntity.gender, userEntity.avatarUrl, userEntity.id, userEntity.username);
    }

    private CreatorEntity mapCreatorModelToEntity(CreatorModel creatorModel) {
        return new CreatorEntity(creatorModel.getId(), creatorModel.getUsername(), creatorModel.getGender(), creatorModel.getAvatarUrl(),
                creatorModel.getDateCreated(), creatorModel.getDateLastActive());
    }

    private Creator mapCreatorModelToDomain(CreatorModel creatorModel) {
        return new Creator(creatorModel.getDateLastActive(), creatorModel.getDateCreated(), creatorModel.getGender(), creatorModel.getAvatarUrl(),
                creatorModel.getId(), creatorModel.getUsername());
    }

    private Category mapCategoryEntityToDomain(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.name, categoryEntity.id, categoryEntity.detail);
    }

    private CategoryEntity mapCategoryModelToEntity(CategoryModel categoryModel) {
        return new CategoryEntity(categoryModel.getId(), categoryModel.getName(), categoryModel.getDetail());
    }

    private Category mapCategoryModelToDomain(CategoryModel categoryModel) {
        return new Category(categoryModel.getName(), categoryModel.getId(), categoryModel.getDetail());
    }

    private Routine mapRoutineEntityToDomain(RoutineEntity routineEntity) {
        Creator creator = mapCreatorEntityToDomain(routineEntity.creator);
        Category category = mapCategoryEntityToDomain(routineEntity.category);
        return new Routine(routineEntity.difficulty, creator, routineEntity.dateCreated, routineEntity.averageRating,
                routineEntity.name, routineEntity.isPublic, routineEntity.id, routineEntity.detail, category);
    }

    private Routine mapRoutineEntityToDomain(RoutineCurrentEntity routineEntity) {
        Creator creator = mapCreatorEntityToDomain(routineEntity.creator);
        Category category = mapCategoryEntityToDomain(routineEntity.category);
        return new Routine(routineEntity.difficulty, creator, routineEntity.dateCreated, routineEntity.averageRating,
                routineEntity.name, routineEntity.isPublic, routineEntity.id, routineEntity.detail, category);
    }

    private Routine mapRoutineEntityToDomain(RoutineFavEntity routineEntity) {
        Creator creator = mapCreatorEntityToDomain(routineEntity.creator);
        Category category = mapCategoryEntityToDomain(routineEntity.category);
        return new Routine(routineEntity.difficulty, creator, routineEntity.dateCreated, routineEntity.averageRating,
                routineEntity.name, routineEntity.isPublic, routineEntity.id, routineEntity.detail, category);
    }

    private RoutineEntity mapRoutineModelToEntity(RoutineModel routineModel) {
        CreatorEntity creatorEntity = mapCreatorModelToEntity(routineModel.getCreator());
        CategoryEntity categoryEntity = mapCategoryModelToEntity(routineModel.getCategory());
        return new RoutineEntity(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(),
                routineModel.getAverageRating(), routineModel.isIsPublic(), routineModel.getDifficulty(), categoryEntity, creatorEntity);
    }

    private RoutineCurrentEntity mapRoutineModelToCurrentEntity(RoutineModel routineModel) {
        CreatorEntity creatorEntity = mapCreatorModelToEntity(routineModel.getCreator());
        CategoryEntity categoryEntity = mapCategoryModelToEntity(routineModel.getCategory());
        return new RoutineCurrentEntity(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(),
                routineModel.getAverageRating(), routineModel.isIsPublic(), routineModel.getDifficulty(), categoryEntity, creatorEntity);
    }

    private RoutineFavEntity mapRoutineModelToFavEntity(RoutineModel routineModel) {
        CreatorEntity creatorEntity = mapCreatorModelToEntity(routineModel.getCreator());
        CategoryEntity categoryEntity = mapCategoryModelToEntity(routineModel.getCategory());
        return new RoutineFavEntity(routineModel.getId(), routineModel.getName(), routineModel.getDetail(), routineModel.getDateCreated(),
                routineModel.getAverageRating(), routineModel.isIsPublic(), routineModel.getDifficulty(), categoryEntity, creatorEntity);
    }

    private Routine mapRoutineModelToDomain(RoutineModel routineModel) {
        Creator creator = mapCreatorModelToDomain(routineModel.getCreator());
        Category category = mapCategoryModelToDomain(routineModel.getCategory());
        return new Routine(routineModel.getDifficulty(), creator, routineModel.getDateCreated(), routineModel.getAverageRating(), routineModel.getName(),
                routineModel.isIsPublic(), routineModel.getId(), routineModel.getDetail(), category);
    }

    private Rating mapRatingEntityToDomain(RatingEntity ratingEntity) {
        Routine routine = mapRoutineEntityToDomain(ratingEntity.routine);
        return new Rating(ratingEntity.date, ratingEntity.score, routine, ratingEntity.review, ratingEntity.id);
    }

    private Rating mapRatingEntityToDomain(RatingCurrentEntity ratingEntity) {
        Routine routine = mapRoutineEntityToDomain(ratingEntity.routine);
        return new Rating(ratingEntity.date, ratingEntity.score, routine, ratingEntity.review, ratingEntity.id);
    }

    private RatingEntity mapRatingModelToEntity(RatingModel ratingModel) {
        RoutineEntity routine = mapRoutineModelToEntity(ratingModel.getRoutine());
        return new RatingEntity(ratingModel.getId(), ratingModel.getDate(), ratingModel.getScore(), ratingModel.getReview(),
                routine);
    }

    private RatingCurrentEntity mapRatingModelToCurrentEntity(RatingModel ratingModel) {
        RoutineCurrentEntity routine = mapRoutineModelToCurrentEntity(ratingModel.getRoutine());
        return new RatingCurrentEntity(ratingModel.getId(), ratingModel.getDate(), ratingModel.getScore(), ratingModel.getReview(), routine);
    }

    private Rating mapRatingModelToDomain(RatingModel ratingModel) {
        Routine routine = mapRoutineModelToDomain(ratingModel.getRoutine());
        return new Rating(ratingModel.getDate(), ratingModel.getScore(), routine, ratingModel.getReview(), ratingModel.getId());
    }

    private Cycle mapCycleEntityToDomain(CycleEntity cycleEntity) {
        return new Cycle(cycleEntity.name, cycleEntity.id, cycleEntity.detail, cycleEntity.type, cycleEntity.repetitions, cycleEntity.order);
    }

    private CycleEntity mapCycleModelToEntity(CycleModel cycleModel, int routineId) {
        return new CycleEntity(cycleModel.getId(), cycleModel.getName(), cycleModel.getDetail(), cycleModel.getType(), cycleModel.getOrder(),
                cycleModel.getRepetitions(), routineId);
    }

    private Cycle mapCycleModelToDomain(CycleModel cycleModel) {
        return new Cycle(cycleModel.getName(), cycleModel.getId(), cycleModel.getDetail(), cycleModel.getType(), cycleModel.getRepetitions(), cycleModel.getOrder());
    }

    private Exercise mapExerciseEntityToDomain(ExerciseEntity exerciseEntity) {
        return new Exercise(exerciseEntity.duration, exerciseEntity.name, exerciseEntity.id, exerciseEntity.detail, exerciseEntity.type,
                exerciseEntity.repetitions, exerciseEntity.order,exerciseEntity.cycleId);
    }

    private ExerciseEntity mapExerciseModelToEntity(ExerciseModel exerciseModel, int cycleID) {
        return new ExerciseEntity(exerciseModel.getId(), exerciseModel.getName(), exerciseModel.getDetail(),
                exerciseModel.getType(), exerciseModel.getDuration(), exerciseModel.getRepetitions(), exerciseModel.getOrder(),
                cycleID);
    }

    private Exercise mapExerciseModelToDomain(ExerciseModel exerciseModel, int cycleId) {
        return new Exercise(exerciseModel.getDuration(), exerciseModel.getName(), exerciseModel.getId(), exerciseModel.getDetail(),
                exerciseModel.getType(), exerciseModel.getRepetitions(), exerciseModel.getOrder(),cycleId);
    }



    public LiveData<Resource<List<Routine>>> getRoutine(@Nullable String difficulty, @Nullable Integer page,
                                                        @Nullable Integer size, @Nullable String orderBy,
                                                        @Nullable String direction, @NotNull RoutinePagedListGetter selector) {
        switch (selector) {
            case ALL:
                return new NetworkBoundResource<List<Routine>, List<RoutineEntity>, PagedList<RoutineModel>>(executors,
                        routineEntities -> routineEntities.stream().map(this::mapRoutineEntityToDomain).collect(Collectors.toList()),
                        model -> model.getResults().stream().map(this::mapRoutineModelToEntity).collect(Collectors.toList()),
                        model -> model.getResults().stream().map(this::mapRoutineModelToDomain).collect(Collectors.toList())) {


                    @Override
                    protected void saveCallResult(@NonNull List<RoutineEntity> entity) {
                        database.routineDao().deleteRoutines(size,page*size);
                        CategoryEntity categoryEntity;
                        CreatorEntity creatorEntity;
                        for (int i = 0; i < entity.size(); i++) {
                            categoryEntity = entity.get(i).category;
                            creatorEntity = entity.get(i).creator;
                            database.categoryDao().delete(categoryEntity);
                            database.categoryDao().insert(categoryEntity);
                            database.userDao().delete(creatorEntity);
                            database.userDao().insert(creatorEntity);
                        }
                        database.routineDao().insertRoutine(entity);
                    }

                    @Override
                    protected boolean shouldFetch(@Nullable List<RoutineEntity> entity) {
                        return ((entity == null) || (size != null && entity.size() < size) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
                    }

                    @Override
                    protected boolean shouldPersist(@Nullable PagedList<RoutineModel> model) {
                        return true;
                    }

                    @NonNull
                    @Override
                    protected LiveData<List<RoutineEntity>> loadFromDb() {
                        return database.routineDao().getRoutines(size,page*size);
                    }

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<RoutineModel>>> createCall() {
                        return apiService.getRoutines(difficulty, page, size, orderBy, direction);
                    }
                }.asLiveData();
            case CURRENT:
                return new NetworkBoundResource<List<Routine>, List<RoutineCurrentEntity>, PagedList<RoutineModel>>(executors,
                        routineEntities -> routineEntities.stream().map(this::mapRoutineEntityToDomain).collect(Collectors.toList()),
                        model -> model.getResults().stream().map(this::mapRoutineModelToCurrentEntity).collect(Collectors.toList()),
                        model -> model.getResults().stream().map(this::mapRoutineModelToDomain).collect(Collectors.toList())) {


                    @Override
                    protected void saveCallResult(@NonNull List<RoutineCurrentEntity> entity) {
                        database.routineDao().deleteCurrentUserRoutines();
                        CategoryEntity categoryEntity;
                        CreatorEntity creatorEntity;
                        for (int i = 0; i < entity.size(); i++) {
                            categoryEntity = entity.get(i).category;
                            creatorEntity = entity.get(i).creator;
                            database.categoryDao().delete(categoryEntity);
                            database.categoryDao().insert(categoryEntity);
                            database.userDao().delete(creatorEntity);
                            database.userDao().insert(creatorEntity);
                        }
                        database.routineDao().insertCurrentRoutine(entity);

                    }

                    @Override
                    protected boolean shouldFetch(@Nullable List<RoutineCurrentEntity> entity) {
                        return ((entity == null) || (size != null && entity.size() < size) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
                    }

                    @Override
                    protected boolean shouldPersist(@Nullable PagedList<RoutineModel> model) {
                        return true;
                    }

                    @NonNull
                    @Override
                    protected LiveData<List<RoutineCurrentEntity>> loadFromDb() {
                        return database.routineDao().getCurrentRoutines();
                    }

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<RoutineModel>>> createCall() {
                        return apiService.getCurrentUserRoutines(difficulty, page, size, orderBy, direction);
                    }
                }.asLiveData();
            case FAVOURITES:
                return new NetworkBoundResource<List<Routine>, List<RoutineFavEntity>, PagedList<RoutineModel>>(executors,
                        routineEntities -> routineEntities.stream().map(this::mapRoutineEntityToDomain).collect(Collectors.toList()),
                        model -> model.getResults().stream().map(this::mapRoutineModelToFavEntity).collect(Collectors.toList()),
                        model -> model.getResults().stream().map(this::mapRoutineModelToDomain).collect(Collectors.toList())) {


                    @Override
                    protected void saveCallResult(@NonNull List<RoutineFavEntity> entity) {
                        database.routineDao().deleteFavRoutines();
                        CategoryEntity categoryEntity;
                        CreatorEntity creatorEntity;
                        for (int i = 0; i < entity.size(); i++) {
                            categoryEntity = entity.get(i).category;
                            creatorEntity = entity.get(i).creator;
                            database.categoryDao().delete(categoryEntity);
                            database.categoryDao().insert(categoryEntity);
                            database.userDao().delete(creatorEntity);
                            database.userDao().insert(creatorEntity);
                        }
                        database.routineDao().insertRoutineFav(entity);

                    }

                    @Override
                    protected boolean shouldFetch(@Nullable List<RoutineFavEntity> entity) {
                        return ((entity == null) || (size != null && entity.size() < size) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
                    }

                    @Override
                    protected boolean shouldPersist(@Nullable PagedList<RoutineModel> model) {
                        return true;
                    }

                    @NonNull
                    @Override
                    protected LiveData<List<RoutineFavEntity>> loadFromDb() {
                        return database.routineDao().getUserFavs();
                    }

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<RoutineModel>>> createCall() {
                        return apiService.getCurrentUserFavourites(page, size, orderBy, direction);
                    }
                }.asLiveData();
        }
        throw new IllegalArgumentException("Bad api call in repo");
    }

    public LiveData<Resource<List<Routine>>> searchRoutines(@NonNull String query,@NonNull Integer page,@NonNull Integer size){
        return new NetworkBoundResource<List<Routine>,List<RoutineEntity>,PagedList<RoutineModel>>(executors,
                routineEntities -> routineEntities.stream().map(this::mapRoutineEntityToDomain).collect(Collectors.toList()),
                routinePagedList -> routinePagedList.getResults().stream().map(this::mapRoutineModelToEntity).collect(Collectors.toList()),
                routinePagedList -> routinePagedList.getResults().stream().map(this::mapRoutineModelToDomain).collect(Collectors.toList())
                ){


            @Override
            protected void saveCallResult(@NonNull List<RoutineEntity> entity) {
                entity.forEach(e->database.routineDao().delete(e));
                database.routineDao().insertRoutine(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RoutineEntity> entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<RoutineModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RoutineEntity>> loadFromDb() {
                LiveData<List<RoutineEntity>> el;
                Log.d("SEARCH", "loadFromDb: searching " + query);
                (el = database.routineDao().searchRoutines(query+"%")).observeForever(e->{
                    Log.d("SEARCH", "loadFromDb: " + e.size());
                });
                return el;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineModel>>> createCall() {
                LiveData<ApiResponse<PagedList<RoutineModel>>> rt;
                (rt = apiService.searchRoutines(query,page,size)) .observeForever(e->{
                    if(e.getData() != null)
                        Log.d("SEARCH", "createCall: " + e.getData().getSize());
                    Log.d("search", "createCall: sending" + query + " page " + page + " size " + size );
                });
                return rt;
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Routine>>> getRoutine(@NonNull RoutinePagedListGetter selector){
        return getRoutine(null,null,null,null,null,selector);
    }

    public LiveData<Resource<List<Routine>>> getRoutine(@NonNull Integer page, @NonNull Integer size,@NonNull RoutinePagedListGetter selector){
        return getRoutine(null,page,size,null,null,selector);
    }

    public LiveData<Resource<List<Routine>>> getRoutinesNoSave(@Nullable String difficulty, @Nullable Integer page,
                                                               @Nullable Integer size, @Nullable String orderBy,
                                                               @Nullable String direction, @NotNull RoutinePagedListGetter selector){

        switch (selector){
            case ALL:
                return new NetworkBoundResource<List<Routine>,List<RoutineEntity>,PagedList<RoutineModel>>(executors,
                        routineEntities -> routineEntities.stream().map(this::mapRoutineEntityToDomain).collect(Collectors.toList()),
                        routineModelPagedList -> routineModelPagedList.getResults().stream().map(this::mapRoutineModelToEntity).collect(Collectors.toList()),
                        routineModelPagedList -> routineModelPagedList.getResults().stream().map(this::mapRoutineModelToDomain).collect(Collectors.toList())
                ){

                    @Override
                    protected void saveCallResult(@NonNull List<RoutineEntity> entity) {
                    }

                    @Override
                    protected boolean shouldFetch(@Nullable List<RoutineEntity> entity) {
                        return true;
                    }

                    @Override
                    protected boolean shouldPersist(@Nullable PagedList<RoutineModel> model) {
                        return false;
                    }

                    @NonNull
                    @Override
                    protected LiveData<List<RoutineEntity>> loadFromDb() {
                        return database.routineDao().getRoutines();
                    }

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<RoutineModel>>> createCall() {
                        return apiService.getRoutines(difficulty,page,size,orderBy,direction);
                    }

                }.asLiveData();
            case CURRENT:
                return new NetworkBoundResource<List<Routine>,List<RoutineEntity>,PagedList<RoutineModel>>(executors,
                        routineEntities -> routineEntities.stream().map(this::mapRoutineEntityToDomain).collect(Collectors.toList()),
                        routineModelPagedList -> routineModelPagedList.getResults().stream().map(this::mapRoutineModelToEntity).collect(Collectors.toList()),
                        routineModelPagedList -> routineModelPagedList.getResults().stream().map(this::mapRoutineModelToDomain).collect(Collectors.toList())
                ){

                    @Override
                    protected void saveCallResult(@NonNull List<RoutineEntity> entity) {
                    }

                    @Override
                    protected boolean shouldFetch(@Nullable List<RoutineEntity> entity) {
                        return true;
                    }

                    @Override
                    protected boolean shouldPersist(@Nullable PagedList<RoutineModel> model) {
                        return false;
                    }

                    @NonNull
                    @Override
                    protected LiveData<List<RoutineEntity>> loadFromDb() {
                        return database.routineDao().getRoutines();
                    }

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<RoutineModel>>> createCall() {
                        return apiService.getCurrentUserRoutines(difficulty,page,size,orderBy,direction);
                    }

                }.asLiveData();

            case FAVOURITES:
                return new NetworkBoundResource<List<Routine>,List<RoutineEntity>,PagedList<RoutineModel>>(executors,
                        routineEntities -> routineEntities.stream().map(this::mapRoutineEntityToDomain).collect(Collectors.toList()),
                        routineModelPagedList -> routineModelPagedList.getResults().stream().map(this::mapRoutineModelToEntity).collect(Collectors.toList()),
                        routineModelPagedList -> routineModelPagedList.getResults().stream().map(this::mapRoutineModelToDomain).collect(Collectors.toList())
                ){

                    @Override
                    protected void saveCallResult(@NonNull List<RoutineEntity> entity) {
                    }

                    @Override
                    protected boolean shouldFetch(@Nullable List<RoutineEntity> entity) {
                        return true;
                    }

                    @Override
                    protected boolean shouldPersist(@Nullable PagedList<RoutineModel> model) {
                        return false;
                    }

                    @NonNull
                    @Override
                    protected LiveData<List<RoutineEntity>> loadFromDb() {
                        return database.routineDao().getRoutines();
                    }

                    @NonNull
                    @Override
                    protected LiveData<ApiResponse<PagedList<RoutineModel>>> createCall() {
                        return apiService.getCurrentUserFavourites(page,size,orderBy,direction);
                    }

                }.asLiveData();
        }
        throw new IllegalArgumentException("Bad selector");

    }

    public LiveData<Resource<List<Rating>>> getCurrentUserRatings(@Nullable Integer page,
                                                                  @Nullable Integer size, @Nullable String orderBy,
                                                                  @Nullable String direction) {
        return new NetworkBoundResource<List<Rating>, List<RatingCurrentEntity>, PagedList<RatingModel>>(executors,
                ratingEntities -> ratingEntities.stream().map(this::mapRatingEntityToDomain).collect(Collectors.toList()),
                ratingModelPagedList -> ratingModelPagedList.getResults().stream().map(this::mapRatingModelToCurrentEntity).collect(Collectors.toList()),
                ratingModelPagedList -> ratingModelPagedList.getResults().stream().map(this::mapRatingModelToDomain).collect(Collectors.toList())) {

            @Override
            protected void saveCallResult(@NonNull List<RatingCurrentEntity> entity) {
                database.routineDao().deleteCurrentRatings();
                database.routineDao().insertCurrentRating(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RatingCurrentEntity> entity) {
                return (entity == null || entity.size() == 0 || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<RatingModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RatingCurrentEntity>> loadFromDb() {
                return database.routineDao().getCurrentRatings();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RatingModel>>> createCall() {
                return apiService.getCurrentUserRoutinesRatings(page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Routine>>> getRoutinesFromUser(@NonNull Integer userID, @Nullable String difficulty, @Nullable Integer page,
                                                                 @Nullable Integer size, @Nullable String orderBy,
                                                                 @Nullable String direction) {
        return new NetworkBoundResource<List<Routine>, List<RoutineEntity>, PagedList<RoutineModel>>(executors,
                routineEntities -> routineEntities.stream().map(this::mapRoutineEntityToDomain).collect(Collectors.toList()),
                routineModelPagedList -> routineModelPagedList.getResults().stream().map(this::mapRoutineModelToEntity).collect(Collectors.toList()),
                routineModelPagedList -> routineModelPagedList.getResults().stream().map(this::mapRoutineModelToDomain).collect(Collectors.toList())) {

            @Override
            protected void saveCallResult(@NonNull List<RoutineEntity> entity) {
                database.routineDao().deleteRoutineFromCreator(userID);
                CategoryEntity categoryEntity;
                CreatorEntity creatorEntity;
                for (int i = 0; i < entity.size(); i++) {
                    categoryEntity = entity.get(i).category;
                    creatorEntity = entity.get(i).creator;
                    database.categoryDao().delete(categoryEntity);
                    database.categoryDao().insert(categoryEntity);
                    database.userDao().delete(creatorEntity);
                    database.userDao().insert(creatorEntity);
                }
                database.routineDao().insertRoutine(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RoutineEntity> entity) {
                return (entity == null || entity.size() == 0 || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<RoutineModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RoutineEntity>> loadFromDb() {
                return database.routineDao().getUserRoutines(userID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RoutineModel>>> createCall() {
                return apiService.getUserRoutines(userID, difficulty, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> getRoutineByID(@NonNull Integer routineID) {
        return new NetworkBoundResource<Routine, RoutineEntity, RoutineModel>(executors,
                this::mapRoutineEntityToDomain,
                this::mapRoutineModelToEntity,
                this::mapRoutineModelToDomain) {

            @Override
            protected void saveCallResult(@NonNull RoutineEntity entity) {
                database.routineDao().delete(entity);
                database.categoryDao().delete(entity.category);
                database.categoryDao().insert(entity.category);
                database.userDao().delete(entity.creator);
                database.userDao().insert(entity.creator);
                database.routineDao().insertRoutine(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineEntity entity) {
                return entity == null || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY);
            }

            @Override
            protected boolean shouldPersist(@Nullable RoutineModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineEntity> loadFromDb() {
                return database.routineDao().getRoutine(routineID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RoutineModel>> createCall() {
                return apiService.getRoutineByID(routineID);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Rating>>> getRoutineRatings(@NonNull Integer routineID, @Nullable String difficulty, @Nullable Integer page,
                                                              @Nullable Integer size, @Nullable String orderBy,
                                                              @Nullable String direction) {
        return new NetworkBoundResource<List<Rating>, List<RatingEntity>, PagedList<RatingModel>>(executors,
                ratingEntities -> ratingEntities.stream().map(this::mapRatingEntityToDomain).collect(Collectors.toList()),
                ratingModelPagedList -> ratingModelPagedList.getResults().stream().map(this::mapRatingModelToEntity).collect(Collectors.toList()),
                ratingModelPagedList -> ratingModelPagedList.getResults().stream().map(this::mapRatingModelToDomain).collect(Collectors.toList())) {

            @Override
            protected void saveCallResult(@NonNull List<RatingEntity> entity) {
                database.routineDao().deleteRoutineRatings(routineID);
                database.routineDao().insertRating(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RatingEntity> entity) {
                return ((entity == null) || entity.size() == 0 || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<RatingModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RatingEntity>> loadFromDb() {
                return database.routineDao().getRoutineRating(routineID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<RatingModel>>> createCall() {
                return apiService.getRoutineRating(routineID, page, size, orderBy, direction);
            }
        }.asLiveData();

    }

    public LiveData<Resource<List<Cycle>>> getRoutineCycles(@NonNull Integer routineID, @Nullable String difficulty, @Nullable Integer page,
                                                            @Nullable Integer size, @Nullable String orderBy,
                                                            @Nullable String direction) {
        return new NetworkBoundResource<List<Cycle>, List<CycleEntity>, PagedList<CycleModel>>(executors,
                cycleEntities -> cycleEntities.stream().map(this::mapCycleEntityToDomain).collect(Collectors.toList()),
                cycleModelPagedList -> cycleModelPagedList.getResults().stream().map(cycleModel -> mapCycleModelToEntity(cycleModel, routineID)).collect(Collectors.toList()),
                cycleModelPagedList -> cycleModelPagedList.getResults().stream().map(this::mapCycleModelToDomain).collect(Collectors.toList())) {

            @Override
            protected void saveCallResult(@NonNull List<CycleEntity> entity) {
                database.cycleDao().deleteRoutineCycles(routineID);
                database.cycleDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CycleEntity> entity) {
                return true;
//                return ((entity == null) || entity.size() == 0 || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<CycleModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CycleEntity>> loadFromDb() {
                return database.cycleDao().getRoutineCycles(routineID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<CycleModel>>> createCall() {
                return apiService.getRoutineCycles(routineID, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Cycle>> getCycleByID(@NonNull Integer routineID, @NonNull Integer cycleID) {
        return new NetworkBoundResource<Cycle, CycleEntity, CycleModel>(executors,
                this::mapCycleEntityToDomain,
                cycleModel -> mapCycleModelToEntity(cycleModel, routineID),
                this::mapCycleModelToDomain) {

            @Override
            protected void saveCallResult(@NonNull CycleEntity entity) {
                database.cycleDao().delete(entity);
                database.cycleDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable CycleEntity entity) {
                return entity == null || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY);
            }

            @Override
            protected boolean shouldPersist(@Nullable CycleModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CycleEntity> loadFromDb() {
                return database.cycleDao().getCycle(routineID, cycleID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CycleModel>> createCall() {
                return apiService.getRoutineCycleByID(routineID, cycleID);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Exercise>>> getCycleExercises(@NonNull Integer routineID, @NonNull Integer cycleID, @Nullable Integer page,
                                                                @Nullable Integer size, @Nullable String orderBy,
                                                                @Nullable String direction) {
        return new NetworkBoundResource<List<Exercise>, List<ExerciseEntity>, PagedList<ExerciseModel>>(executors,
                exerciseEntities -> exerciseEntities.stream().map(e ->mapExerciseEntityToDomain(e)).collect(Collectors.toList()),
                exerciseModelPagedList -> exerciseModelPagedList.getResults().stream().map(exerciseModel -> mapExerciseModelToEntity(exerciseModel, cycleID)).collect(Collectors.toList()),
                exerciseModelPagedList -> exerciseModelPagedList.getResults().stream().map(e->mapExerciseModelToDomain(e,cycleID)).collect(Collectors.toList())) {

            @Override
            protected void saveCallResult(@NonNull List<ExerciseEntity> entity) {
                database.exerciseDao().deleteFromCycle(cycleID);
                database.exerciseDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ExerciseEntity> entity) {
                return true;
//                return ((entity == null) || entity.size() == 0 || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<ExerciseModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ExerciseEntity>> loadFromDb() {
                return database.exerciseDao().getCycleExercises(cycleID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<ExerciseModel>>> createCall() {
                return apiService.getCycleExercises(routineID, cycleID, page, size, orderBy, direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Exercise>> getExerciseByID(@NonNull Integer routineID, @NonNull Integer cycleID, @NonNull Integer exerciseID) {
        return new NetworkBoundResource<Exercise, ExerciseEntity, ExerciseModel>(executors,
                e->mapExerciseEntityToDomain(e),
                exerciseModel -> mapExerciseModelToEntity(exerciseModel, cycleID),
                e->mapExerciseModelToDomain(e,cycleID)) {

            @Override
            protected void saveCallResult(@NonNull ExerciseEntity entity) {
                database.exerciseDao().delete(entity);
                database.exerciseDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable ExerciseEntity entity) {
                return entity == null || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY);
            }

            @Override
            protected boolean shouldPersist(@Nullable ExerciseModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ExerciseEntity> loadFromDb() {
                return database.exerciseDao().getExerciseById(exerciseID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ExerciseModel>> createCall() {
                return apiService.getExerciseByID(routineID, cycleID, exerciseID);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Category>>> getCategories(@Nullable Integer page,
                                                            @Nullable Integer size, @Nullable String orderBy,
                                                            @Nullable String direction){
        return new NetworkBoundResource<List<Category>, List<CategoryEntity>, PagedList<CategoryModel>>(executors,
                categoryEntities -> categoryEntities.stream().map(this::mapCategoryEntityToDomain).collect(Collectors.toList()),
                categoryModelPagedList -> categoryModelPagedList.getResults().stream().map(this::mapCategoryModelToEntity).collect(Collectors.toList()),
                categoryModelPagedList -> categoryModelPagedList.getResults().stream().map(this::mapCategoryModelToDomain).collect(Collectors.toList())
        ) {
            @Override
            protected void saveCallResult(@NonNull List<CategoryEntity> entity) {
                database.categoryDao().deleteAll();
                database.categoryDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CategoryEntity> entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable PagedList<CategoryModel> model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CategoryEntity>> loadFromDb() {
                return database.categoryDao().getCategories();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<CategoryModel>>> createCall() {
                return apiService.getCategories(page,size,orderBy,direction);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Category>> getCategoryById(int categoryId){
        return new NetworkBoundResource<Category, CategoryEntity, CategoryModel>(executors,
                this::mapCategoryEntityToDomain,
                this::mapCategoryModelToEntity,
                this::mapCategoryModelToDomain
        ) {
            @Override
            protected void saveCallResult(@NonNull CategoryEntity entity) {
                database.categoryDao().delete(entity);
                database.categoryDao().insert(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable CategoryEntity entity) {
                return (entity == null || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable CategoryModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<CategoryEntity> loadFromDb() {
                return database.categoryDao().getCategoryByIdId(categoryId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CategoryModel>> createCall() {
                return apiService.getCategoryById(categoryId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> addToFavourites(@NonNull Integer routineID) {
        return new NetworkBoundResource<Void, RoutineFavEntity, Void>(executors,
                routineFavEntity -> null,
                model -> null,
                model -> null) {
            int entityId = 0;

            @Override
            protected void saveCallResult(@NonNull RoutineFavEntity entity) {
                database.routineDao().deleteRoutineFav(routineID);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineFavEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineFavEntity> loadFromDb() {
                return database.routineDao().getFavRoutine(routineID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addToFavourites(routineID);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Rating>> addRating(@NonNull Integer routineID, RatingModel rating) {
        return new NetworkBoundResource<Rating, RatingEntity, RatingModel>(executors,
                this::mapRatingEntityToDomain,
                this::mapRatingModelToEntity,
                this::mapRatingModelToDomain) {
            int entityId = 0;

            @Override
            protected void saveCallResult(@NonNull RatingEntity entity) {
                entityId = entity.id;
                database.routineDao().insertRating(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RatingEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable RatingModel model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RatingEntity> loadFromDb() {
                return database.routineDao().getRatingById(entityId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RatingModel>> createCall() {
                return apiService.addRatingToRoutine(routineID, rating);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> removeFromFavourites(@NonNull Integer routineID) {
        return new NetworkBoundResource<Void, RoutineEntity, Void>(executors, routineEntity -> null,
                model -> null,
                model -> null
        ) {
            @Override
            protected void saveCallResult(@NonNull RoutineEntity entity) {
                database.routineDao().delete(entity);
            }

            @Override
            protected boolean shouldFetch(@Nullable RoutineEntity entity) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Void model) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<RoutineEntity> loadFromDb() {
                return database.routineDao().getRoutine(routineID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.removeFromFavourites(routineID);
            }
        }.asLiveData();
    }


}
