package ar.com.edu.itba.hci_app.ui.adapters;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.domain.Exercise;

public class ExerciseRoutineAdapter extends RecyclerView.Adapter<ExerciseRoutineAdapter.ExerciseViewHolder>{

    private List<Exercise> list;

    public ExerciseRoutineAdapter(List<Exercise> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_frame,null,false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position){
        holder.bindData(list.get(position));
    }

    public void setList(List<Exercise> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView duration;

        public ExerciseViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.exercise_name);
            duration = view.findViewById(R.id.exercise_length);
        }

        void bindData(Exercise exercise){

            String aux = "x" + String.valueOf(exercise.getRepetitions());
            duration.setText(aux);

            if(exercise.getId() == -1) {
                name.setText(exercise.getName());
                name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            }

            else {
                String aux2 = "\t" + exercise.getName();
                name.setText(aux2);
            }
        }
    }
}
