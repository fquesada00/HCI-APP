package ar.com.edu.itba.hci_app.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import ar.com.edu.itba.hci_app.repository.BaseRepository;

public abstract class BaseFragment<VM extends ViewModel, B extends ViewBinding, R extends BaseRepository> extends Fragment {

    protected B binding;
    protected VM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getFragmentBinding(inflater,container);
        ViewModelProvider.NewInstanceFactory factory = new ViewModelFactory(getFragmentRepository());
        viewModel = new ViewModelProvider(this,factory).get(getViewModel());
        return binding.getRoot();
    }

    public abstract Class<VM> getViewModel();

    public abstract  B getFragmentBinding(LayoutInflater inflater,ViewGroup container);

    public abstract R getFragmentRepository();
}
