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
import java.util.Collections;
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
        String[] dayArr=new String[9];
        List<String>daysList=new ArrayList<>();
        double[] dayAmount=new double[9];

        for (String s:days.keySet()){
            daysList.add(s);
        }

        Collections.sort(daysList);
        Collections.reverse(daysList);


        for(int i = 1; i <8 ; i++){
            Transaction transaction=days.get(daysList.get(i-1)).get(0);

            dayArr[i] = transaction.getTran_date();
            double dayTotalAmount=0;
            for (Transaction transaction1: days.get(daysList.get(i-1))){
                dayTotalAmount=dayTotalAmount+transaction1.getTran_amount();
            }
            dayAmount[i] = dayTotalAmount;

        }

        dayArr[0]="0";dayArr[8]="0";
        dayAmount[0]=0;dayAmount[8]=0;


        staticLabelsFormatter.setHorizontalLabels(dayArr);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0,0),
                new DataPoint(1, dayAmount[1]),
                new DataPoint(2, dayAmount[2]),
                new DataPoint(3, dayAmount[3]),
                new DataPoint(4, dayAmount[4]),
                new DataPoint(5, dayAmount[5]),
                new DataPoint(6, dayAmount[6]),
                new DataPoint(7, dayAmount[7]),
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
