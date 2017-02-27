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
import sr.unasat.financialapp.arrayadapters.ReportRecyclerAdapter;

public class ReportsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReportRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_reports, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rep_recycler);
        List<String>reports=new ArrayList<>();
        String[] reportsArr={"expense by category","daily expense","monthly expense"};

        for (String s:reportsArr){
            reports.add(s);
        }

        adapter = new ReportRecyclerAdapter(reports);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        return view;
    }

}
