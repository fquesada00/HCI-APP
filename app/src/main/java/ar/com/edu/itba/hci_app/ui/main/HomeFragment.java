package ar.com.edu.itba.hci_app.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentHomeBinding;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.domain.RoutineContainer;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.adapters.ListRoutineHomeAdapter;
import ar.com.edu.itba.hci_app.ui.adapters.RoutineAdapterListener;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;

public class HomeFragment extends BaseFragment<MainActivityViewModel, FragmentHomeBinding, RoutineRepository> {

    private static HomeFragment homeFragment;

    private View view;

    private RecyclerView homeRecyclerView;
    private List<Routine> list;
    private ListRoutineHomeAdapter listHomeAdapter;

    private RecyclerView recommendedRecyclerView;
    private List<Routine> recommendedList;
    private ListRoutineHomeAdapter listRecommendedAdapter;

    private static final int ROUTINES_TO_DISPLAY = 5;

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
        Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT);
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

        viewModel.setHomeFragmentRoutinesPerPage(ROUTINES_TO_DISPLAY);

        binding.imageView.setOnClickListener(v -> {

        });

        binding.imageView2.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DisplayRoutinesFragment()).addToBackStack(null).commit();
        });

        viewModel.getDailyRoutine().observe(requireActivity(), routineResource -> {
            switch (routineResource.getStatus()) {
                case SUCCESS:
                    break;
                default:
                    switchResourceStatus(routineResource.getStatus());
            }
        });

        viewModel.getCurrentUserRoutines().observe(requireActivity(), pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    viewModel.setSelectedRoutineList(pagedListResource.getData());
                    break;
                default:
                    switchResourceStatus(pagedListResource.getStatus());
            }
        });

        viewModel.getPopularRoutines().observe(requireActivity(), pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    list.clear();
                    list.addAll(pagedListResource.getData());
                    listHomeAdapter.notifyDataSetChanged();
                    break;
                default:
                    switchResourceStatus(pagedListResource.getStatus());
            }
        });

        viewModel.getDifficultyRoutines().observe(requireActivity(), pagedListResource -> {
            switch (pagedListResource.getStatus()) {
                case SUCCESS:
                    recommendedList.clear();
                    recommendedList.addAll(pagedListResource.getData());
                    listRecommendedAdapter.notifyDataSetChanged();
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
        listHomeAdapter = new ListRoutineHomeAdapter(list,getContext());
        homeRecyclerView.setAdapter(listHomeAdapter);

        recommendedRecyclerView = view.findViewById(R.id.home_routine_recommended_recycler_view);
        recommendedList = new ArrayList<>();
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        listRecommendedAdapter = new ListRoutineHomeAdapter(recommendedList,getContext());
        recommendedRecyclerView.setAdapter(listRecommendedAdapter);

        return view;
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