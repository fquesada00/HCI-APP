package ar.com.edu.itba.hci_app.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentProfileConfigurationBinding;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;
import ar.com.edu.itba.hci_app.ui.routine.DisplayRoutineActivity;

public class ProfileConfigurationFragment extends BaseFragment<MainActivityViewModel, FragmentProfileConfigurationBinding, RoutineRepository> {


    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentProfileConfigurationBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileConfigurationBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        return BaseRepository.getRoutineRepository(getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.button2.setOnClickListener(v -> {
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoutineDescriptionFragment()).addToBackStack(null).commit();
//            getActivity().getSupportFragmentManager().executePendingTransactions();
            getActivity().startActivity(new Intent(getActivity(), DisplayRoutineActivity.class));
        });
    }
}