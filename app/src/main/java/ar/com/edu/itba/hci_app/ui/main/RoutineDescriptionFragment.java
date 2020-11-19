package ar.com.edu.itba.hci_app.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentProfileConfigurationBinding;
import ar.com.edu.itba.hci_app.databinding.FragmentRoutineDescriptionBinding;
import ar.com.edu.itba.hci_app.databinding.FragmentSearchBinding;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class RoutineDescriptionFragment extends BaseFragment<MainActivityViewModel, FragmentRoutineDescriptionBinding, RoutineRepository> {


    public RoutineDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routine_description, container, false);
    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentRoutineDescriptionBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRoutineDescriptionBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }
}