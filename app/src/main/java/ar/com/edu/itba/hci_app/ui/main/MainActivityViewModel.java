package ar.com.edu.itba.hci_app.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import ar.com.edu.itba.hci_app.domain.Category;
import ar.com.edu.itba.hci_app.domain.Cycle;
import ar.com.edu.itba.hci_app.domain.Exercise;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.Status;

import ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter;
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
//        switch (status) {
//            case LOADING:
//                Log.d("LOADING", "vm");
//                try {
//                    mutableLiveData.setValue(Resource.loading(c.getConstructor().newInstance()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case ERROR:
//                try {
//                    mutableLiveData.setValue(Resource.error(resource.getError(), c.getConstructor().newInstance()));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            default:
//                unknownStatusException();
//        }
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
        Log.d("LOADING", "GET DAILY ROUTINE");
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

    public void setHomeFragmentRoutinesPerPage(int integer) {
        homeFragmentRoutinesPerPage = integer;
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


    //TODO getcategories
    private void setCategories() {
        if (categories == null)
            categories = new MutableLiveData<>();
        repository.getCategories(null, categoriesPerPage, "name", "asc")
                .observeForever(pagedListResource -> {
                    switch (pagedListResource.getStatus()) {
                        case SUCCESS:
                            if (pagedListResource.getData().size() == 0) {
                                List<Category> pagedList = new ArrayList<>();
                                categories.setValue(Resource.success(pagedList));
                            } else
                                categories.setValue(Resource.success(pagedListResource.getData()));
                            break;
                        default:
                            switchResourceStatus(pagedListResource.getStatus(), pagedListResource, List.class, categories);
                    }
                });
    }

    public LiveData<Resource<List<Category>>> getCategories() {
        setCategories();
        return categories;
    }

    public LiveData<Resource<Category>> getCategoryById(@NonNull Integer id) {
        return repository.getCategoryById(id);
    }


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
        currentUserRoutines.observeForever(pagedListResource -> {
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
        favouritesRoutines.observeForever(pagedListResource -> {
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

    public MutableLiveData<List<Exercise>> getCalentamientoList() {
        return calentamientoList;
    }

    public void setCalentamientoList(List<Exercise> calentamientoList) {
        if (this.calentamientoList.getValue() != null)
            this.calentamientoList.getValue().clear();
        this.calentamientoList.setValue(calentamientoList);
        Log.d("VISTA", "Calentamiento size: " + this.calentamientoList.getValue().size());
    }

    public MutableLiveData<List<Exercise>> getPrincipalList() {
        return principalList;
    }

    public void setPrincipalList(List<Exercise> principalList) {
        if (this.principalList.getValue() != null)
            this.principalList.getValue().clear();
        this.principalList.setValue(principalList);
        Log.d("VISTA", "Principal size " + this.principalList.getValue().size());
    }

    public MutableLiveData<List<Exercise>> getEnfriamientoList() {
        return enfriamientoList;
    }

    public void setEnfriamientoList(List<Exercise> enfriamientoList) {
        if (this.enfriamientoList.getValue() != null)
            this.enfriamientoList.getValue().clear();
        this.enfriamientoList.setValue(enfriamientoList);
        Log.d("VISTA", "Enfriamiento list " + this.enfriamientoList.getValue().size());
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

    private MutableLiveData<List<Exercise>> calentamientoList = new MutableLiveData<>();
    private MutableLiveData<List<Exercise>> principalList = new MutableLiveData<>();
    private MutableLiveData<List<Exercise>> enfriamientoList = new MutableLiveData<>();

    public LiveData<Resource<Exercise>> getExerciseById(@NonNull Integer routineID, @NonNull Integer cycleID, @NonNull Integer exerciseID) {
        return repository.getExerciseByID(routineID, cycleID, exerciseID);
    }

    public LiveData<Resource<Cycle>> getCycleById(@NonNull Integer routineID, @NonNull Integer cycleID) {
        return repository.getCycleByID(routineID, cycleID);
    }

    public LiveData<Resource<Routine>> getRoutineById(@NonNull Integer routineID) {
        return repository.getRoutineByID(routineID);
    }

    private MutableLiveData<Resource<List<Exercise>>> cycleExercises = new MutableLiveData<>();

    public LiveData<Resource<List<Exercise>>> getCycleExercises(@NonNull Integer routineID, @NonNull Integer cycleID, @Nullable Integer page,
                                                                @Nullable Integer size, @Nullable String orderBy,
                                                                @Nullable String direction) {
        repository.getCycleExercises(routineID, cycleID, page, size, orderBy, direction).observeForever(v -> {
            switch (v.getStatus()) {
                case SUCCESS:
                    if (v.getData().size() == 0) {
                        List<Exercise> list = new ArrayList<>();
                        cycleExercises.setValue(Resource.success(list));
                    } else
                        cycleExercises.setValue(Resource.success(v.getData()));
                    break;
                default:
                    switchResourceStatus(v.getStatus(), v, List.class, cycleExercises);
            }
        });

        return cycleExercises;
    }

    private MutableLiveData<Resource<List<Cycle>>> cycles = new MutableLiveData<>();

    public LiveData<Resource<List<Cycle>>> getRoutineCycles(@NonNull Integer routineID, @Nullable String difficulty, @Nullable Integer page,
                                                            @Nullable Integer size, @Nullable String orderBy,
                                                            @Nullable String direction) {
        repository.getRoutineCycles(routineID, difficulty, page, size, orderBy, direction).observeForever(v -> {
            switch (v.getStatus()) {
                case SUCCESS:
                    if (v.getData().size() == 0)
                        cycles.setValue(Resource.success(new ArrayList<>()));
                    else
                        cycles.setValue(Resource.success(v.getData()));
                    break;
                default:
                    switchResourceStatus(v.getStatus(), v, List.class, cycles);
            }
        });

        return cycles;
    }

    private MutableLiveData<Resource<List<List<Exercise>>>> routineExercises = new MutableLiveData<>();

    private MutableLiveData<Resource<List<Cycle>>> routineCycles = new MutableLiveData<>();

    private Map<String, Integer> basicsIds = new HashMap<>();

    private MutableLiveData<Resource<Map<Integer, List<Exercise>>>> routineExercisesMap = new MutableLiveData<>();

    private Map<Integer, Exercise> cycleSection = new HashMap<>();

    private final static String CALENTAMIENTO = "Calentamiento";

    private final static String ENFRIAMIENTO = "Enfriamiento";

    private void setRoutineExercises(@NonNull Integer routineID, @Nullable String difficulty, @Nullable Integer page,
                                     @Nullable Integer size, @Nullable String orderBy,
                                     @Nullable String direction) {


        this.getRoutineCycles(routineID, difficulty, page, size, orderBy, direction).observeForever(v -> {
            switch (v.getStatus()) {
                case SUCCESS:
                    routineCycles.setValue(Resource.success(new ArrayList<>()));
                    routineExercises.setValue(Resource.success(new ArrayList<>()));
                    routineExercisesMap.setValue(Resource.success(new HashMap<>()));
                    for (int i = 0; i < v.getData().size(); i++) { //list[0] calentamient, list[1] principallist, list[2] enfriamiento
                        if (v.getData().get(i).getName().equals(ENFRIAMIENTO))
                            basicsIds.put(ENFRIAMIENTO, v.getData().get(i).getId());
                        else if (v.getData().get(i).getName().equals(CALENTAMIENTO))
                            basicsIds.put(CALENTAMIENTO, v.getData().get(i).getId());
                        routineExercisesMap.getValue().getData().put(v.getData().get(i).getId(), new ArrayList<>());
                        routineExercises.getValue().getData().add(new ArrayList<>());
                        routineCycles.getValue().getData().add(v.getData().get(i));
                        if (basicsIds.getOrDefault(ENFRIAMIENTO, -1) != v.getData().get(i).getId() && basicsIds.getOrDefault(CALENTAMIENTO, -1) != v.getData().get(i).getId())
                            cycleSection.put(v.getData().get(i).getId(), new Exercise(1, v.getData().get(i).getName(), -1, null
                                    , null, v.getData().get(i).getRepetitions(), -1, v.getData().get(i).getId()));
//                            routineExercisesMap.getValue().getData().get(v.getData().get(i).getId()).add(new Exercise(1, v.getData().get(i).getName(), -1, null
//                                    , null, v.getData().get(i).getRepetitions(), -1, v.getData().get(i).getId()));

//                        if(i > 0 && i < (v.getData().size() - 1))
//                            routineExercises.getValue().getData().get(i).add(new Exercise(1, v.getData().get(pos).getName(), -1, null, null, v.getData().get(pos).getRepetitions(),
//                                0, v.getData().get(i).getId()));
                        this.getCycleExercises(routineID, v.getData().get(i).getId(), page, size, orderBy, direction).observeForever(b -> {
                            switch (b.getStatus()) {
                                case SUCCESS:
//                                    Log.d("SIZE", "cycleId: " + b.getData().get(0).getCycleId());
//                                    for (int h = 0; h < b.getData().size(); h++)
//                                        Log.d("SIZE", "Exid= " + b.getData().get(h).getId());
                                    if (b.getData() == null || b.getData().size() == 0)
                                        throw new IllegalArgumentException("Not enough exercises");
//                                    routineExercises.getValue().getData().get(b.getData().get(0).getCycleId() - 1).clear();
                                    routineExercisesMap.getValue().getData().get(b.getData().get(0).getCycleId()).clear();
                                    if (basicsIds.get(ENFRIAMIENTO) != b.getData().get(0).getCycleId() &&
                                            basicsIds.get(CALENTAMIENTO) != b.getData().get(0).getCycleId())
                                        routineExercisesMap.getValue().getData().get(b.getData().get(0).getCycleId()).add(cycleSection.get(b.getData().get(0).getCycleId()));
                                    routineExercisesMap.getValue().getData().get(b.getData().get(0).getCycleId()).addAll(b.getData());
//                                    routineExercises.getValue().getData().get(b.getData().get(0).getCycleId() - 1).addAll(b.getData());
//                                    if(pos > 0 && pos < (sizeCycles - 1)){
//                                        if(routineExercises.getValue().getData().get(pos).size() < b.getData().size()){
//                                            routineExercises.getValue().getData().get(pos).clear();
//                                            routineExercises.getValue().getData().get(pos).add(new Exercise(1, v.getData().get(pos).getName(), -1, null, null, v.getData().get(pos).getRepetitions(), 0));
//                                            routineExercises.getValue().getData().get(pos).addAll(b.getData());
//                                        }
//                                    }
//                                    routineExercises.getValue().getData().get(pos).clear();
//                                    if (pos != 0 && pos < (sizeCycles - 1))
//                                        routineExercises.getValue().getData().get(pos).add(new Exercise(1, v.getData().get(pos).getName(), -1, null, null, v.getData().get(pos).getRepetitions(), 0));
//                                    routineExercises.getValue().getData().get(pos).addAll(b.getData());

//                                    Log.d("SIZE", "cycleid: " + v.getData().get(pos).getId() + "tamaño: " + b.getData().size() + " SECCION " + v.getData().get(pos).getName());
//                                    for (int k = 0; k < b.getData().size(); k++)
//                                        Log.d("SIZE 2", "name: " + b.getData().get(k).getName() + " ex Id: " + b.getData().get(k).getId());
                                    break;
                                default:
                                    //TODO
                            }
                        });
                    }
                    for (int i = 0; i < routineExercises.getValue().getData().size(); i++) {

                        for (int j = 0; j < routineExercises.getValue().getData().get(i).size(); j++) {
                            Log.d("GOOD MORNING", "Ejercicio: " + routineExercises.getValue().getData().get(i).get(j).getName()
                                    + " Category: " + routineExercises.getValue().getData().get(i).get(j).getId());
                        }
                    }
                    break;
                default:
                    //TODO
            }
        });
    }

    private Integer lastRoutineId = -1;

    public MutableLiveData<Resource<List<List<Exercise>>>> getRoutineExercises(@NonNull Integer routineID, @Nullable String difficulty, @Nullable Integer page,
                                                                               @Nullable Integer size, @Nullable String orderBy,
                                                                               @Nullable String direction) {

        if(lastRoutineId == routineID) {
            for(int i = 0 ; i < routineExercises.getValue().getData().size() ; i++)
                Log.d("LISTA","ee "+routineExercises.getValue().getData().get(i).size());
            return routineExercises;
        }
//        if(lastRoutineId != -1){
////            for (int i = 0 ; i < routineExercises.getValue().getData().size() ; i++)
////                routineExercises.getValue().getData().get(i).clear();
//            routineExercises.getValue().getData().clear();
//            routineExercisesMap.getValue().getData().clear();
//        }
        lastRoutineId = routineID;

        if(routineID != lastRoutineId)
            setRoutineExercises(routineID, difficulty, page, size, orderBy, direction);
//        if (routineExercisesMap.getValue() == null || routineExercisesMap.getValue().getData() == null || routineExercises.getValue().getData().isEmpty() || routineExercisesMap.getValue().getData().isEmpty()) {
//            setRoutineExercises(routineID, difficulty, page, size, orderBy, direction);
//        }
//
//
//
//        if (routineExercises.getValue() == null || routineExercises.getValue().getData() == null /*|| routineExercises.getValue().getData().get(0) == null*/) {
//            Log.d("GOOD MORNING", "2");
//
//            setRoutineExercises(routineID, difficulty, page, size, orderBy, direction);
//        }

        int i = 0;
        if (routineExercisesMap.getValue() != null && routineExercisesMap.getValue().getData() != null && !routineExercisesMap.getValue().getData().isEmpty()) {
            for (Map.Entry<Integer, List<Exercise>> entry : routineExercisesMap.getValue().getData().entrySet()) {
                routineExercises.getValue().getData().get(i++).addAll(entry.getValue());
            }
        }

        return routineExercises;
    }


    //livedata<resource<list<list<exercise>>>>

    public List<Cycle> getRoutineCycles() {
        return routineCycles.getValue().getData();
    }

}
