package ar.com.edu.itba.hci_app.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import ar.com.edu.itba.hci_app.databinding.FragmentDisplayRoutinesBinding;
import ar.com.edu.itba.hci_app.databinding.FragmentRoutineDescriptionBinding;
import ar.com.edu.itba.hci_app.domain.Cycle;
import ar.com.edu.itba.hci_app.domain.Exercise;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.adapters.ListSearchAdapter;
import ar.com.edu.itba.hci_app.ui.adapters.RoutineAdapterListener;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class DisplayRoutinesFragment extends BaseFragment<MainActivityViewModel, FragmentDisplayRoutinesBinding, RoutineRepository> implements RoutineAdapterListener {

    private View view;

    private List<Routine> list;
    private RecyclerView recyclerView;
    private ListSearchAdapter adapter;


    public DisplayRoutinesFragment() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = view.findViewById(R.id.display_routines);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListSearchAdapter(list, getContext(), this::onRoutineButtonClick);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getSelectedRoutineList().observe(requireActivity(), v -> {
            list.clear();
            list.addAll(v);
            adapter.notifyDataSetChanged();
            if (v.size() == 0) {
                view.findViewById(R.id.display_routines).setVisibility(View.GONE);
                view.findViewById(R.id.no_routines_to_display).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentDisplayRoutinesBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDisplayRoutinesBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }

    @Override
    public void onRoutineButtonClick(Routine routine) {
        viewModel.getRoutineExercises(routine.getId(), null, null, Integer.MAX_VALUE, null, null);
//                .observe(getViewLifecycleOwner(), v -> {
//                    switch (v.getStatus()) {
//                        case SUCCESS:
//                            if (v.getData().size() < 3) {
//                                return;
//                            }
//                            viewModel.setCalentamientoList(v.getData().get(0));
//                            List<Exercise> exerciseList = new ArrayList<>();
//                            for (int i = 1; i < (v.getData().size() - 1); i++) {
//                                exerciseList.addAll(v.getData().get(i));
//                            }
//                            viewModel.setPrincipalList(exerciseList);
//                            viewModel.setEnfriamientoList(v.getData().get(v.getData().size() - 1));
//                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatisticsFragment()).addToBackStack(null).commit();
//                            break;
//                        default:
//                            Log.d("VISTA", "AFUERA OBSERVEeeeee");
//                    }
//                });
        viewModel.temp.observe(getViewLifecycleOwner(), v->{
            switch (v.getStatus()) {
                case SUCCESS:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatisticsFragment()).addToBackStack(null).commit();
                    break;
                default:
            }
        });

        viewModel.setRoutineURL(routine);











//        List<Exercise> enfriamientoList = new ArrayList<>();
//        enfriamientoList.add(new Exercise(1, "Prueba 1", 1, null, null, 1, 1));
//        enfriamientoList.add(new Exercise(1, "Prueba 2", 1, null, null, 1, 1));
//
//        List<Exercise> principalList = new ArrayList<>();
//        principalList.add(new Exercise(1, "Brazos", -1, null, null, 2, 1));
//        principalList.add(new Exercise(1, "Flexiones de Brazos", 1, null, null, 1, 1));
//        principalList.add(new Exercise(1, "Abs", 1, null, null, 1, 1));
//        principalList.add(new Exercise(1, "Piernas", 1, null, null, 1, 1));
//
//        principalList.add(new Exercise(1, "Abdominales", -1, null, null, 1, 1));
//        principalList.add(new Exercise(1, "Abs", 1, null, null, 10, 1));
//        principalList.add(new Exercise(1, "Abs Cruzados", 1, null, null, 12, 1));
//        principalList.add(new Exercise(1, "Abs Crunches", 1, null, null, 20, 1));
//
//        List<Exercise> calentamientoList = new ArrayList<>();
//        calentamientoList.add(new Exercise(1, "Gluteos", 1, null, null, 1, 1));
//        calentamientoList.add(new Exercise(1, "Abdominales", 1, null, null, 1, 1));
//        calentamientoList.add(new Exercise(1, "Flexiones de Brazos", 1, null, null, 1, 1));
//        calentamientoList.add(new Exercise(1, "Abs", 1, null, null, 1, 1));
//        calentamientoList.add(new Exercise(1, "Piernas", 1, null, null, 1, 1));
//
//        viewModel.setPrincipalList(principalList);
//        viewModel.setEnfriamientoList(enfriamientoList);
//        viewModel.setCalentamientoList(calentamientoList);

//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StatisticsFragment()).addToBackStack(null).commit();
    }
}