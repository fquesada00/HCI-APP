package ar.com.edu.itba.hci_app.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentStatisticsBinding;
import ar.com.edu.itba.hci_app.domain.Cycle;
import ar.com.edu.itba.hci_app.domain.Exercise;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.adapters.ExerciseRoutineAdapter;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;
import ar.com.edu.itba.hci_app.ui.routine.DisplayRoutineActivity;


public class StatisticsFragment extends BaseFragment<MainActivityViewModel, FragmentStatisticsBinding, RoutineRepository> {

    private static StatisticsFragment statisticsFragment;
    private View view;

    private RecyclerView calentamientoRecyclerView;
    private List<Exercise> calentamientoList;
    private ExerciseRoutineAdapter calentamientoListAdapter;

    private RecyclerView principalRecyclerView;
    private List<Exercise> principalList;
    private ExerciseRoutineAdapter principalListAdapter;

    private RecyclerView enfriamientoRecyclerView;
    private List<Exercise> enfriamientoList;
    private ExerciseRoutineAdapter enfriamientoListAdapter;

    private List<Cycle> cycleList;

    private boolean favourite = false;

    public static StatisticsFragment getStatisticsFragment() {
        if (statisticsFragment == null) {
            statisticsFragment = new StatisticsFragment();
        }
        return statisticsFragment;
    }

    public StatisticsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statisticsFragment = this;
        Log.d("ACA", statisticsFragment.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = super.onCreateView(inflater, container, savedInstanceState);

        viewModel.getCalentamientoList().observe(getViewLifecycleOwner(), v -> {
            calentamientoList.clear();
            calentamientoList.addAll(v);
            calentamientoListAdapter.notifyDataSetChanged();
        });

        viewModel.getPrincipalList().observe(getViewLifecycleOwner(), v -> {
            principalList.clear();
            principalList.addAll(v);
            principalListAdapter.notifyDataSetChanged();
        });

        viewModel.getEnfriamientoList().observe(getViewLifecycleOwner(), v -> {
            enfriamientoList.clear();
            enfriamientoList.addAll(v);
            enfriamientoListAdapter.notifyDataSetChanged();
        });

        calentamientoRecyclerView = view.findViewById(R.id.exercise_routine_recycler_view1);
        calentamientoList = new ArrayList<>();
        calentamientoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        calentamientoList.add(new Exercise(1, "Gluteos", 1, null, null, 1, 1));
//        calentamientoList.add(new Exercise(1, "Abdominales", 1, null, null, 1, 1));
//        calentamientoList.add(new Exercise(1, "Flexiones de Brazos", 1, null, null, 1, 1));
//        calentamientoList.add(new Exercise(1, "Abs", 1, null, null, 1, 1));
//        calentamientoList.add(new Exercise(1, "Piernas", 1, null, null, 1, 1));

        calentamientoListAdapter = new ExerciseRoutineAdapter(calentamientoList);
        calentamientoRecyclerView.setAdapter(calentamientoListAdapter);

        principalRecyclerView = view.findViewById(R.id.exercise_routine_recycler_view2);
        principalList = new ArrayList<>();
        principalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        principalList.add(new Exercise(1, "Brazos", -1, null, null, 2, 1));
//        principalList.add(new Exercise(1, "Flexiones de Brazos", 1, null, null, 1, 1));
//        principalList.add(new Exercise(1, "Abs", 1, null, null, 1, 1));
//        principalList.add(new Exercise(1, "Piernas", 1, null, null, 1, 1));
//
//        principalList.add(new Exercise(1, "Abdominales", -1, null, null, 1, 1));
//        principalList.add(new Exercise(1, "Abs", 1, null, null, 10, 1));
//        principalList.add(new Exercise(1, "Abs Cruzados", 1, null, null, 12, 1));
//        principalList.add(new Exercise(1, "Abs Crunches", 1, null, null, 20, 1));

        principalListAdapter = new ExerciseRoutineAdapter(principalList);
        principalRecyclerView.setAdapter(principalListAdapter);

        //   principalRecyclerView.setNestedScrollingEnabled(false);

        enfriamientoRecyclerView = view.findViewById(R.id.exercise_routine_recycler_view);
        enfriamientoList = new ArrayList<>();
        enfriamientoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //  enfriamientoRecyclerView.setNestedScrollingEnabled(false);

//        enfriamientoList.add(new Exercise(1, "Prueba 1", 1, null, null, 1, 1));
//        enfriamientoList.add(new Exercise(1, "Prueba 2", 1, null, null, 1, 1));

        enfriamientoListAdapter = new ExerciseRoutineAdapter(enfriamientoList);
        enfriamientoRecyclerView.setAdapter(enfriamientoListAdapter);

        view.findViewById(R.id.share_button).setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            String s = Integer.toString(viewModel.routineURL.getId());
            sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.fitbo.com/routines/" + s);
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        view.findViewById(R.id.comenzar).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DisplayRoutineActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("ROUTINE", viewModel.routineURL);
            intent.putExtras(bundle);

            for (Exercise ex : viewModel.routineExercisesList)
                Log.d("DUFFYYY", "onCreateView: " + ex.getName());
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("ROUTINE_EXERCISES", (Serializable) viewModel.routineExercisesList);
            intent.putExtras(bundle1);
            startActivity(intent);
        });

        viewModel.getFavouritesRoutines().observe(getViewLifecycleOwner(),resource -> {
            switch (resource.getStatus()){
                case SUCCESS:
                    binding.favouriteBtn.setBackgroundResource(R.drawable.full_favorite);
                    favourite = true;
            }
        });

        binding.favouriteBtn.setOnClickListener(v -> {
            if (!favourite) {
                viewModel.addFavouriteRoutine(viewModel.routineURL.getId()).observe(getViewLifecycleOwner(), rvoid -> {
                    switch (rvoid.getStatus()) {
                        case SUCCESS:
                            binding.favouriteBtn.setBackgroundResource(R.drawable.full_favorite);
                    }
                });

            } else {
                viewModel.removeFavouriteRoutine(viewModel.routineURL.getId()).observe(getViewLifecycleOwner(), rvoid -> {
                    switch (rvoid.getStatus()) {
                        case SUCCESS:
                            binding.favouriteBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    }
                });

            }
            favourite = !favourite;
        });

        String s = "x" + viewModel.calentamientoRepetitions;
        binding.calentamientoReps.setText(s);
        s = "x" + viewModel.enfriamientoRepetitions;
        binding.enfriamientoReps.setText(s);

        binding.backBtn.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }


    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentStatisticsBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentStatisticsBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }

    @Override
    public void onDestroyView() {
        viewModel.resetData(getViewLifecycleOwner());
        super.onDestroyView();
    }
}