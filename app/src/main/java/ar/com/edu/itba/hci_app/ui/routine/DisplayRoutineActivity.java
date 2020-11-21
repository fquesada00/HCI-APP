package ar.com.edu.itba.hci_app.ui.routine;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.ActivityDisplayRoutineBinding;
import ar.com.edu.itba.hci_app.domain.Exercise;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.ui.rating.RateRoutineActivity;

public class DisplayRoutineActivity extends AppCompatActivity {

    private int totalExercises;
    private int actualExercise;
    private List<Exercise> exercises;
    private Routine routine;
    private ActivityDisplayRoutineBinding binding;
    private CountDownTimer counter;
    private Button btn;
    private Button pause;
    private boolean active;
    private boolean changePause;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_SignIn);
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_display_routine);

        Toolbar toolbar = findViewById(R.id.toolbar_display_routine);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        routine = (Routine) getIntent().getExtras().getSerializable("ROUTINE");
        exercises = (List<Exercise>) getIntent().getExtras().getSerializable("ROUTINE_EXERCISES");
        Log.d("ROUTINE", "R: " + routine.getId() + " list size " + exercises.size());
        for (Exercise ex : exercises)
            Log.d("DUFFYY", "onCreate: " + ex.getName());

        totalExercises = exercises.size();

//        binding = ActivityDisplayRoutineBinding.inflate(getLayoutInflater());
        changeButtons();
        btn = findViewById(R.id.continuar);
        btn.setOnClickListener(v -> {
            actualExercise++;
            if(actualExercise == totalExercises){

                Intent intent = new Intent(this, RateRoutineActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ROUTINE", routine);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return;
            }
            changeButtons();
        });
        TextView textView = findViewById(R.id.back_text_view);
        textView.setOnClickListener(v -> {
            onBackPressed();
        });

//        pause = findViewById(R.id.pause);
//        pause.setOnClickListener(v -> {
//            if (changePause) {
//                if (!active) {
//                    counter.
//                    pause.setText("CONTINUAR");
//                }
//                else
//                    pause.setText("PAUSAR");
//                active = !active;
//
//            }
//        });

    }

    private void changeButtons() {
        if(actualExercise == totalExercises){
            finish();
            return;
        }
        TextView textView = findViewById(R.id.ej_actual);
        Log.d("DUFYYYY", "changeButtons: " + exercises.get(actualExercise).getName());
        textView.setText(exercises.get(actualExercise).getName());
        String s;
        if (counter != null)
            counter.cancel();
        if (exercises.get(actualExercise).getDuration() != 0) {
            counter = new CountDownTimer(exercises.get(actualExercise).getDuration() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    TextView textView = findViewById(R.id.card_timer);
                    textView.setText(Long.toString(l / 1000) + "s");
                }

                @Override
                public void onFinish() {
                    btn.callOnClick();
                }
            }.start();
            s = exercises.get(actualExercise).getDuration() + "s";
        } else
            s = "x" + exercises.get(actualExercise).getRepetitions();
        textView = findViewById(R.id.ej_actual_reps);
        textView.setText(s);
        textView = findViewById(R.id.card_timer);
        textView.setText(s);
//        binding.ejActualReps.setText(s);
        if ((actualExercise + 1) < totalExercises) {
            textView = findViewById(R.id.sig_ej);
            textView.setText(exercises.get(actualExercise + 1).getName());
//            binding.sigEj.setText(exercises.get(actualExercise + 1).getName());
            if (exercises.get(actualExercise + 1).getDuration() != 0)
                s = exercises.get(actualExercise + 1).getDuration() + "s";
            else
                s = "x" + exercises.get(actualExercise + 1).getRepetitions();
            textView = findViewById(R.id.sig_ej_reps);
            textView.setText(s);
//            binding.sigEjReps.setText(s);
            if ((actualExercise + 2) < totalExercises) {
                textView = findViewById(R.id.sig_sig_ej);
                textView.setText(exercises.get(actualExercise + 2).getName());
//                binding.sigSigEj.setText(exercises.get(actualExercise + 2).getName());
                if (exercises.get(actualExercise + 2).getDuration() != 0)
                    s = exercises.get(actualExercise + 2).getDuration() + "s";
                else
                    s = "x" + exercises.get(actualExercise + 2).getRepetitions();
                textView = findViewById(R.id.sig_sig_ej_reps);
                textView.setText(s);
//                binding.sigSigEjReps.setText(s);
            } else {
                textView = findViewById(R.id.sig_sig_ej);
                textView.setText("---");
                textView = findViewById(R.id.sig_sig_ej_reps);
                textView.setText("---");
//                binding.sigSigEj.setText("---");
//                binding.sigSigEjReps.setText("---");
            }
        } else {
            textView = findViewById(R.id.sig_ej);
            textView.setText("---");
            textView = findViewById(R.id.sig_ej_reps);
            textView.setText("---");
//            binding.sigEj.setText("---");
//            binding.sigEjReps.setText("---");
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