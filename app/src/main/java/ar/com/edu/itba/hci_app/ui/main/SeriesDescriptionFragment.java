package ar.com.edu.itba.hci_app.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentSeriesDescriptionBinding;
import ar.com.edu.itba.hci_app.domain.Exercise;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;

public class SeriesDescriptionFragment extends BaseFragment<MainActivityViewModel, FragmentSeriesDescriptionBinding, RoutineRepository> {

    private RecyclerView exerciseRecyclerView;
    private List<Exercise> exerciseList;
    private ExerciseRoutineAdapter listExerciseAdapter;

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentSeriesDescriptionBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSeriesDescriptionBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        exerciseList = new ArrayList<>();


        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        exerciseRecyclerView = view.findViewById(R.id.exercise_routine_recycler_view);
        exerciseList.add(new Exercise(20,"Gluteo",1,null,null,1,1));
        exerciseList.add(new Exercise(20,"Bicep",1,null,null,1,1));
        exerciseList.add(new Exercise(20,"Tricep",1,null,null,1,1));

        listExerciseAdapter = new ExerciseRoutineAdapter(exerciseList);
        exerciseRecyclerView.setAdapter(listExerciseAdapter);
        return view;
    }
}
