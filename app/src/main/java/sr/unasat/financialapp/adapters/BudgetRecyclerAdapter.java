package sr.unasat.financialapp.adapters;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;


public class BudgetRecyclerAdapter extends RecyclerView.Adapter<BudgetRecyclerAdapter.RecyclerViewHolder> {
    private List<Category> categories=new ArrayList<>();

    Context context;
    FragmentManager fragmentManager;

    public BudgetRecyclerAdapter(List<Category> categories, Context context, FragmentManager fragmentManager) {

        this.context = context;

        this.fragmentManager = fragmentManager;

        for (int i = 0 ; i<categories.size();i++){

            if (categories.get(i).getBudget()!=0){
                this.categories.add(categories.get(i));
            }
        }


    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_budget,parent,false);
        return new RecyclerViewHolder(view,fragmentManager);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        double used = new Dao(context).getAmountUsedByCategoryCurrentMonth(categories.get(position));
        double budget= categories.get(position).getBudget();
        holder.categoryNameView.setText(categories.get(position).getName());
        double remaining =budget-used;
        holder.budgetView.setText(String.valueOf(remaining));

        int percentage = Integer.valueOf(String.valueOf(Math.round((used/budget)*100)));

        holder.progressBar.setMax(100);
        holder.progressBar.setProgress(percentage);
        String progress=used+" / "+budget;
        holder.progressText.setText(progress);
        if (remaining<0){
            holder.budgetView.setTextColor(Color.RED);
            holder.progressText.setTextColor(Color.RED);
            holder.progressBar.setVisibility(View.GONE);

        }if (percentage<50){
            holder.progressText.setTextColor(Color.rgb(85,181,62));
        }



    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView categoryNameView;
        TextView budgetView;
        ProgressBar progressBar;
        TextView progressText;
        public RecyclerViewHolder(View itemView,FragmentManager fragmentManager) {
            super(itemView);


            categoryNameView= (TextView)itemView.findViewById(R.id.budget_category_name_cat);
            budgetView= (TextView)itemView.findViewById(R.id.budget_remaining_value);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
            progressText = (TextView)itemView.findViewById(R.id.progresstext);

        }
    }
}
