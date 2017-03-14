package sr.unasat.financialapp.activities.main.fragments.overview;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.report.PieChartFragment;
import sr.unasat.financialapp.adapters.BalanceAdapter;
import sr.unasat.financialapp.adapters.CategoryRecyclerAdapterWithBar;
import sr.unasat.financialapp.adapters.TransactionRecycledAdapter;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;
import sr.unasat.financialapp.dto.Transaction;

import static sr.unasat.financialapp.util.DateUtil.convertDate;
import static sr.unasat.financialapp.util.DateUtil.int_to_day;
import static sr.unasat.financialapp.util.DateUtil.int_to_month;

public class OverviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.main_fragment_overview, container, false);

        BalanceAdapter adapter = new BalanceAdapter(getContext());
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.balance_recycler);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(getContext(),1,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollTo((int)recyclerView.getX()/2,0);


        recyclerView.setAdapter(adapter);



        GraphView graph = (GraphView) view.findViewById(R.id.overview_bar);
        graph.removeAllSeries();
        final HashMap<String,List<Transaction>> days= new Dao(getContext()).getTransactionsLast7Days();
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
                if (transaction1.getCategory()!=null) {
                    if (transaction1.getCategory().getId() != 2) {
                        dayTotalAmount = dayTotalAmount + transaction1.getTran_amount();
                    }
                }else{
                    dayTotalAmount = dayTotalAmount + transaction1.getTran_amount();
                }

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


        List<Double>val=new ArrayList<>();
        for (double num:dayAmount){
            val.add(num);
        }
        Collections.sort(val);
        final double highest=val.get(val.size()-1);
        final double lowest=val.get(0);



        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {

                if (data.getY()==highest){
                    return Color.RED;
                }else if (data.getY()==lowest){
                    return Color.GREEN;
                }
                return Color.BLUE;
            }
        });


        series.setSpacing(20);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.setTitle("this month : "+int_to_month(convertDate(Calendar.getInstance().getTime())[1]));


        Dao dao=new Dao(getContext());
        int month=0;
        int year=0;
        ArrayList<PieEntry> yentries= new ArrayList<>();
        final ArrayList<String> xEntries =new ArrayList<>();
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

        for (int i = 0;i<categories.size();i++ ){
            double value;

            value = dao.getCategoryValuesToDay(categories.get(i));

            data[i]=(float)value;
            xdata[i]= categories.get(i).getName();
            totalValue=totalValue+value;
            if(value!=0){
                categoriesWithValues.add(categories.get(i));
            }
        }
        for (int i=0;i<data.length;i++){
            yentries.add(new PieEntry(data[i],categories.get(i)));


        }
        Collections.addAll(xEntries, xdata);

        PieDataSet set=new PieDataSet(yentries,"first pie");
        ArrayList<Integer>colors=new ArrayList<>();


        for (Category cat:categories){
            colors.add(cat.getColor());
        }


        set.setColors(colors);
        set.setSliceSpace(4f);
        set.setValueTextSize(0);


        PieData piedata = new PieData(set);
        PieChart pieChart=(PieChart)view.findViewById(R.id.overview_pie);
        pieChart.setData(piedata);
        pieChart.setCenterText("total expenses:\n"+String.valueOf(totalValue));
        int[] date=convertDate(Calendar.getInstance().getTime());

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieChartFragment fragment = new PieChartFragment();
                fragment.bartype = "expense by category";
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,
                        fragment).addToBackStack("lo").commit();

            }

            @Override
            public void onNothingSelected() {

            }
        });


            Description descr=new Description();
        descr.setText("data for today : "+ int_to_day(date[3])+" "+date[4]+" "+int_to_month(date[1]));
        pieChart.setDescription(descr);
        pieChart.setRotationEnabled(false);
        pieChart.setClickable(false);
        pieChart.setEnabled(false);
        pieChart.invalidate();

        RecyclerView recyclerView2=(RecyclerView)view.findViewById(R.id.pie_recycle_overview);
        CategoryRecyclerAdapterWithBar adapterWithBar=new CategoryRecyclerAdapterWithBar
                (categoriesWithValues,totalValue,"today",year,month,getContext(),getFragmentManager());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setEnabled(false);


        recyclerView2.setAdapter(adapterWithBar);


        return view;
    }
}
