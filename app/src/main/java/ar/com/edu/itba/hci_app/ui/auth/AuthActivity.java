package ar.com.edu.itba.hci_app.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {
    ActivityAuthBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);
    }
}