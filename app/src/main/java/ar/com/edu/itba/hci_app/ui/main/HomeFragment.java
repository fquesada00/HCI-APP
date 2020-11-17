package ar.com.edu.itba.hci_app.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentHomeBinding;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;
import ar.com.edu.itba.hci_app.ui.base.ViewModelFactory;
import ar.com.edu.itba.hci_app.ui.routine.DisplayRoutineActivity;

public class HomeFragment extends BaseFragment<MainActivityViewModel, FragmentHomeBinding, RoutineRepository> {


    private static HomeFragment homeFragment;

    public static HomeFragment getHomeFragment(){
        if(homeFragment == null){
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeFragment = this;
        viewModel.getDailyRoutine().observe(requireActivity(),routineResource -> {
            switch (routineResource.getStatus()){
                case SUCCESS:
                    binding.textView7.setText(routineResource.getData().getName());
                    break;
                case LOADING:
                    Toast.makeText(getContext(),"Loading", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getContext(),"ERROR", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentHomeBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater,container,false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        return BaseRepository.getRoutineRepository(getContext());
    }
}