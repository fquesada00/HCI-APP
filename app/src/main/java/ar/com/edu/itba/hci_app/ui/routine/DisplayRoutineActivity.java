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

        binding = ActivityDisplayRoutineBinding.inflate(getLayoutInflater());

//        Button button = findViewById(R.id.button);
//
//        int id = getIntent().getExtras().getInt("color");
//        switch (id){
//            case 0:
//                button.setBackgroundColor(Color.WHITE);
//                break;
//            case 1:
//                button.setBackgroundColor(Color.RED);
//                break;
//        }
    }

}