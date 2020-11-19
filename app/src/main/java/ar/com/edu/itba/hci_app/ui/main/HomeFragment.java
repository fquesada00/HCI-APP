package ar.com.edu.itba.hci_app.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentHomeBinding;
import ar.com.edu.itba.hci_app.domain.Category;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;
import ar.com.edu.itba.hci_app.ui.base.ViewModelFactory;
import ar.com.edu.itba.hci_app.ui.routine.DisplayRoutineActivity;

public class HomeFragment extends BaseFragment<MainActivityViewModel, FragmentHomeBinding, RoutineRepository> {


    private static HomeFragment homeFragment;
    private View view;
    private RecyclerView homeRecyclerView;
    private List<Routine> list;
    private ListRoutineHomeAdapter listHomeAdapter;

    private RecyclerView recommendedRecyclerView;
    private List<Routine> recommendedList;
    private ListRoutineHomeAdapter listRecommendedAdapter;



    public static HomeFragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
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
                Log.d("LOADING", "HOME FRAGMENT");
                displayMessage("Loading");
                break;
            case ERROR:
                displayMessage("Error");
                break;
            default:
                unknownStatusException();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeFragment = this;
        binding.imageView.setOnClickListener(v -> {
            Toast.makeText(getContext(), "DUFFY", Toast.LENGTH_SHORT);
            Log.d("DUFFY", "eeeeeeeeee");
        });

        Log.d("LOADING", "BEFORE");

        viewModel.getDailyRoutine().observe(requireActivity(), routineResource -> {
            switch (routineResource.getStatus()) {
                case SUCCESS:
                    Log.d("LOADING", "OBSERVE GET DAILY ROUTINE");
                    binding.textView7.setText(routineResource.getData().getName() == null ? "NULL" : routineResource.getData().getName());
                    break;
                default:
                    switchResourceStatus(routineResource.getStatus());
            }
        });

        Log.d("LOADING", "BEFORE 2");
        viewModel.getCurrentUserRoutines().observe(requireActivity(), pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
//                    binding.textView8.setText(pagedListResource.getData().getResults().get(0).getName());
                    for (int i = 0; i < pagedListResource.getData().size(); i++) {
                        Log.d("LOADING", "routines" + pagedListResource.getData().get(i).getName());
                    }
                    break;
                default:
                    switchResourceStatus(pagedListResource.getStatus());
            }
        });

        viewModel.getPopularRoutines().observe(requireActivity(), pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    break;
                default:
                    switchResourceStatus(pagedListResource.getStatus());
            }
        });

        viewModel.getDifficultyRoutines().observe(requireActivity(), pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    break;
                default:
                    switchResourceStatus(pagedListResource.getStatus());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        homeRecyclerView = view.findViewById(R.id.home_routine_recycler_view);
        list = new ArrayList<>();
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        list.add(new Routine("BAJA", null, null, 3.0, "BAJA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("BAJA", null, null, 3.0, "BAJA", true, 0, null, null));

        listHomeAdapter = new ListRoutineHomeAdapter(list,getContext());
        homeRecyclerView.setAdapter(listHomeAdapter);

        recommendedRecyclerView = view.findViewById(R.id.home_routine_recommended_recycler_view);
        recommendedList = new ArrayList<>();
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recommendedList.add(new Routine("BAJA1", null, null, 3.0, "BAJA", true, 0, null, null));
        recommendedList.add(new Routine("ALTA2", null, null, 3.0, "ALTA", true, 0, null, null));
        recommendedList.add(new Routine("MEDIO3", null, null, 3.0, "MEDIO", true, 0, null, null));
        listRecommendedAdapter = new ListRoutineHomeAdapter(recommendedList,getContext());
        recommendedRecyclerView.setAdapter(listRecommendedAdapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        homeFragment = this;
//        binding.imageView.setOnClickListener(v -> {
//            Toast.makeText(getContext(), "DUFFY", Toast.LENGTH_SHORT);
//            Log.d("DUFFY", "eeeeeeeeee");
//        });
//
//        viewModel.getDailyRoutine().observe(requireActivity(), routineResource -> {
//            switch (routineResource.getStatus()) {
//                case SUCCESS:
//                    binding.textView7.setText(routineResource.getData().getName() == null ? "NULL" : routineResource.getData().getName());
//                    break;
//                default:
//                    switchResourceStatus(routineResource.getStatus());
//            }
//        });
//
//        viewModel.getCurrentUserRoutines().observe(requireActivity(), pagedListResource -> {
//            switch (pagedListResource.getStatus()) {
//                case SUCCESS:
//                    binding.textView8.setText(pagedListResource.getData().getResults().get(0).getName());
//                    break;
//                default:
//                    switchResourceStatus(pagedListResource.getStatus());
//            }
//        });
//
//        viewModel.getPopularRoutines().observe(requireActivity(), pagedListResource -> {
//            switch (pagedListResource.getStatus()) {
//                case SUCCESS:
//                    binding.button10.setBackgroundColor(Color.RED);
//                    break;
//                default:
//                    switchResourceStatus(pagedListResource.getStatus());
//            }
//        });
//
//        viewModel.getDifficultyRoutines().observe(requireActivity(), pagedListResource -> {
//            switch (pagedListResource.getStatus()){
//                case SUCCESS:
//                    binding.button20.setBackgroundColor(Color.BLUE);
//                    break;
//                default:
//                    switchResourceStatus(pagedListResource.getStatus());
//            }
//        });
    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentHomeBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }
}