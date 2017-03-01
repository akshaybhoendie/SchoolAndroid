package sr.unasat.financialapp.activities.main.fragments.report;


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
import sr.unasat.financialapp.adapters.ReportRecyclerAdapter;

/***/
public class ReportExpenseFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_expense, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rep_recycler);
        List<String> reports=new ArrayList<>();
        String[] reportsArr={"expense by category","daily expenses","monthly expenses"};

        Collections.addAll(reports, reportsArr);

        ReportRecyclerAdapter adapter = new ReportRecyclerAdapter(reports,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        return view;
    }

}
