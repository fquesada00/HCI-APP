package ar.com.edu.itba.hci_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import ar.com.edu.itba.hci_app.databinding.ActivityDispatcherBinding;
import ar.com.edu.itba.hci_app.ui.auth.AuthActivity;
import ar.com.edu.itba.hci_app.ui.main.MainActivity;


public class DispatcherActivity extends AppCompatActivity {


    private ActivityDispatcherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDispatcherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button secondActBtn = binding.secondActivityBtn;
        Button authActBtn = binding.authActivityBtn;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.app_logo);
        secondActBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
        authActBtn.setOnClickListener(v->{
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        });

    }


}