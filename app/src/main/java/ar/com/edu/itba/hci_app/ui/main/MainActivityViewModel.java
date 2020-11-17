package ar.com.edu.itba.hci_app.ui.main;

import androidx.lifecycle.ViewModel;

import ar.com.edu.itba.hci_app.repository.RoutineRepository;

public class MainActivityViewModel extends ViewModel {

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

    public MainActivityViewModel(RoutineRepository repository) {
        this.repository = repository;
    }
}
