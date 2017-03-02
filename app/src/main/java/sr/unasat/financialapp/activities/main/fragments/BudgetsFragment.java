package sr.unasat.financialapp.activities.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.adapters.BudgetRecyclerAdapter;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;
import sr.unasat.financialapp.dto.Transaction;

public class BudgetsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.main_fragment_budgets, container, false);

        Dao dao=new Dao(getContext());

        List<Category> categories = dao.getCategories();

        BudgetRecyclerAdapter adapter = new BudgetRecyclerAdapter(categories,getContext(),getFragmentManager());
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.budget_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        return view;
    }

}
