package sr.unasat.financialapp.arrayadapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import sr.unasat.financialapp.R;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.RecyclerViewHolder> {

    private List<String> category_names;
    public CategoryRecyclerAdapter(List<String> category_names){

        this.category_names=category_names;
        category_names.remove("no report");

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category,parent,false);
        return new RecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.category.setText(category_names.get(position));
    }

    @Override
    public int getItemCount() {
        return category_names.size();
    }

     static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView category;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
                 category= (TextView)itemView.findViewById(R.id.category_name);

        }
    }

}
