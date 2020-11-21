package ar.com.edu.itba.hci_app.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentProfileBinding;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class ProfileFragment extends BaseFragment<MainActivityViewModel, FragmentProfileBinding, RoutineRepository> {

    private static ProfileFragment profileFragment;

    public static ProfileFragment getProfileFragment() {
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
        }
        return profileFragment;
    }

    private ProfileFragment() {
        // Required empty public constructor
    }

    private void unknownStatusException() {
        throw new IllegalArgumentException("Unkown resource status");
    }

    private void displayMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
    }

    private void switchResourceStatus(Status status) {
        switch (status) {
            case LOADING:
                loadingScreen(ProgressBar.VISIBLE, false);
                break;
            case ERROR:
                displayMessage("Error");
                break;
            default:
                unknownStatusException();
        }
    }

    private final void loadingScreen(int progressBarStatus, boolean btnEnable) {
        binding.progressBar.setVisibility(progressBarStatus);
        binding.favouritesRoutines.setEnabled(btnEnable);
        binding.createdRoutines.setEnabled(btnEnable);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getCurrentUserRoutines().observe(requireActivity(), pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    loadingScreen(ProgressBar.GONE, true);
                    break;
                default:
                    switchResourceStatus(pagedListResource.getStatus());
            }
        });

        viewModel.getFavouritesRoutines().observe(requireActivity(), pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    loadingScreen(ProgressBar.GONE, true);
                    break;
                default:
                    switchResourceStatus(pagedListResource.getStatus());
            }
        });

        viewModel.getNumberOfCurrentUserRoutines().observe(requireActivity(), n -> {
            if (n >= 0) {
                loadingScreen(ProgressBar.GONE, true);
                binding.createdRoutines.setText(n.toString() + " Rutinas creadas");
            } else
                switchResourceStatus(n == -1 ? Status.ERROR : Status.LOADING);
        });

        viewModel.getNumberOfFavouritesUserRoutines().observe(requireActivity(), n -> {
            if (n >= 0) {
                loadingScreen(ProgressBar.GONE, true);
                binding.favouritesRoutines.setText(n.toString() + " Rutinas favoritas");
            } else
                switchResourceStatus(n == -1 ? Status.ERROR : Status.LOADING);
        });

        binding.createdRoutines.setOnClickListener(v -> {
            viewModel.setSelectedRoutineList(viewModel.getCreatedRoutinesList().getValue());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DisplayRoutinesFragment()).addToBackStack(null).commit();
        });

        binding.favouritesRoutines.setOnClickListener(v -> {
            viewModel.setSelectedRoutineList(viewModel.getFavouritesRoutinesList().getValue());
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DisplayRoutinesFragment()).addToBackStack(null).commit();
        });

        binding.configuration.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).addToBackStack(null).commit();
        });

        //TODO falta el de completadas
    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentProfileBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }
}