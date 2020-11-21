package ar.com.edu.itba.hci_app.ui.routine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentDisplayExerciseBinding;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class DisplayExerciseFragment extends BaseFragment<DisplayRoutineViewModel, FragmentDisplayExerciseBinding, RoutineRepository> {


    public DisplayExerciseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_exercise, container, false);
    }

    @Override
    public Class<DisplayRoutineViewModel> getViewModel() {
        return DisplayRoutineViewModel.class;
    }

    @Override
    public FragmentDisplayExerciseBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentDisplayExerciseBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }
}