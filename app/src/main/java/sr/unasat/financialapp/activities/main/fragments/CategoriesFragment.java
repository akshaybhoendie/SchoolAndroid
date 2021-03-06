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
import sr.unasat.financialapp.adapters.CategoryRecyclerAdapter;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;

public class CategoriesFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.main_fragment_categories, container, false);
        Dao dao = new Dao(getActivity());
        List<Category>categories=dao.getCategories();
        List<String>categoriesStr = new ArrayList<>();
        for (Category category : categories){

            categoriesStr.add(category.getName());

        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cat_recycler);
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(categoriesStr, getContext(), getFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        return view;

    }

}
