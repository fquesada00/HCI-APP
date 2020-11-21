package ar.com.edu.itba.hci_app.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
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


    public MainActivityViewModel(Application app){
        super(app);

    }

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
        if(this.principalList.getValue() == null)
            this.principalList.setValue(new ArrayList<>());
        List<Exercise> list = this.principalList.getValue();
        list.addAll(principalList);
        this.principalList.setValue(list);
//        if (this.principalList.getValue() != null)
//            this.principalList.getValue().clear();
//        this.principalList.setValue(principalList);
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
                    } else {
                        Log.d("COUNTER", "EN LISTA DE EJS" + " cyckleid: " + cycleID);
                        cycleExercises.setValue(Resource.success(v.getData()));
                    }

                    break;
                default:
                    //switchResourceStatus(v.getStatus(), v, List.class, cycleExercises);
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
                    else {
                        cycles.setValue(Resource.success(v.getData()));
                        Log.d("COUNTER", "EN LISTA DE CYCLES routineid: " + routineID);
                    }
                    break;
                default:
                    //switchResourceStatus(v.getStatus(), v, List.class, cycles);
            }
        });

        return cycles;
    }

    private MutableLiveData<Resource<List<List<Exercise>>>> routineExercises = new MutableLiveData<>();

//    private MutableLiveData<Resource<List<Cycle>>> routineCycles = new MutableLiveData<>();

    private Map<String, Integer> basicsIds = new HashMap<>();

    private MutableLiveData<Resource<Map<Integer, List<Exercise>>>> routineExercisesMap = new MutableLiveData<>();

    private Resource<Map<Integer, List<Exercise>>> auxmap;

    private Map<Integer, Exercise> cycleSection = new HashMap<>();

    private final static String CALENTAMIENTO = "Calentamiento";

    private final static String ENFRIAMIENTO = "Enfriamiento";

    public MutableLiveData<Resource<List<List<Exercise>>>> temp = new MutableLiveData<>();

    private int cyclecounter;

    private void setRoutineExercises(@NonNull Integer routineID, @Nullable String difficulty, @Nullable Integer page,
                                     @Nullable Integer size, @Nullable String orderBy,
                                     @Nullable String direction) {


        this.getRoutineCycles(routineID, difficulty, page, size, orderBy, direction).observeForever(v -> {
            switch (v.getStatus()) {
                case SUCCESS:
                    cyclecounter = 0;
                    counter.setValue(0);
                    for (int i = 0; i < v.getData().size(); i++) {
                        cyclecounter++;
                        if (v.getData().get(i).getName().equals(ENFRIAMIENTO))
                            basicsIds.put(ENFRIAMIENTO, v.getData().get(i).getId());
                        else if (v.getData().get(i).getName().equals(CALENTAMIENTO))
                            basicsIds.put(CALENTAMIENTO, v.getData().get(i).getId());
                        auxmap.getData().putIfAbsent(v.getData().get(i).getId(), new ArrayList<>());
                        if (basicsIds.getOrDefault(ENFRIAMIENTO, -1) != v.getData().get(i).getId() && basicsIds.getOrDefault(CALENTAMIENTO, -1) != v.getData().get(i).getId())
                            cycleSection.put(v.getData().get(i).getId(), new Exercise(1, v.getData().get(i).getName(), -1, null
                                    , null, v.getData().get(i).getRepetitions(), -1, v.getData().get(i).getId()));
                        this.getCycleExercises(routineID, v.getData().get(i).getId(), page, size, orderBy, direction).observeForever(b -> {
                            switch (b.getStatus()) {
                                case SUCCESS:
//                                    Log.d("CORTA","id: "+b.getData().get(0).getCycleId());
                                    if (b.getData() == null || b.getData().size() == 0 || auxmap.getData().get(b.getData().get(0).getCycleId()) == null)
                                        return;
                                    if (!auxmap.getData().get(b.getData().get(0).getCycleId()).isEmpty())
                                        return;
//                                    auxmap.getData().get(b.getData().get(0).getCycleId()).clear();
                                    if (basicsIds.getOrDefault(ENFRIAMIENTO, -2) != b.getData().get(0).getCycleId() &&
                                            basicsIds.getOrDefault(CALENTAMIENTO,-2) != b.getData().get(0).getCycleId())
                                        auxmap.getData().get(b.getData().get(0).getCycleId()).add(cycleSection.get(b.getData().get(0).getCycleId()));
                                    auxmap.getData().get(b.getData().get(0).getCycleId()).addAll(b.getData());

                                    counter.setValue(counterR.getValue().incrementAndGet());
                                    Log.d("COUNTER", "EN OBSERVER");
                                    Log.d("MAPA", "AUX MAP: " + auxmap.getData().toString());

                                    routineExercisesMap.postValue(auxmap);
                                    break;
                                default:
                                    //TODO
                            }
                        });
                    }
                    break;
                default:
                    //TODO
            }
        });
    }

    private Integer lastRoutineId = -1;

    public void resetTemp(LifecycleOwner lifecycleOwner){
        temp.removeObservers(lifecycleOwner);
        routineExercises.removeObservers(lifecycleOwner);
        routineExercisesMap.removeObservers(lifecycleOwner);
        counter.removeObservers(lifecycleOwner);
        temp = new MutableLiveData<>();
        routineExercisesMap = new MutableLiveData<>();
        routineExercises = new MutableLiveData<>();
        auxmap = Resource.success(new HashMap<>());
        counter = new MutableLiveData<>(0);
        counterR.setValue(new AtomicInteger(0));
        Log.d("RESET TEMP","e: "+counter.getValue());
    }

    public MutableLiveData<Resource<List<List<Exercise>>>> getRoutineExercises(@NonNull Integer routineID, @Nullable String difficulty, @Nullable Integer page,
                                                                               @Nullable Integer size, @Nullable String orderBy,
                                                                               @Nullable String direction) {

        routineExercisesMap.setValue(Resource.success(new HashMap<>()));
        auxmap = Resource.success(new HashMap<>());
        setRoutineExercises(routineID, difficulty, page, size, orderBy, direction);
        routineExercisesMap.observeForever(v -> {
            switch (v.getStatus()) {
                case SUCCESS:
                    routineExercises.setValue(Resource.success(new ArrayList<>()));
                    Log.d("CONDICION", "e: "+v.getData().isEmpty() +" 2da: "+counterR.getValue().get() + " otro val: "+cyclecounter);
                    if (!v.getData().isEmpty() && counterR.getValue().get() == cyclecounter) {
                        Log.d("MAPA", "MAPA: " + routineExercisesMap.getValue().getData().toString());
                        int i = 0;

                        //TODO ORDENAR CALENTAMIENTO -> PPCAL -> ENFRIAMIENTO
                        for (Map.Entry<Integer, List<Exercise>> entry : routineExercisesMap.getValue().getData().entrySet()) {
                            routineExercises.getValue().getData().add(i, new ArrayList<>());
                            routineExercises.getValue().getData().get(i++).addAll(entry.getValue());
                            Log.d("LISTA DE EJS","LIST: "+entry.getValue().toString());
                        }
                        for (int j = 0; j < routineExercises.getValue().getData().size(); j++) {
                            Log.d("ITEM", "CYCLE ID: " + routineExercises.getValue().getData().get(j).get(0).getCycleId());
                            for (int k = 0; k < routineExercises.getValue().getData().get(j).size(); k++)
                                Log.d("ITEM LIST", "ELEMENTO: " + routineExercises.getValue().getData().get(j).get(k).getId());
                        }
                        counterR.setValue(new AtomicInteger(0));
                        routineExercisesMap.setValue(Resource.success(new HashMap<>()));
                        auxmap = Resource.success(new HashMap<>());
                        temp.postValue(routineExercises.getValue());
                    }
                    break;
                default:
            }
        });
        return routineExercises;
    }

    private MutableLiveData<AtomicInteger> counterR = new MutableLiveData<>(new AtomicInteger(0));

    private MutableLiveData<Integer> counter = new MutableLiveData<>(0);

    private MutableLiveData<Integer> isLastRoutine = new MutableLiveData<>();

    public LiveData<Integer> getIsLastRoutine() {
        return isLastRoutine;
    }

//    public List<Cycle> getRoutineCycles() {
//        return routineCycles.getValue().getData();
//    }

}
