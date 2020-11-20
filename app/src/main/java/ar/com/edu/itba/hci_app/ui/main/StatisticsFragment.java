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
import android.widget.Adapter;
import android.widget.ArrayAdapter;


import java.util.ArrayList;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentSearchBinding;
import ar.com.edu.itba.hci_app.databinding.FragmentStatisticsBinding;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.adapters.ExerciseAdapter;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;



public class StatisticsFragment extends BaseFragment<MainActivityViewModel, FragmentStatisticsBinding, RoutineRepository> {

    private static StatisticsFragment statisticsFragment;

    private View view;

    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private ExerciseAdapter adapter;

    public static StatisticsFragment getStatisticsFragment(){
        if(statisticsFragment == null){
            statisticsFragment = new StatisticsFragment();
        }
        return statisticsFragment;
    }

    private StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statisticsFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = view.findViewById(R.id.exercise_recycler_view);
        list = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i++)
            list.add("Row: "+i);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExerciseAdapter(list);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.button4.setOnClickListener(v -> {
            list.add("Row");
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentStatisticsBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentStatisticsBinding.inflate(inflater,container,false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();    }
}