package ar.com.edu.itba.hci_app.ui.rating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;

import ar.com.edu.itba.hci_app.DispatcherActivity;
import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.domain.Rating;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.ui.base.ViewModelFactory;
import ar.com.edu.itba.hci_app.ui.main.MainActivity;
import ar.com.edu.itba.hci_app.ui.main.MainActivityViewModel;

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
        MyApplication application = (MyApplication) getApplication();
//        ViewModelProvider.NewInstanceFactory factory = new ViewModelFactory(application.getRoutineRepository(), getApplication());
//        RateRoutineActivityViewModel viewModel= new ViewModelProvider(this, factory).get(RateRoutineActivityViewModel.class);



        Button confirm = findViewById(R.id.confirm_rating);
        confirm.setOnClickListener( v-> application.getRoutineRepository().addRating(routine.getId(),new Rating((double) ratingBar.getRating(),"")).observe(this, ratingResource -> {
            switch (ratingResource.getStatus()){
                case SUCCESS:
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    return;
            }
        }));
    }
}