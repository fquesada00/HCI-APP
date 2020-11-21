package ar.com.edu.itba.hci_app.ui.rating;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import ar.com.edu.itba.hci_app.DispatcherActivity;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.ui.main.MainActivity;

public class RateRoutineActivity extends AppCompatActivity {

    private Routine routine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_routine);

        routine = (Routine) getIntent().getExtras().getSerializable("ROUTINE");
        TextView textView = findViewById(R.id.routine_name_rating);
        textView.setText(routine.getName());

        RatingBar ratingBar = findViewById(R.id.rating_stars);

        Button confirm = findViewById(R.id.confirm_rating);
        confirm.setOnClickListener(v -> {
            startActivity(new Intent(this, DispatcherActivity.class));
            finish();
            return;
        });
    }
}