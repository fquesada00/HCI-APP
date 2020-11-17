package ar.com.edu.itba.hci_app.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentSearchBinding;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class SearchFragment extends BaseFragment<MainActivityViewModel, FragmentSearchBinding, RoutineRepository> {


    private static SearchFragment searchFragment;

    public static SearchFragment getSearchFragment(){
        if(searchFragment == null){
            searchFragment = new SearchFragment();
        }
        return searchFragment;
    }

//    private SearchFragment() {
//        // Required empty public constructor
//    }

    //TODO LO PARCHIE COMO PUDE JAJAJJA
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int[] val = {0};
        binding.button3.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), DisplayRoutineActivity.class);
//            intent.putExtras(getActivity().getIntent()).putExtra("color", val[0]);
//            val[0] = val[0] == 0?1:0;
//            startActivity(intent);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoutineDescriptionFragment()).addToBackStack(null).commit();
            getActivity().getSupportFragmentManager().executePendingTransactions();
        });
        searchFragment = this;
        Log.d("ACA", searchFragment.toString());

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
    public RoutineRepository getFragmentRepository() {
        return BaseRepository.getRoutineRepository(getContext());
    }
}