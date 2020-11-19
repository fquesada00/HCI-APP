package ar.com.edu.itba.hci_app.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.domain.Category;

public class ListCategorySearchAdapter extends RecyclerView.Adapter<ListCategorySearchAdapter.CategoryViewHolder> {

    private List<Category> list;

    public ListCategorySearchAdapter(List<Category> list){
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ListCategorySearchAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_search_view, null, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategorySearchAdapter.CategoryViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    public void setList(List<Category> list) {
        this.list = list;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public CategoryViewHolder(View view){
            super(view);
            button = view.findViewById(R.id.btn_category_list);
        }

        void bindData(Category category){
            button.setText(category.getName());
        }
    }
}
