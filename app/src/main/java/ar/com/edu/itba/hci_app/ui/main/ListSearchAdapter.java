package ar.com.edu.itba.hci_app.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.domain.Routine;

public class ListSearchAdapter extends RecyclerView.Adapter<ListSearchAdapter.ViewHolder> {

    private List<Routine> list;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListSearchAdapter(List<Routine> list, Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ListSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item_search_fragment, null);
        return new ListSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListSearchAdapter.ViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    public void setList(List<Routine> list){
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//                ImageView imageView;
        TextView creator, name, rating;

        public ViewHolder(View itemView) {
            super(itemView);
//            imageView = itemView.findViewById(R.id.);
            creator = itemView.findViewById(R.id.creator_routine);
            name = itemView.findViewById(R.id.routine_title);
            rating = itemView.findViewById(R.id.rating_bar);
        }

        void bindData(Routine routine){
            //TODO la rutina no tiene un drawable --> imageView.setImageDrawable(routine);
            creator.setText(routine.getCreator().getUsername());
            name.setText(routine.getName());
            rating.setText(routine.getAverageRating().toString());
        }
    }

}
