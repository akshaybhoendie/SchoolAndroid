package sr.unasat.financialapp.activities.main.fragments.report;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Collections;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.dialogs.MonthPickerDialog;
import sr.unasat.financialapp.adapters.CategoryRecyclerAdapterWithBar;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;

import static sr.unasat.financialapp.activities.main.MainActivity.monthPickerDialog;
import static sr.unasat.financialapp.activities.main.fragments.dialogs.MonthPickerDialog.pieGraphType;

public class PieChartFragment extends Fragment {

    public String bartype;
    private String period="today";
    PieChart pieChart;
    View theView;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.graph_piechart, container, false);

        bundle = getArguments();
        pieChart=(PieChart) theView.findViewById(R.id.piechart);
        String[] items = {"today","this month","choose month","all past transactions"};
        ArrayAdapter adapter= new ArrayAdapter(getContext(),R.layout.spinner_layout,R.id.spinner_item,items);
        Spinner spinner= (Spinner) theView.findViewById(R.id.spinner_piechart);
        spinner.setAdapter(adapter);


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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,View view, int position, long id) {
                switch (String.valueOf(parent.getItemAtPosition(position))) {

                    case "today":
                        period = "today";
                        if (bartype.equals("expense by category")) {
                            expenseByCategory(theView, pieChart);
                        }else {
                            incomeByCategory(theView,pieChart);
                        }
                        break;
                    case "this month":
                        period = "this month";

                        if (bartype.equals("expense by category")) {
                            expenseByCategory(theView, pieChart);
                        }else {
                            incomeByCategory(theView,pieChart);
                        }
                        break;
                    case "choose month":
                        period = "choose month";

                            pieGraphType=bartype;
                            monthPickerDialog=new MonthPickerDialog();
                            monthPickerDialog.pickMonthFor="pieChart";
                            monthPickerDialog.show(getActivity().getFragmentManager(),"monthpicker");

                        break;
                    case "all past transactions":
                        period = "all past transactions";

                        if (bartype.equals("expense by category")) {
                            expenseByCategory(theView, pieChart);
                        }else {
                            incomeByCategory(theView,pieChart);
                        }
                        break;
                }
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        switch (bartype){
            case "expense by category":
                expenseByCategory(theView,pieChart);
                break;
            case "income by category":
                incomeByCategory(theView,pieChart);
                break;
        }

        return theView;
    }

    private void incomeByCategory(View view,PieChart pieChart) {
        Dao dao=new Dao(getContext());
        int month=0;
        int year=0;
        ArrayList<PieEntry> yentries= new ArrayList<>();
        ArrayList<String> xEntries =new ArrayList<>();
        List<Category>categories=dao.getCategories();
        for (int i=0;i<categories.size();i++){
            if (categories.get(i).getId()!=2){
                categories.remove(i);
                i=0;
            }
        }
        float[] data=new float[categories.size()];
        String[] xdata=new String[categories.size()];
        List<Category>categoriesWithValues=new ArrayList<>();
        double totalValue=0;

        switch (period){
            case "today":
                for (int i = 0;i<categories.size();i++ ){
                    double value=0;
                    if (bundle!=null){
                        period="choose month";
                        month=bundle.getInt("month");
                        year=bundle.getInt("year");
                        value=dao.getCategoryValuesByMonth(categories.get(i),year,month);

                    }else {
                        value = dao.getCategoryValuesToDay(categories.get(i));
                    }

                    data[i]=(float)value;
                    xdata[i]= categories.get(i).getName();
                    totalValue=totalValue+value;
                    if(value!=0){
                        categoriesWithValues.add(categories.get(i));
                    }
                }


                break;
            case "this month":
                for (int i = 0;i<categories.size();i++ ){

                    double value=dao.getAmountUsedByCategoryCurrentMonth(categories.get(i));
                    data[i]=(float)value;
                    xdata[i]= categories.get(i).getName();
                    totalValue=totalValue+value;
                    if(value!=0){
                        categoriesWithValues.add(categories.get(i));
                    }
                }

                break;
            case "choose month":
                for (int i = 0;i<categories.size();i++ ){

                    double value=dao.getCategoryValuesToDay(categories.get(i));
                    data[i]=(float)value;
                    xdata[i]= categories.get(i).getName();
                    totalValue=totalValue+value;
                    if(value!=0){
                        categoriesWithValues.add(categories.get(i));
                    }
                }
                Toast.makeText(getContext(), "showing you todays pie", Toast.LENGTH_SHORT).show();
                break;

            case "all past transactions":
                for (int i = 0;i<categories.size();i++ ){

                    double value=dao.getCategoryValues(categories.get(i));
                    data[i]=(float)value;
                    xdata[i]= categories.get(i).getName();
                    totalValue=totalValue+value;
                    if(value!=0){
                        categoriesWithValues.add(categories.get(i));
                    }
                }

                break;
        }


        for (int i=0;i<data.length;i++){
            yentries.add(new PieEntry(data[i],i));
        }
        Collections.addAll(xEntries, xdata);

        ArrayList<Integer>colors=new ArrayList<>();
        colors.add(Color.GREEN);


        PieDataSet set=new PieDataSet(yentries,"first pie");

        set.setColors(colors);
        set.setSliceSpace(4f);
        set.setValueTextSize(0);

        PieData piedata = new PieData(set);
        pieChart.setData(piedata);
        pieChart.setCenterText("total expenses:\n"+String.valueOf(totalValue));
        pieChart.invalidate();

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.pie_listview);
        CategoryRecyclerAdapterWithBar adapterWithBar=new CategoryRecyclerAdapterWithBar
                (categoriesWithValues,totalValue,period,year,month,getContext(),getFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapterWithBar);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //Toast.makeText(getContext(), String.valueOf(e.getX()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


    Toast.makeText(view.getContext(), "income cat", Toast.LENGTH_SHORT).show();
    }

    private void expenseByCategory(View view,PieChart pieChart) {

        Dao dao=new Dao(getContext());
        int month=0;
        int year=0;
        ArrayList<PieEntry> yentries= new ArrayList<>();
        ArrayList<String> xEntries =new ArrayList<>();
        List<Category>categories=dao.getCategories();
        for (int i=0;i<categories.size();i++){
            if (categories.get(i).getId()==2){
                categories.remove(i);
                i=0;
            }
        }
        float[] data=new float[categories.size()];
        String[] xdata=new String[categories.size()];
        List<Category>categoriesWithValues=new ArrayList<>();
        double totalValue=0;

        switch (period){
            case "today":

                for (int i = 0;i<categories.size();i++ ){
                    double value;
                    if (bundle!=null){
                        period="choose month";
                        month=bundle.getInt("month");
                        year=bundle.getInt("year");
                        value=dao.getCategoryValuesByMonth(categories.get(i),year,month);

                    }else {
                        value = dao.getCategoryValuesToDay(categories.get(i));
                    }
                    data[i]=(float)value;
                    xdata[i]= categories.get(i).getName();
                    totalValue=totalValue+value;
                    if(value!=0){
                        categoriesWithValues.add(categories.get(i));
                    }
                }


                break;
            case "this month":
                for (int i = 0;i<categories.size();i++ ){

                    double value=dao.getAmountUsedByCategoryCurrentMonth(categories.get(i));
                    data[i]=(float)value;
                    xdata[i]= categories.get(i).getName();
                    totalValue=totalValue+value;
                    if(value!=0){
                        categoriesWithValues.add(categories.get(i));
                    }
                }

                break;
            case "choose month":
                for (int i = 0;i<categories.size();i++ ){

                    double value=dao.getCategoryValuesToDay(categories.get(i));
                    data[i]=(float)value;
                    xdata[i]= categories.get(i).getName();
                    totalValue=totalValue+value;
                    if(value!=0){
                        categoriesWithValues.add(categories.get(i));
                    }
                }
                Toast.makeText(getContext(), "showing you todays pie", Toast.LENGTH_SHORT).show();
                break;
            case "all past transactions":
                for (int i = 0;i<categories.size();i++ ){

                    double value=dao.getCategoryValues(categories.get(i));
                    data[i]=(float)value;
                    xdata[i]= categories.get(i).getName();
                    totalValue=totalValue+value;
                    if(value!=0){
                        categoriesWithValues.add(categories.get(i));
                    }
                }

                break;
        }


        for (int i=0;i<data.length;i++){
            yentries.add(new PieEntry(data[i],i));
        }
        Collections.addAll(xEntries, xdata);

        ArrayList<Integer>colors=new ArrayList<>();

        for (Category category:categoriesWithValues){
            colors.add(category.getColor());
        }

        PieDataSet set=new PieDataSet(yentries,"first pie");

        set.setColors(colors);
        set.setSliceSpace(4f);
        set.setValueTextSize(0);

        PieData piedata = new PieData(set);
        pieChart.setData(piedata);
        pieChart.setCenterText("total expenses:\n"+String.valueOf(totalValue));
        pieChart.invalidate();

        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.pie_listview);
        CategoryRecyclerAdapterWithBar adapterWithBar=new CategoryRecyclerAdapterWithBar
                (categoriesWithValues,totalValue,period,year,month,getContext(),getFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapterWithBar);

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
