package ar.com.edu.itba.hci_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ar.com.edu.itba.hci_app.databinding.ActivityDispatcherBinding;
import ar.com.edu.itba.hci_app.network.Status;
import ar.com.edu.itba.hci_app.network.api.model.CredentialsModel;
import ar.com.edu.itba.hci_app.network.api.model.RoutinePagedListGetter;
import ar.com.edu.itba.hci_app.repository.BaseRepository;
import ar.com.edu.itba.hci_app.ui.auth.AuthActivity;
import ar.com.edu.itba.hci_app.ui.main.HomeFragment;
import ar.com.edu.itba.hci_app.ui.main.MainActivity;


public class    DispatcherActivity extends AppCompatActivity {

    /*
        TODO
         -ordenar por categoria?? si devuelve id
         -abrir rutinas por URL??
         -el tema de rutinas en ejecucion que se vean de dos modos se podria mechar con la opcion que el usuario personalize el display del mismo
         -compartir rutinas?

         -agregar idioma ingles
         -el tema de la personalizacion con funcionamiento es el punto 3 de arriba (PREGUNTAR!!!!!)
     */
 
    @SuppressLint("StaticFieldLeak")
    private static Button secondActBtn = null;
    private AppCompatActivity act;
    private ActivityDispatcherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_SignIn);
        super.onCreate(savedInstanceState);
        act = this;
        binding = ActivityDispatcherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button secondActBtn = binding.secondActivityBtn;
        Button authActBtn = binding.authActivityBtn;
        Button getRoutines = binding.getRoutinesBtn;
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
        MyApplication application = (MyApplication) getApplication();
        getRoutines.setOnClickListener(v -> application.getUserRepository().login(new CredentialsModel("johndoe", "1234567890")).observe(this, tokenResource -> {

            if (tokenResource.getStatus() == Status.SUCCESS) {
                Log.d("LOGIN", "onCreate: "+ tokenResource.getData());

                appPreferences.setAuthToken(tokenResource.getData());
                application.getRoutineRepository().getRoutine(null, null, null, null, null, RoutinePagedListGetter.ALL)
                        .observe(act, pagedListResource -> {
                            if (pagedListResource.getStatus() == Status.SUCCESS) {
                                Toast.makeText(getApplicationContext(), pagedListResource.getData().toString(), Toast.LENGTH_SHORT).show();
                            } else if(pagedListResource.getStatus() != Status.LOADING)
                                Toast.makeText(getApplicationContext(), "GET FAILED", Toast.LENGTH_SHORT).show();
                        });
            }else if(tokenResource.getStatus() != Status.LOADING)
                Toast.makeText(getApplicationContext(),"Failed Login",Toast.LENGTH_SHORT).show();
        }));
        if(appPreferences.getAuthToken() != null)
            binding.loggedText.setVisibility(View.VISIBLE);
        else
            binding.loggedText.setVisibility(View.GONE);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData(); //data es el id de la rutina
        if (data != null) {
            if (appPreferences.getAuthToken() != null) {
                //TODO LO MANDO A LA RUTINA
            }
            else {
                //TODO LO MANDO AL LOGIN
            }
        }
    }


}