package ar.com.edu.itba.hci_app.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentProfileBinding;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;



public class ProfileFragment extends BaseFragment<MainActivityViewModel, FragmentProfileBinding, UserRepository> {



    private static ProfileFragment profileFragment;

    public static ProfileFragment getProfileFragment(){
        if(profileFragment == null){
            profileFragment = new ProfileFragment();
        }
        return profileFragment;
    }

    private ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentProfileBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater,container,false);
    }

    @Override
    public UserRepository getFragmentRepository() {
        return BaseRepository.getUserRepository(getContext());
    }
}