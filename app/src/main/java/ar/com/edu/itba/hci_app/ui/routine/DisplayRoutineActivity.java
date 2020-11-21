package ar.com.edu.itba.hci_app.ui.routine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.ActivityDisplayRoutineBinding;
import ar.com.edu.itba.hci_app.domain.Exercise;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.repository.RoutineRepository;
import ar.com.edu.itba.hci_app.ui.base.ViewModelFactory;
import ar.com.edu.itba.hci_app.ui.main.MainActivityViewModel;

public class DisplayRoutineActivity extends AppCompatActivity {

    private int totalExercises;
    private int actualExercise;
    private List<Exercise> exercises;
    private Routine routine;
    private ActivityDisplayRoutineBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_SignIn);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_routine);

        Toolbar toolbar = findViewById(R.id.toolbar_display_routine);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        routine = (Routine) getIntent().getExtras().getSerializable("ROUTINE");
        exercises = (List<Exercise>) getIntent().getExtras().getSerializable("ROUTINE_EXERCISES");
        Log.d("ROUTINE", "R: " + routine.getId() + " list size " + exercises.size());
        for(Exercise ex : exercises)
            Log.d("DUFFYY", "onCreate: "+ex.getName());

        totalExercises = exercises.size();

        binding = ActivityDisplayRoutineBinding.inflate(getLayoutInflater());
        changeButtons();
        binding.continuar.setOnClickListener(v -> {
            actualExercise++;
            changeButtons();
        });
    }

    private void changeButtons() {
        TextView textView = findViewById(R.id.ej_actual);
        Log.d("DUFYYYY", "changeButtons: "+exercises.get(actualExercise).getName());
        textView.setText(exercises.get(actualExercise).getName());
        String s;
        if (exercises.get(actualExercise).getDuration() != 0)
            s = exercises.get(actualExercise).getDuration() + "s";
        else
            s = "x" + exercises.get(actualExercise).getRepetitions();
        binding.ejActualReps.setText(s);
        if ((actualExercise + 1) < totalExercises) {
            binding.sigEj.setText(exercises.get(actualExercise + 1).getName());
            if (exercises.get(actualExercise + 1).getDuration() != 0)
                s = exercises.get(actualExercise + 1).getDuration() + "s";
            else
                s = "x" + exercises.get(actualExercise + 1).getRepetitions();
            binding.sigEjReps.setText(s);
            if ((actualExercise + 2) < totalExercises) {
                binding.sigSigEj.setText(exercises.get(actualExercise + 2).getName());
                if (exercises.get(actualExercise + 2).getDuration() != 0)
                    s = exercises.get(actualExercise + 2).getDuration() + "s";
                else
                    s = "x" + exercises.get(actualExercise + 2).getRepetitions();
                binding.sigSigEjReps.setText(s);
            }else{
                binding.sigSigEj.setText("---");
                binding.sigSigEjReps.setText("---");
            }
        }
        else{
            binding.sigEj.setText("---");
            binding.sigEjReps.setText("---");
        }
    }

    @Override
    public void onBackPressed() {
        if (actualExercise == 0) {
            finish();
            return;
        }
        actualExercise--;
        changeButtons();
    }
}