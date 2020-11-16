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

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentHomeBinding;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;
import ar.com.edu.itba.hci_app.ui.base.ViewModelFactory;
import ar.com.edu.itba.hci_app.ui.routine.DisplayRoutineActivity;

public class HomeFragment extends BaseFragment<HomeFragmentViewModel, FragmentHomeBinding, UserRepository> {


    private static HomeFragment homeFragment;

    public static HomeFragment getHomeFragment(){
        if(homeFragment == null){
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    private HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.button1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DisplayRoutineActivity.class);
            intent.putExtra("color", 1);
            startActivity(intent);
        });
        binding.button2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DisplayRoutineActivity.class);
            intent.putExtra("color", 2);
            startActivity(intent);
        });

    }

    @Override
    public Class<HomeFragmentViewModel> getViewModel() {
        return HomeFragmentViewModel.class;
    }

    @Override
    public FragmentHomeBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater,container,false);
    }

    @Override
    public UserRepository getFragmentRepository() {
        return BaseRepository.getUserRepository(getContext());
    }
}