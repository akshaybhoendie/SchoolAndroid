package sr.unasat.financialapp.activities.main.fragments.report;


import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import sr.unasat.financialapp.R;

/**
 */
public class BarChartFragment extends Fragment {

    public String bartype;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bar_chart, container, false);
        switch (bartype){
            case "daily expenses":
                dailyExpenses(view);
                break;
            case "monthly expenses":
                monthlyExpenses(view);
                break;
            case "daily income":
                dailyIncome(view);
                break;
            case "monthly income":
                monthlyIncome(view);
                break;
            default:
                break;
        }

        return view;
    }
    public void dailyExpenses(View view){

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);


    }

    public void dailyIncome(View view){

        Toast.makeText(view.getContext(), "daily income", Toast.LENGTH_SHORT).show();
    }

    private void monthlyExpenses(View view) {
        Toast.makeText(view.getContext(), "monthly expense", Toast.LENGTH_SHORT).show();
    }

    private void monthlyIncome(View view) {

        Toast.makeText(view.getContext(), "monthly income", Toast.LENGTH_SHORT).show();
    }


}
