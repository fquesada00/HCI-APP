package ar.com.edu.itba.hci_app.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import ar.com.edu.itba.hci_app.domain.Category;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.Status;

import ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter;

import static ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter.ALL;
import static ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter.CURRENT;
import static ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter.FAVOURITES;

import ar.com.edu.itba.hci_app.repository.RoutineRepository;

public class MainActivityViewModel extends AndroidViewModel {

    //TODO que el login haga un getUser y cargue en room, y aca se fetchea de ahi,
    //asi nos ahorramos dos repos

    //HOME FRAGMENT

    /*una routine cualquiera (rutina de hoy)
    routines creadas por el usuario
    routines favs

    get de pagedlist para obtener cada routine (usamos generic con routine aca)
     */

    //STATISTICS FRAGMENT

    /*
    no sabemos que datos usar para representar en el grafico, probablemente
    quede solo el progreso de cada dia
     */

    //SEARCH FRAGMENT

    /*
        getCategory para el display superior
        search por nombre
        mostrar los entrenamientos mejor rateados (vendrian a ser nuestros populares)
        nro fijo de rutinas por pag
     */

    //NOTIFICATIONS FRAGMENT

    /*
    stand by
     */

    //PROFILE FRAGMENT

    /*
        getCurrentUser para la info
        observar
     */


    private RoutineRepository repository;

    //------------------------------------ HOME -------------------------------------

    private void unknownStatusException() {
        throw new IllegalArgumentException("Unkown resource status");
    }

    private <T> void switchResourceStatus(Status status, Resource<T> resource, Class c, MutableLiveData mutableLiveData) {
        switch (status) {
            case LOADING:
                Log.d("LOADING", "vm");
                try {
                    mutableLiveData.setValue(Resource.loading((T) c.getConstructor().newInstance()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ERROR:
                try {
                    mutableLiveData.setValue(Resource.error(resource.getError(), (T) c.getConstructor().newInstance()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                unknownStatusException();
        }
    }

    public MutableLiveData<Resource<Routine>> dailyRoutine;

    public MutableLiveData<Resource<List<Routine>>> currentUserRoutines;

    private MutableLiveData<Resource<List<Routine>>> popularRoutines;

    private MutableLiveData<Resource<List<Routine>>> difficultyRoutines;

    private int currentUserPage = 0;

    private int routinesPerPage = 10;

    private int homeFragmentRoutinesPerPage = 5;

    private String difficultyDirection = "desc";


    public MainActivityViewModel(Application application, RoutineRepository repository) {
        super(application);
        this.repository = repository;
    }

    private void setDailyRoutine() {
        if (dailyRoutine == null)
            dailyRoutine = new MutableLiveData<>();
        repository.getRoutine(null, 0, 1, "dateCreated", "desc", RoutinePagedListGetter.ALL)
                .observeForever(pagedListResource -> {
                    switch (pagedListResource.getStatus()) {
                        case SUCCESS:
                            Log.d("LOADING", "SET DAILY ROUTINE");
                            if (pagedListResource.getData().size() == 0)
                                dailyRoutine.setValue(Resource.success(new Routine()));
                            else
                                dailyRoutine.setValue(Resource.success(pagedListResource.getData().get(0)));
                            break;
                        default:
                            switchResourceStatus(pagedListResource.getStatus(), pagedListResource, Routine.class, dailyRoutine);
                    }
                });
    }

    public LiveData<Resource<Routine>> getDailyRoutine() {
        setDailyRoutine();
        return dailyRoutine;
    }

    private void addCurrentUserPage() {
        currentUserPage++;
    }

    private void substractCurrentUserPage() {
        if (currentUserPage > 0)
            currentUserPage--;
    }

    private void setCurrentUserRoutines() {
        if (currentUserRoutines == null)
            currentUserRoutines = new MutableLiveData<>();
        repository.getRoutine(null, currentUserPage, routinesPerPage, "dateCreated", "asc", RoutinePagedListGetter.CURRENT)
                .observeForever(pagedListResource -> {
                    switch (pagedListResource.getStatus()) {
                        case SUCCESS:
                            if (pagedListResource.getData().size() == 0) {
                                List<Routine> pagedList = new ArrayList<>();
                                createdRoutinesList.setValue(pagedList);
                                currentUserRoutines.setValue(Resource.success(pagedList));
                            } else {
                                currentUserRoutines.setValue(Resource.success(pagedListResource.getData()));
                                createdRoutinesList.setValue(currentUserRoutines.getValue().getData());
                            }
                            Log.d("current", "aca estoy viendo");
                            break;
                        default:
                            switchResourceStatus(pagedListResource.getStatus(), pagedListResource, List.class, currentUserRoutines);
                    }
                });
    }

    public LiveData<Resource<List<Routine>>> getCurrentUserRoutines() {
        setCurrentUserRoutines();
        return currentUserRoutines;

    }

    private void setPopularRoutines() {
        if (popularRoutines == null)
            popularRoutines = new MutableLiveData<>();
        repository.getRoutine(null, 0, homeFragmentRoutinesPerPage, "averageRating", "asc", RoutinePagedListGetter.ALL)
                .observeForever(pagedListResource -> {
                    switch (pagedListResource.getStatus()) {
                        case SUCCESS:
                            if (pagedListResource.getData().size() == 0) {
                                List<Routine> pagedList = new ArrayList<>();
                                popularRoutines.setValue(Resource.success(pagedList));
                            } else
                                popularRoutines.setValue(Resource.success(pagedListResource.getData()));
                            break;
                        default:
                            switchResourceStatus(pagedListResource.getStatus(), pagedListResource, List.class, popularRoutines);
                    }
                });
    }


    public LiveData<Resource<List<Routine>>> getPopularRoutines() {
        setPopularRoutines();
        return popularRoutines;
    }

    public void setDifficultyDirection(String difficultyDirection) {
        this.difficultyDirection = difficultyDirection;
    }

    private void setDifficultyRoutines() {
        if (difficultyRoutines == null)
            difficultyRoutines = new MutableLiveData<>();
        repository.getRoutine(null, 0, homeFragmentRoutinesPerPage, "difficulty", difficultyDirection, RoutinePagedListGetter.ALL)
                .observeForever(pagedListResource -> {
                    switch (pagedListResource.getStatus()) {
                        case SUCCESS:
                            if (pagedListResource.getData().size() == 0) {
                                List<Routine> pagedList = new ArrayList<>();
                                difficultyRoutines.setValue(Resource.success(pagedList));
                            } else
                                difficultyRoutines.setValue(Resource.success(pagedListResource.getData()));
                            break;
                        default:
                            switchResourceStatus(pagedListResource.getStatus(), pagedListResource, List.class, difficultyRoutines);
                    }
                });
    }

    public LiveData<Resource<List<Routine>>> getDifficultyRoutines() {
        setDifficultyRoutines();
        return difficultyRoutines;
    }

    //-------------------------------------------------------------------------------


    //--------------------------------------- SEARCH --------------------------------

    private MutableLiveData<Resource<List<Category>>> categories;

    private MutableLiveData<Resource<Category>> displayCategory;

    private int categoriesPerPage = 10;

    private List<Category> activeCategories = new ArrayList<>();

    private boolean isLastRoutinePage = false;

    private final static int PAGE_SIZE = 10;

    private int routinePage = 0;

    private String query;

    private boolean searchActive = false;

    public void setCurrentGetter(GetRoutinesEnum currentGetter) {
        this.currentGetter = currentGetter;
    }

    private GetRoutinesEnum currentGetter;

    public boolean isSearchActive() {
        return searchActive;
    }

    public void setSearchActive(boolean searchActive) {
        isLastRoutinePage = false;
        routinePage = 0;
        this.searchActive = searchActive;

    }

    private final List<Routine> allRoutines = new ArrayList<>();

    private final MutableLiveData<Resource<List<Routine>>> routines = new MutableLiveData<>();
    //TODO getcategories


    public LiveData<Resource<List<Routine>>> getRoutines(GetRoutinesEnum routinesEnum) {
        if (currentGetter != routinesEnum) {
            isLastRoutinePage = false;
            routinePage = 0;
            currentGetter = routinesEnum;
            routines.setValue(Resource.loading(new ArrayList<>()));
            allRoutines.clear();
        }
        getMoreRoutines();
        return routines;
    }

    public LiveData<Resource<List<Routine>>> getRoutines(GetRoutinesEnum routinesEnum, String query2) {
        query = query2;
        isLastRoutinePage = false;
        routinePage = 0;
        currentGetter = routinesEnum;
        routines.setValue(Resource.loading(new ArrayList<>()));
        allRoutines.clear();
        getMoreRoutines();
        return routines;
    }

    public void getMoreRoutines() {
        if (isLastRoutinePage)
            return;
        switch (currentGetter) {
            case ALL:
                repository.getRoutine(routinePage, PAGE_SIZE, ALL).observeForever(setRoutines());
                break;
            case FAV:
                break;
            case SEARCH:
                repository.searchRoutines(query, routinePage, PAGE_SIZE).observeForever(setRoutines());
                break;
            case CURR:
                break;
        }


    }

//    public LiveData<Resource<List<Routine>>> searchRoutines(String query) {
//        this.query = query;
//        loadSearch(query);
//        return routines;
//
//    }
//
//    private void loadSearch(String query) {
//        LiveData<Resource<List<Routine>>> resourceLiveData;
//        routines.addSource((resourceLiveData = repository.searchRoutines(query, routinePage, PAGE_SIZE)), setRoutines(resourceLiveData));
//    }

    @NotNull
    private Observer<Resource<List<Routine>>> setRoutines() {
        return resource -> {
            if (resource.getStatus() == Status.SUCCESS) {
                if ((resource.getData().size() == 0) || (resource.getData().size() < PAGE_SIZE))
                    isLastRoutinePage = true;
                routinePage++;
                List<Routine> aux;
//                aux = (allRoutines.stream().distinct().filter(e -> !resource.getData().contains(e)).collect(Collectors.toList()));
                allRoutines.clear();
//                allRoutines.addAll(aux);
                allRoutines.addAll(resource.getData());
                Log.d("GETROUTINES", "getMoreRoutines: " + resource.getData().size());
                routines.setValue(Resource.success(allRoutines));
            } else if (resource.getStatus() == Status.LOADING) {
                routines.setValue(resource);
            }
        };
    }

//    public void toggleCategory(Category category){
//        if(activeCategories.contains(category))
//            activeCategories.remove(category);
//        else
//            activeCategories.add(category);
//    }

    //    public List<Category> getActiveCategories(){
//        return activeCategories;
//    }
//    private void setCategories() {
//        if (categories == null)
//            categories = new MutableLiveData<>();
//        repository.getCategories(null, categoriesPerPage, "name", "asc")
//                .observeForever(pagedListResource -> {
//                    switch (pagedListResource.getStatus()) {
//                        case SUCCESS:
//                            if (pagedListResource.getData().getResults().size() == 0){
//                                PagedList<Category> pagedList = new PagedList<>();
//                                pagedList.setResults(new ArrayList<>());
//                                categories.setValue(Resource.success(pagedList));}
//                            else
//                                categories.setValue(Resource.success(pagedListResource.getData()));
//                            break;
//                        default:
//                            switchResourceStatus(pagedListResource.getStatus(), pagedListResource, PagedList.class, categories);
//                    }
//                });
//    }
//
//    public LiveData<Resource<PagedList<Category>>> getCategories() {
//        setCategories();
//        return categories;
//    }
//    private void setCategories() {
//        if (categories == null)
//            categories = new MutableLiveData<>();
//        repository.getCategories(null, categoriesPerPage, "name", "asc")
//                .observeForever(pagedListResource -> {
//                    switch (pagedListResource.getStatus()) {
//                        case SUCCESS:
//                            if (pagedListResource.getData().size() == 0) {
//                                List<Category> pagedList = new ArrayList<>();
//                                categories.setValue(Resource.success(pagedList));
//                            } else
//                                categories.setValue(Resource.success(pagedListResource.getData()));
//                            break;
//                        default:
//                            switchResourceStatus(pagedListResource.getStatus(), pagedListResource, PagedList.class, categories);
//                    }
//                });
//    }

//    public LiveData<Resource<List<Category>>> getCategories() {
//        setCategories();
//        return categories;
//    }

//    public LiveData<Resource<Category>> getCategoryById(@NonNull Integer id) {
//        return repository.getCategoryById(id);
//    }


    //-------------------------------------------------------------------------------


    //-------------------------------------- PROFILE --------------------------------

    private MutableLiveData<Resource<List<Routine>>> favouritesRoutines;

    private MutableLiveData<Integer> numberOfCurrentUserRoutines = new MutableLiveData<>();

    private MutableLiveData<Integer> numberOfFavouritesUserRoutines = new MutableLiveData<>();

    private int favouriteUserPage = 0;

    private void addFavouriteUserPage() {
        favouriteUserPage++;
    }

    private void substractFavouriteUserPage() {
        if (favouriteUserPage > 0)
            favouriteUserPage--;
    }


    public LiveData<Integer> getNumberOfCurrentUserRoutines() {
//        numberOfCurrentUserRoutines.addSource(currentUserRoutines, listResource -> {
//            switch (listResource.getStatus()){
//                case SUCCESS:
//                    numberOfCurrentUserRoutines.setValue(listResource.getData().size());
//                    numberOfCurrentUserRoutines.removeSource(currentUserRoutines);
//                    break;
//                default:
//
////                    switchResourceStatus(listResource.getStatus(), listResource, List.class, numberOfCurrentUserRoutines);
//            }
//        });

        repository.getRoutinesNoSave(null, null, Integer.MAX_VALUE, null, null, CURRENT).observeForever(pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    Log.d("NUMBER s", "eeee " + numberOfCurrentUserRoutines.getValue());
                    numberOfCurrentUserRoutines.setValue(pagedListResource.getData().size());
                    break;
                case LOADING:
                    Log.d("NUMBER l", "eeee " + numberOfCurrentUserRoutines.getValue());

                    numberOfCurrentUserRoutines.setValue(-2);
                    break;
                case ERROR:
                    Log.d("NUMBER error", "eeee " + numberOfCurrentUserRoutines.getValue());

                    numberOfCurrentUserRoutines.setValue(-1);
                    break;
            }
        });
        Log.d("NUMBER", "routines " + currentUserRoutines.getValue().getData().size());
        return numberOfCurrentUserRoutines;
    }

    private void setFavouritesRoutines() {
        if (favouritesRoutines == null)
            favouritesRoutines = new MutableLiveData<>();
        repository.getRoutine(null, favouriteUserPage, routinesPerPage, "name", "asc", RoutinePagedListGetter.FAVOURITES)
                .observeForever(pagedListResource -> {
                    switch (pagedListResource.getStatus()) {
                        case SUCCESS:
                            Log.d("ALL", "setFavouritesRoutines: " + pagedListResource.getData().size());
                            if (pagedListResource.getData().size() == 0) {
                                List<Routine> pagedList = new ArrayList<>();
                                favouritesRoutinesList.setValue(pagedList);
                                favouritesRoutines.setValue(Resource.success(pagedList));
                            } else {
                                favouritesRoutines.setValue(Resource.success(pagedListResource.getData()));
                                favouritesRoutinesList.setValue(favouritesRoutines.getValue().getData());
                            }
                            break;
                        default:
                            switchResourceStatus(pagedListResource.getStatus(), pagedListResource, List.class, favouritesRoutines);
                    }
                });
    }

    public LiveData<Resource<List<Routine>>> getFavouritesRoutines() {
        setFavouritesRoutines();
        return favouritesRoutines;
    }

    public LiveData<Integer> getNumberOfFavouritesUserRoutines() {
        numberOfFavouritesUserRoutines.setValue(0);
        repository.getRoutinesNoSave(null, null, Integer.MAX_VALUE, null, null, FAVOURITES).observeForever(pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    numberOfFavouritesUserRoutines.setValue(pagedListResource.getData().size());
                    break;
                case LOADING:
                    numberOfFavouritesUserRoutines.setValue(-2);
                    break;
                case ERROR:
                    numberOfFavouritesUserRoutines.setValue(-1);
                    break;
            }
        });
        return numberOfFavouritesUserRoutines;
    }

    public LiveData<Resource<Void>> addRoutineToFavourites(@NonNull Integer id) {
        return repository.addToFavourites(id);
    }

    public LiveData<Resource<Void>> removeRoutineFromFavourites(@NonNull Integer id) {
        return repository.removeFromFavourites(id);
    }

    private MutableLiveData<List<Routine>> selectedRoutineList = new MutableLiveData<>();

    public void setSelectedRoutineList(List<Routine> list) {
        selectedRoutineList.setValue(list);
    }

    public MutableLiveData<List<Routine>> getCreatedRoutinesList() {
        return createdRoutinesList;
    }

    public MutableLiveData<List<Routine>> getFavouritesRoutinesList() {
        return favouritesRoutinesList;
    }


    public MutableLiveData<List<Routine>> getCompletedRoutinesList() {
        return completedRoutinesList;
    }

    public LiveData<List<Routine>> getSelectedRoutineList() {
        if (selectedRoutineList.getValue() == null) {
            selectedRoutineList.setValue(new ArrayList<>());
            Log.d("CURRENT", "SOY NULL");
        }
        return selectedRoutineList;
    }

    private MutableLiveData<List<Routine>> createdRoutinesList = new MutableLiveData<>();
    private MutableLiveData<List<Routine>> favouritesRoutinesList = new MutableLiveData<>();
    private MutableLiveData<List<Routine>> completedRoutinesList = new MutableLiveData<>();
    private MutableLiveData<Integer> numberSelectedRoutineList = new MutableLiveData<>();


}
