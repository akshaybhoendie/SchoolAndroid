package sr.unasat.financialapp.activities.main.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.arrayadapters.ReportRecyclerAdapter;
import sr.unasat.financialapp.arrayadapters.ViewPagerAdapter;

public class ReportsFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_reports, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        viewPagerAdapter=new ViewPagerAdapter(getFragmentManager());
        viewPagerAdapter.addfragments(new ReportExpenseFragment(),"expense");
        viewPagerAdapter.addfragments(new ReportIncomeFragment(),"income");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
/*  recyclerView = (RecyclerView)view.findViewById(R.id.rep_recycler);
        List<String>reports=new ArrayList<>();
        String[] reportsArr={"expense by category","daily expense","monthly expense"};

        for (String s:reportsArr){
            reports.add(s);
        }

        adapter = new ReportRecyclerAdapter(reports);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);*/