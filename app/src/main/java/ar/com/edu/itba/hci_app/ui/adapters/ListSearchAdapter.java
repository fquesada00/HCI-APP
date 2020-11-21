package ar.com.edu.itba.hci_app.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Function;

import ar.com.edu.itba.hci_app.MyApplication;
import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.domain.Category;
import ar.com.edu.itba.hci_app.domain.Routine;
import ar.com.edu.itba.hci_app.ui.main.MainActivityViewModel;

public class ListSearchAdapter extends RecyclerView.Adapter<ListSearchAdapter.RoutineViewHolder> {

    private List<Routine> list;

    private LayoutInflater layoutInflater;
    private Context context;
    private RoutineAdapterListener listener;

    public ListSearchAdapter(List<Routine> list, Context context, RoutineAdapterListener listener) {
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ListSearchAdapter.RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search_fragment, null, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListSearchAdapter.RoutineViewHolder holder, int position) {
        Log.d("HOLDER", "HOLDER: " + position);
        holder.bindData(list.get(position));
    }

    public void setList(Function<?, List<Routine>> fun) {
        this.list = fun.apply(null);
    }

    public void setList(List<Routine> list) {
        this.list = list;
    }


    public List<Routine> getList() {
        return list;
    }

    public class RoutineViewHolder extends RecyclerView.ViewHolder {

        //                ImageView imageView;
        TextView creator, name, rating;
        CardView cardView;
        int color;
        MainActivityViewModel viewModel;

        public RoutineViewHolder(View itemView) {
            super(itemView);

//            imageView = itemView.findViewById(R.id.);
            creator = (TextView) itemView.findViewById(R.id.creator_routine);
            name = (TextView) itemView.findViewById(R.id.routine_title);
            cardView = itemView.findViewById(R.id.routine_card_display);
//            rating = itemView.findViewById(R.id.rating_bar);
        }

        void bindData(Routine routine) {
            //TODO la rutina no tiene un drawable --> imageView.setImageDrawable(routine);
            creator.setText(routine.getDifficulty());
            name.setText(routine.getName());
            cardView.setOnClickListener(v -> {
                cardView.setCardBackgroundColor(Color.BLUE);
                listener.onRoutineButtonClick(routine);
                Log.d("HOLDER", "HOLDER: " + "eeee");
            });

//            rating.setText(routine.getAverageRating().toString());
        }
    }

}
