package sr.unasat.financialapp.arrayadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

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


     static class RecyclerViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener{

        TextView category;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
                 category= (TextView)itemView.findViewById(R.id.category_name_cat);

        }


         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             Toast.makeText(view.getContext(), "itemselected "+parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
         }
     }

}
