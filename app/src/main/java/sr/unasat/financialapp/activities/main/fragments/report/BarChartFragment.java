package sr.unasat.financialapp.activities.main.fragments.report;


import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Transaction;

import static sr.unasat.financialapp.util.DateUtil.convertDate;

/**
 */
public class BarChartFragment extends Fragment {

    public String bartype;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.graph_barchart, container, false);
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
        HashMap<String,List<Transaction>> days= new Dao(getContext()).getTransactionsLast7Days();
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        String[] dayArr=new String[7];
        List<Transaction> nullTransactions=new ArrayList<>();



        staticLabelsFormatter.setHorizontalLabels(new String[] {"","mon", "tue", "wed","thu","fri","yesterday","today",""});


        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0,0),
                new DataPoint(1, 17),
                new DataPoint(2, 56),
                new DataPoint(3, 34),
                new DataPoint(4, 25),
                new DataPoint(5, 56),
                new DataPoint(6, 39),
                new DataPoint(7, 120),
                new DataPoint(8,0)

        });
        graph.addSeries(series);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);// It will remove the background grids

        //graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);// remove horizontal x labels and line
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);

// remove vertical labels and lines



        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if (data.getY()>100){
                    return Color.RED;
                }else if(data.getY()>50){
                    return Color.BLUE;
                }else{
                    return Color.GREEN;
                }
            }
        });

        series.setSpacing(20);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        view.findViewById(R.id.period_choise).setVisibility(View.GONE);
        view.findViewById(R.id.period_spinner).setVisibility(View.GONE) ;

        days.clone();
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
