package ar.com.edu.itba.hci_app.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.api.model.RoutineModel;
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

    private MutableLiveData<Resource<Routine>> dailyRoutine;


    public MainActivityViewModel(Application application,RoutineRepository repository) {
        super(application);
        this.repository = repository;
    }

    private void setDailyRoutine(){
        if(dailyRoutine == null)
            dailyRoutine = new MutableLiveData<>();
        repository.getRoutine(null,0,1,"dateCreated","desc",RoutinePagedListGetter.ALL)
                .observeForever(pagedListResource -> {
                    switch (pagedListResource.getStatus()){
                        case SUCCESS:
                            if(pagedListResource.getData().size() > 0)
                                dailyRoutine.setValue(Resource.success(pagedListResource.getData().get(0)));
                            else
                                dailyRoutine.setValue(Resource.success(new Routine()));
                            break;
                        case LOADING:
                            dailyRoutine.setValue(Resource.loading(new Routine()));
                            break;
                        case ERROR:
                            dailyRoutine.setValue(Resource.error(pagedListResource.getError(),new Routine()));
                            break;
                    }
                });
    }

    public LiveData<Resource<Routine>> getDailyRoutine(){
        setDailyRoutine();
        return dailyRoutine;
    }
}
