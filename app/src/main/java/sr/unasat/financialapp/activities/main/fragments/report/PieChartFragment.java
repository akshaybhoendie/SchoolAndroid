package sr.unasat.financialapp.activities.main.fragments.report;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;
import sr.unasat.financialapp.dto.Transaction;

public class PieChartFragment extends Fragment {

    public String bartype;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.graph_piechart, container, false);

        String[] items = {"today","this month","choose month","all past transactions"};
        ArrayAdapter adapter= new ArrayAdapter(getContext(),R.layout.spinner_layout,R.id.spinner_item,items);
        Spinner spinner= (Spinner)view.findViewById(R.id.spinner_piechart);
        spinner.setAdapter(adapter);

        PieChart pieChart=(PieChart)view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(80f);
        pieChart.setTransparentCircleAlpha(30);
        pieChart.setCenterTextSize(10);
        pieChart.setRotationEnabled(false);
        pieChart.setTransparentCircleColor(Color.BLACK);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        switch (bartype){
            case "expense by category":
                expenseByCategory(view,pieChart);
                break;
            case "income by category":
                incomeByCategory(view,pieChart);
                break;
        }

        return view;
    }

    private void incomeByCategory(View view,PieChart pieChart) {
        Toast.makeText(view.getContext(), "income cat", Toast.LENGTH_SHORT).show();
    }

    private void expenseByCategory(View view,PieChart pieChart) {


        Dao dao=new Dao(getContext());
        ArrayList<PieEntry> yentries= new ArrayList<>();
        ArrayList<String> xEntries =new ArrayList<>();
        List<Category>categories=dao.getCategories();
        float[] data=new float[categories.size()];
        String[] xdata=new String[categories.size()];
        double totalValue=0;
        for (int i = 0;i<categories.size();i++ ){

            double value=dao.getCategoryValuesToDay(categories.get(i));
            data[i]=(float)value;
            xdata[i]= categories.get(i).getName();
            totalValue=totalValue+value;
        }



        for (int i=0;i<data.length;i++){
            yentries.add(new PieEntry(data[i],i));
        }
        for (int i=0;i<xdata.length;i++){
            xEntries.add(xdata[i]);
        }
        ArrayList<Integer>colors=new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);

        PieDataSet set=new PieDataSet(yentries,"first pie");

        set.setColors(colors);
        set.setSliceSpace(4f);
        set.setValueTextSize(0);

        PieData piedata = new PieData(set);
        pieChart.setData(piedata);
        pieChart.setCenterText("total expenses:\n"+String.valueOf(totalValue));
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Toast.makeText(getContext(), String.valueOf(e.getX()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        Toast.makeText(view.getContext(), "expense cat", Toast.LENGTH_SHORT).show();
    }


}
