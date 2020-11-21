package ar.com.edu.itba.hci_app.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import ar.com.edu.itba.hci_app.DispatcherActivity;
import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.databinding.FragmentSettingsBinding;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.BaseFragment;


public class SettingsFragment extends BaseFragment<MainActivityViewModel, FragmentSettingsBinding, RoutineRepository> {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.logout.setOnClickListener(v -> {

        });

        int night = Configuration.UI_MODE_NIGHT_YES;





        binding.confirmSettings.setOnClickListener(v -> {
            if (binding.switch1.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            String languageToLoad  = "fr_FR";
            if(binding.switch2.isChecked()){
                languageToLoad = "es";
            }else{
                languageToLoad = "en";
            }
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
            Intent intent = new Intent(requireActivity(), DispatcherActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @Override
    public Class<MainActivityViewModel> getViewModel() {
        return MainActivityViewModel.class;
    }

    @Override
    public FragmentSettingsBinding getFragmentBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentSettingsBinding.inflate(inflater, container, false);
    }

    @Override
    public RoutineRepository getFragmentRepository() {
        MyApplication myApplication = (MyApplication) requireActivity().getApplication();
        return myApplication.getRoutineRepository();
    }

}
