package sr.unasat.financialapp.activities.main.fragments.report;


import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
    final String spinnerItem1 ="last 6 months";
    final String spinnerItem2 ="last 12 months";
    final String spinnerItem3 ="last year";
    final String spinnerItem4 ="choose year";
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.graph_barchart, container, false);
        this.view=view;
        TextView typeBar =(TextView)view.findViewById(R.id.type_bar);
        switch (bartype){
            case "daily expenses":

                typeBar.setText("daily expenses");
                dailyExpenses(view);
                break;
            case "monthly expenses":
                typeBar.setText("monthly expenses");
                monthlyExpensesChoise(view);
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
        graph.removeAllSeries();
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

    private void monthlyExpenses(int range,int year) {

        if (year==0){
            view.findViewById(R.id.choose_year).setVisibility(View.GONE);
            view.findViewById(R.id.year_spinner).setVisibility(View.GONE);
            year=convertDate(Calendar.getInstance().getTime())[0];
        }
        if (year==-1){
            view.findViewById(R.id.choose_year).setVisibility(View.GONE);
            view.findViewById(R.id.year_spinner).setVisibility(View.GONE);
            year=convertDate(Calendar.getInstance().getTime())[0]-1;
        }


        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        graph.removeAllSeries();

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);

        Toast.makeText(view.getContext(), "monthly expense", Toast.LENGTH_SHORT).show();
        Dao dao=new Dao(getContext());

        String[] monthArr =new String[range];
        double[] monthAmount = new double[range];
        HashMap<String,List<Transaction>>months=dao.getTransactions12Months(year);
        List<String>monthList=new ArrayList<>();
        List<Integer>monthListInt=new ArrayList<>();

        for (String s:months.keySet()){
            monthListInt.add(Integer.valueOf(s));
        }

        Collections.sort(monthListInt);
        Collections.reverse(monthListInt);

        for (int i:monthListInt){
            monthList.add(String.valueOf(i));
        }

        int a=0;
        for (int i =0;i<12;i++){
            Transaction transaction = months.get(monthList.get(i)).get(0);

            if (i>=range&&range==6) {
                monthArr[a] = transaction.getTran_date();
            }else if(range!=6){
                monthArr[i] = transaction.getTran_date();
            }
            double monthTotalAmount=0;
            for (Transaction transaction1: months.get(monthList.get(i))){
                monthTotalAmount=monthTotalAmount+transaction1.getTran_amount();
            }
            if (i>=range&&range==6) {
                monthAmount[a] = monthTotalAmount;
                a++;
            }else if (range!=6){
                monthAmount[i] = monthTotalAmount;
            }
        }

        staticLabelsFormatter.setHorizontalLabels(monthArr);

        DataPoint[] dataPoints=new DataPoint[range];

        for (int i=0;i<range;i++){

            dataPoints[i]=new DataPoint(i,monthAmount[i]);

        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dataPoints);
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

    private void monthlyExpensesChoise(View view){

        Spinner spinner=(Spinner)view.findViewById(R.id.period_spinner);


        final String[] items = {spinnerItem1,spinnerItem2,spinnerItem3,spinnerItem4};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.spinner_item, items);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (String.valueOf(parent.getItemAtPosition(position))){

                    case spinnerItem1:

                        monthlyExpenses(6,0);
                        Toast.makeText(getContext(), spinnerItem1, Toast.LENGTH_SHORT).show();
                        break;
                    case spinnerItem2:

                        monthlyExpenses(12,0);
                        break;
                    case spinnerItem3:
                        monthlyExpenses(12,-1);
                        break;
                    case spinnerItem4:
                        monthlyExpenseByYear();
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void monthlyExpenseByYear(){
        int year=convertDate(Calendar.getInstance().getTime())[0];
        TextView chooseYear=(TextView)view.findViewById(R.id.choose_year);
        chooseYear.setVisibility(View.VISIBLE);

        Spinner spinner=(Spinner)view.findViewById(R.id.year_spinner);
        String[] years= new String[5];

        for (int i = 0; i<5;i++){

            years[i] = String.valueOf(convertDate(Calendar.getInstance().getTime())[0]-i);

        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>
                (getContext(),R.layout.spinner_layout,R.id.spinner_item,years);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthlyExpenses(12,Integer.valueOf(String.valueOf(parent.getItemAtPosition(position))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
