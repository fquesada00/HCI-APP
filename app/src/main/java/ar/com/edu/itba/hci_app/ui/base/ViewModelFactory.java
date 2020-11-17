package ar.com.edu.itba.hci_app.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.auth.AuthViewModel;
import ar.com.edu.itba.hci_app.ui.main.MainActivityViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private BaseRepository repository;
    private Application application;

    public ViewModelFactory(BaseRepository repository, Application application) {
        this.repository = repository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!modelClass.isAssignableFrom(AuthViewModel.class) && !modelClass.isAssignableFrom(MainActivityViewModel.class))
            throw new IllegalArgumentException("ViewModel Class Not found");
        //TODO agregar repositorios correspondientes
        if (modelClass.isAssignableFrom(MainActivityViewModel.class))
            return (T) new MainActivityViewModel(application, (RoutineRepository) repository);

        return (T) new AuthViewModel(application,(UserRepository) repository);


    }
}
