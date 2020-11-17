package ar.com.edu.itba.hci_app.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.FragmentNotificationsBinding;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.repository.UserRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;

public class NotificationsFragment extends BaseFragment<MainActivityViewModel, FragmentNotificationsBinding, RoutineRepository> {

    private static NotificationsFragment notificationsFragment;

    public static NotificationsFragment getNotificationsFragment(){
        if(notificationsFragment == null){
            notificationsFragment = new NotificationsFragment();
        }
        return notificationsFragment;
    }

    private NotificationsFragment() {
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
    public FragmentNotificationsBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentNotificationsBinding.inflate(inflater,container,false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        return BaseRepository.getRoutineRepository(getContext());
    }
}