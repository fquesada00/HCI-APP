package ar.com.edu.itba.hci_app.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.databinding.FragmentRoutineDescriptionBinding;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;
import ar.com.edu.itba.hci_app.ui.routine.DisplayRoutineActivity;


public class RoutineDescriptionFragment extends BaseFragment<MainActivityViewModel, FragmentRoutineDescriptionBinding, RoutineRepository> {


    public RoutineDescriptionFragment() {
        // Required empty public constructor

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        binding.comenzar.setOnClickListener(v -> {
//            startActivity(new Intent(this.getActivity(),DisplayRoutineActivity.class));
//        });
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