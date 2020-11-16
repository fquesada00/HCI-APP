package ar.com.edu.itba.hci_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import ar.com.edu.itba.hci_app.databinding.ActivityDispatcherBinding;
import ar.com.edu.itba.hci_app.databinding.ActivityMainBinding;
import ar.com.edu.itba.hci_app.network.Resource;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.network.api.model.Credentials;
import ar.com.edu.itba.hci_app.network.api.model.PagedList;
import ar.com.edu.itba.hci_app.network.api.model.Routine;
import ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter;
import ar.com.edu.itba.hci_app.network.api.model.Token;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.ui.auth.AuthActivity;
import ar.com.edu.itba.hci_app.ui.main.MainActivity;


public class DispatcherActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Button secondActBtn = null;
    private AppCompatActivity act;
    private ActivityDispatcherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = this;
        binding = ActivityDispatcherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button secondActBtn = binding.secondActivityBtn;
        Button authActBtn = binding.authActivityBtn;
        Button getRoutines = binding.getRoutinesBtn;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.app_logo);
        secondActBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
        authActBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        });
        //TODO: MUY HARDCODEADO PERO PARA TESTEOS
        AppPreferences appPreferences = new AppPreferences(getApplicationContext());
        appPreferences.clearToken();
        getRoutines.setOnClickListener(v -> BaseRepository.getUserRepository(this).login(new Credentials("johndoe", "1234567890")).observe(this, tokenResource -> {

            if (tokenResource.getStatus() == Status.SUCCESS) {
                Log.d("LOGIN", "onCreate: "+ tokenResource.getData().getToken());

                appPreferences.setAuthToken(tokenResource.getData().getToken());
                BaseRepository.getRoutineRepository(getApplicationContext()).getRoutine(null, null, null, null, null, RoutinePagedListGetter.ALL)
                        .observe(act, pagedListResource -> {
                            if (pagedListResource.getStatus() == Status.SUCCESS) {
                                Toast.makeText(getApplicationContext(), pagedListResource.getData().getResults().toString(), Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getApplicationContext(), "GET FAILED", Toast.LENGTH_SHORT).show();
                        });
            }else if(tokenResource.getStatus() != Status.LOADING)
                Toast.makeText(getApplicationContext(),"Failed Login",Toast.LENGTH_SHORT).show();
        }));


    }


}