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

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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

        TextView typeBar =(TextView)view.findViewById(R.id.type_bar);
        typeBar.setText("daily expenses");

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        HashMap<String,List<Transaction>> days= new Dao(getContext()).getTransactionsLast7Days();
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        String[] dayArr=new String[7];
        List<String>daysList=new ArrayList<>();
        double[] dayAmount=new double[7];

        for (String s:days.keySet()){
            daysList.add(s);
        }

        Collections.sort(daysList);
        Collections.reverse(daysList);


        for(int i = 0; i <7 ; i++){
            Transaction transaction=days.get(daysList.get(i)).get(0);

            dayArr[i] = transaction.getTran_date();
            double dayTotalAmount=0;
            for (Transaction transaction1: days.get(daysList.get(i))){
                dayTotalAmount=dayTotalAmount+transaction1.getTran_amount();
            }
            dayAmount[i] = dayTotalAmount;

        }




        staticLabelsFormatter.setHorizontalLabels(dayArr);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{

                new DataPoint(0, dayAmount[0]),
                new DataPoint(1, dayAmount[1]),
                new DataPoint(2, dayAmount[2]),
                new DataPoint(3, dayAmount[3]),
                new DataPoint(4, dayAmount[4]),
                new DataPoint(5, dayAmount[5]),
                new DataPoint(6, dayAmount[6]),

        });
        graph.addSeries(series);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);// It will remove the background grids

        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);// remove horizontal x labels and line
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

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getContext(), "$"+String.valueOf(dataPoint.getY()), Toast.LENGTH_SHORT).show();
            }
        });

        series.setSpacing(20);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        view.findViewById(R.id.period_choise).setVisibility(View.GONE);
        view.findViewById(R.id.period_spinner).setVisibility(View.GONE) ;


    }

    public void dailyIncome(View view){

        Toast.makeText(view.getContext(), "daily income", Toast.LENGTH_SHORT).show();
    }

    private void monthlyExpenses(View view) {
        TextView typeBar =(TextView)view.findViewById(R.id.type_bar);
        typeBar.setText("monthly expenses");

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);

        Toast.makeText(view.getContext(), "monthly expense", Toast.LENGTH_SHORT).show();
        Dao dao=new Dao(getContext());
        Date date= Calendar.getInstance().getTime();
        String[] monthArr =new String[12];
        double[] monthAmount = new double[12];
        HashMap<String,List<Transaction>>months=dao.getTransactionsLast12Months();
        List<String>monthList=new ArrayList<>();

        for (String s:months.keySet()){
            monthList.add(s);
        }

        Collections.sort(monthList);
        Collections.reverse(monthList);


        for (int i =0;i<12;i++){
            Transaction transaction = months.get(monthList.get(i)).get(0);
            monthArr[i] = transaction.getTran_date();
            double monthTotalAmount=0;
            for (Transaction transaction1: months.get(monthList.get(i))){
                monthTotalAmount=monthTotalAmount+transaction1.getTran_amount();
            }
            monthAmount[i]= monthTotalAmount;

        }

        staticLabelsFormatter.setHorizontalLabels(monthArr);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{

                new DataPoint(0, monthAmount[0]),
                new DataPoint(1, monthAmount[1]),
                new DataPoint(2, monthAmount[2]),
                new DataPoint(3, monthAmount[3]),
                new DataPoint(4, monthAmount[4]),
                new DataPoint(5, monthAmount[5]),
                new DataPoint(6, monthAmount[6]),
                new DataPoint(7, monthAmount[7]),
                new DataPoint(8, monthAmount[8]),
                new DataPoint(9, monthAmount[9]),
                new DataPoint(10, monthAmount[10]),
                new DataPoint(11, monthAmount[11])


        });
        graph.addSeries(series);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);// It will remove the background grids

        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);// remove horizontal x labels and line
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

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getContext(), "$"+String.valueOf(dataPoint.getY()), Toast.LENGTH_SHORT).show();
            }
        });

        series.setSpacing(40);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

    }

    private void monthlyIncome(View view) {

        Toast.makeText(view.getContext(), "monthly income", Toast.LENGTH_SHORT).show();
    }


}
