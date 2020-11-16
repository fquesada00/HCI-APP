package ar.com.edu.itba.hci_app.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentSearchBinding;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;
import ar.com.edu.itba.hci_app.ui.routine.DisplayRoutineActivity;


public class SearchFragment extends BaseFragment<MainActivityViewModel, FragmentSearchBinding, UserRepository> {


    private static SearchFragment searchFragment;

    public static SearchFragment getSearchFragment(){
        if(searchFragment == null){
            searchFragment = new SearchFragment();
        }
        return searchFragment;
    }

    private SearchFragment() {
        // Required empty public constructor
    }

    //TODO LO PARCHIE COMO PUDE JAJAJJA
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int[] val = {0};
        binding.button3.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DisplayRoutineActivity.class);
            intent.putExtras(getActivity().getIntent()).putExtra("color", val[0]);
            val[0] = val[0] == 0?1:0;
            startActivity(intent);
        });
    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentSearchBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSearchBinding.inflate(inflater,container,false);
    }

    @Override
    public UserRepository getFragmentRepository() {
        return BaseRepository.getUserRepository(getContext());
    }
}