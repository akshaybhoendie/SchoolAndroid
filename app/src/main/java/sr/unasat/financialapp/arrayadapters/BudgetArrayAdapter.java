package sr.unasat.financialapp.arrayadapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import sr.unasat.financialapp.dto.Budget;

/**
 * Created by abhoendie on 2/23/2017.
 */

public class BudgetArrayAdapter extends RecyclerView.Adapter{

    ArrayList <Budget> budgets = new ArrayList<Budget>();

    public BudgetArrayAdapter(ArrayList <Budget> budgets) {
       this.budgets = budgets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
