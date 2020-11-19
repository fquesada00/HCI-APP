package ar.com.edu.itba.hci_app.ui.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentSearchBinding;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class SearchFragment extends BaseFragment<MainActivityViewModel, FragmentSearchBinding, RoutineRepository> {


    private View view;
    private RecyclerView recyclerView;
    private List<Routine> list;

    private static SearchFragment searchFragment;

    public static SearchFragment getSearchFragment() {
        if (searchFragment == null) {
            searchFragment = new SearchFragment();
        }
        return searchFragment;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.search_recycle_view);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.add(new Routine("BAJA", null, null, 3.0, "BAJA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));
        list.add(new Routine("ALTA", null, null, 3.0, "ALTA", true, 0, null, null));

        ListSearchAdapter adapter = new ListSearchAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        setHasOptionsMenu(true);



//        viewModel.getDifficultyRoutines().observe(requireActivity(), list -> {
//            switch (list.getStatus()) {
//                case SUCCESS:
//                    for (int i = 0; i < list.getData().size(); i++) {
//                        routineList.add(list.getData().get(i));
//                    }
//
//                    break;
//                default:
//                    switchResourceStatus(list.getStatus());
//            }
//        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



//
//        viewModel.getCurrentUserRoutines().observe(requireActivity(), list -> {
//            switch (list.getStatus()) {
//                case SUCCESS:
//
//                    break;
//                default:
//                    switchResourceStatus(list.getStatus());
//            }
//        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchFragment = this;
//
//        routineList.add(new Routine("BAJA", null, null, 3.0, "OCTAVIO", true, 0, null, null));
//        routineList.add(new Routine("ALTA", null, null, 3.0, "OCTAVIO", true, 0, null, null));
//
//        ListSearchAdapter adapter = new ListSearchAdapter(routineList, getContext());
//        RecyclerView recyclerView = getView().findViewById(R.id.search_recycle_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);

//        binding.routineCardDisplay.setOnClickListener(v -> {
//            binding.routineCardDisplay.setCardBackgroundColor(Color.BLUE);
//        });

//        viewModel.getCategories().observe(requireActivity(), pagedListResource -> {
//            switch (pagedListResource.getStatus()){
//                case SUCCESS:
//                    break;
//                default:
//                    switchResourceStatus(pagedListResource.getStatus());
//            }
//        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentSearchBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSearchBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }
}