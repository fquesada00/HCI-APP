package ar.com.edu.itba.hci_app.ui.main;

import android.app.SearchManager;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentSearchBinding;
import ar.com.edu.itba.hci_app.domain.Category;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.adapters.CategoryAdapterListener;
import ar.com.edu.itba.hci_app.ui.adapters.ListCategorySearchAdapter;
import ar.com.edu.itba.hci_app.ui.adapters.ListSearchAdapter;
import ar.com.edu.itba.hci_app.ui.adapters.RoutineAdapterListener;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class SearchFragment extends BaseFragment<MainActivityViewModel, FragmentSearchBinding, RoutineRepository> implements RoutineAdapterListener {

    private View view;
    private RecyclerView recyclerView;
    private List<Routine> list;
    private ListSearchAdapter listSearchAdapter;

    private View categoryView;
    private RecyclerView categoryRecyclerView;
    private List<Category> categoryList;
    private ListCategorySearchAdapter categorySearchAdapter;

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
        setHasOptionsMenu(true);
        view = super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = view.findViewById(R.id.search_recycle_view);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listSearchAdapter = new ListSearchAdapter(list, getContext(), this);
        //TODO FRAN CAMBIA LA LISTA A LIVE DATA ACA MISMO
        viewModel.getRoutines().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.getStatus()) {
                case LOADING:
                    //TODO show progressbar
                    break;
                case SUCCESS:
                    //TODO hide progressbar
//                    list.clear();
//                    list.addAll(listResource.getData());
                    listSearchAdapter.setList(listResource.getData());
                    listSearchAdapter.notifyDataSetChanged();
                    if (list.size() > 10)
//                        recyclerView.scrollToPosition(list.size()-1);
                        break;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    Log.d("SCROLL", "onScrollStateChanged: ");
                    viewModel.getMoreRoutines();
                }
            }
        });

        recyclerView.setAdapter(listSearchAdapter);

//        categoryRecyclerView = view.findViewById(R.id.search_category_recycler_view);
//        categoryList = new ArrayList<>();
//        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));


//        categorySearchAdapter = new ListCategorySearchAdapter(categoryList, this);
//        categoryRecyclerView.setAdapter(categorySearchAdapter);

        return view;
    }

//    //TODO ACA ES EL CLICK EN LAS CATEGORIAS, QUE BUSCA POR ID Y HACE DISPLAY
//    public void onCategoryButtonClick(Category category){
//            viewModel.toggleCategory(category);
//            List<Routine> routines = listSearchAdapter.getList().stream().filter(e->viewModel.getActiveCategories().isEmpty() || viewModel.getActiveCategories().contains(e.getCategory())).collect(Collectors.toList());
////            list.clear();
////            listSearchAdapter.notifyDataSetChanged();
//            listSearchAdapter.setList(routines);
//            listSearchAdapter.notifyDataSetChanged();
//
//    }

    //TODO ACA ES EL CLICK DE LA CARTA DE RUTINA, QUE REDIRIJE A LA VISTA DE RECYCLERVIEW DE RECYCLERVIEW
    public void onRoutineButtonClick(Routine routine) {
        Log.d("here", "int " + routine.getName());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button33.setOnClickListener(v -> {
            binding.button33.setBackgroundColor(Color.RED);
        });

//        viewModel.getCategories().observe(requireActivity(), v -> {
//            switch (v.getStatus()) {
//                case SUCCESS:
////                    categoryList.clear();
////                    categoryList.addAll(v.getData());
////                    categorySearchAdapter.notifyDataSetChanged();
//                    break;
//                default:
//                    switchResourceStatus(v.getStatus());
//            }
//        });

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
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_button1).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                viewModel.setSearchActive(true);
                list.clear();
                listSearchAdapter.setList(new ArrayList<>());
                listSearchAdapter.notifyDataSetChanged();
                Log.d("TEST", "onQueryTextSubmit: " + listSearchAdapter.getList().toString());
                viewModel.searchRoutines(query).observe(getViewLifecycleOwner(), listResource -> {
                    switch (listResource.getStatus()) {
                        case LOADING:
                            //TODO show progressbar
                            break;
                        case SUCCESS:
                            //TODO hide progressbar
//                    list.clear();
//                    list.addAll(listResource.getData());
                            Log.d("TEST", "onQueryTextSubmit: " + listResource.getData().toString());
                            listSearchAdapter.setList(listResource.getData());
                            listSearchAdapter.notifyDataSetChanged();
                            if (list.size() > 10)
//                        recyclerView.scrollToPosition(list.size()-1);
                                break;
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(onQueryTextListener);
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