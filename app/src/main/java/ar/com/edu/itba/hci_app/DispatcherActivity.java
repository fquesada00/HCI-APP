package ar.com.edu.itba.hci_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ar.com.edu.itba.hci_app.databinding.ActivityMainBinding;
import ar.com.edu.itba.hci_app.ui.auth.AuthActivity;
import ar.com.edu.itba.hci_app.ui.main.SecondActivity;


public class DispatcherActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Button secondActBtn = null;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button secondActBtn = binding.secondActivityBtn;
        Button authActBtn = binding.authActivityBtn;
        secondActBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            finish();
        });
        authActBtn.setOnClickListener(v->{
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        });

    }


}