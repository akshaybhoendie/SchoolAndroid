package sr.unasat.financialapp.activities.main.fragments.report;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import sr.unasat.financialapp.R;

public class PieChartFragment extends Fragment {

    public String bartype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.graph_piechart, container, false);

        switch (bartype){
            case "expense by category":
                expenseByCategory(view);
                break;
            case "income by category":
                incomeByCategory(view);
                break;
        }

        return view;
    }

    private void incomeByCategory(View view) {
        Toast.makeText(view.getContext(), "income cat", Toast.LENGTH_SHORT).show();
    }

    private void expenseByCategory(View view) {
        Toast.makeText(view.getContext(), "expense cat", Toast.LENGTH_SHORT).show();
    }


}
