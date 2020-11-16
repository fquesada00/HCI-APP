package ar.com.edu.itba.hci_app.ui.base;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.auth.AuthViewModel;
import ar.com.edu.itba.hci_app.ui.main.HomeFragmentViewModel;
import ar.com.edu.itba.hci_app.ui.main.MainActivityViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private BaseRepository repository;

    public ViewModelFactory(BaseRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (!modelClass.isAssignableFrom(AuthViewModel.class) && !modelClass.isAssignableFrom(MainActivityViewModel.class) && !modelClass.isAssignableFrom(HomeFragmentViewModel.class))
            throw new IllegalArgumentException("ViewModel Class Not found");
        //TODO agregar repositorios correspondientes
        if (modelClass.isAssignableFrom(MainActivityViewModel.class))
            return (T) new MainActivityViewModel();
        if(modelClass.isAssignableFrom(HomeFragmentViewModel.class))
            return (T) new HomeFragmentViewModel();
        return (T) new AuthViewModel((UserRepository) repository);


    }
}
