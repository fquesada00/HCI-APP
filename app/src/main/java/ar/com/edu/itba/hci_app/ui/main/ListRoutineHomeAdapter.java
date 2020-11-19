package ar.com.edu.itba.hci_app.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.domain.Routine;

public class ListRoutineHomeAdapter extends RecyclerView.Adapter<ListRoutineHomeAdapter.RoutineViewHolder> {

    private List<Routine> list;
    private Context context;

    public ListRoutineHomeAdapter(List<Routine> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ListRoutineHomeAdapter.RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_item_home, null, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRoutineHomeAdapter.RoutineViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    public void setList(List<Routine> list) {
        this.list = list;
    }


    public class RoutineViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView img;
        boolean flag = false;


        public RoutineViewHolder(View view) {
            super(view);
            nombre = view.findViewById(R.id.routine_item_text);
            img = view.findViewById(R.id.routine_item_image);
        }

        void bindData(Routine routine) {
            nombre.setText(routine.getName());
            img.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.image1));
            img.setOnClickListener(v -> {
                if(flag)
                    nombre.setText(routine.getName());
                else
                    nombre.setText(routine.getDifficulty());
                flag = !flag;
            });
        }
    }

}
