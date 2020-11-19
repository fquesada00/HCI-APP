package ar.com.edu.itba.hci_app.ui.routine;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.ActivityDisplayRoutineBinding;

public class DisplayRoutineActivity extends AppCompatActivity {

    private ActivityDisplayRoutineBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_routine);

    }

}