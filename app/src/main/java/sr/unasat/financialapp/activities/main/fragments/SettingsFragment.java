package sr.unasat.financialapp.activities.main.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.adapters.SettingsRecyclerAdapter;

public class SettingsFragment extends Fragment {

    String option1,option2,option3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.main_fragment_settings, container, false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.settings_recycler);

        option1="edit currency";option3="reset account";option2="backup database";
        List<String>options=new ArrayList<>();
        options.add(option1);
        options.add(option2);
        options.add(option3);

        SettingsRecyclerAdapter adapter= new SettingsRecyclerAdapter(options,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        return view;

    }

}
