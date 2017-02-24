package sr.unasat.financialapp.activities.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import sr.unasat.financialapp.R;
import sr.unasat.financialapp.arrayadapters.DateGroupExpendableAdapter;
import sr.unasat.financialapp.arrayadapters.YearGroupExpandableAdapter;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Transaction;

import static sr.unasat.financialapp.util.DateUtil.convertDate;

public class BalanceFragment extends Fragment {

     Date date ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_balance, container, false);

        Dao dao = new Dao(getActivity());
        final String spinnerItem1 ="this month";
        final String spinnerItem2 ="last month";
        final String spinnerItem3 ="all transactions";

        Spinner spinner = (Spinner)view.findViewById(R.id.balance_month_spinner);
        final String[] items = {spinnerItem1,spinnerItem2,spinnerItem3};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.spinner_item, items);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               date=Calendar.getInstance().getTime();


                switch (String.valueOf(parent.getItemAtPosition(position))){

                    case spinnerItem1:
                        setDays(date,0,0);
                        break;
                    case spinnerItem2:
                        int month=convertDate(date)[1];
                        if (month==1){
                            setDays(date,12,0);
                        }else{
                            setDays(date,month-1,0);
                        }
                        break;

                    case spinnerItem3:
                        groupByYear();
                        break;


                }

                Toast.makeText(getActivity(), String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextView closingView = (TextView)view.findViewById(R.id.balance_closing_value);



        closingView.setText(String.valueOf(dao.getUserById(1).getClosing()));


        return view;
    }

    public void setDays(Date date,int month,int year){
        getView().findViewById(R.id.group_listView).refreshDrawableState();
        Dao dao=new Dao(getActivity());

                int[] dateArr = convertDate(date);

            if (month == 0||year==0) {
                month = dateArr[1];
                year = dateArr[0];
            }
            List<String> days = dao.getDays(month, year);


            //day+" "+int_to_month(month)+" "+year;


            HashMap<String, List<String>> transactions = new HashMap<>();

            List<String> tranNames;


            for (String theDay : days) {

                String[] array = theDay.split("\\s");
                int day = Integer.valueOf(array[0]);
                tranNames = new ArrayList<>();
                List<Transaction> list = dao.getTransactionsByDay(day, month, year);
                for (Transaction transaction : list) {

                    tranNames.add(transaction.getTran_name());
                    transactions.put(theDay, tranNames);

                }


            }


            ExpandableListView groupListView = (ExpandableListView) getView().findViewById(R.id.group_listView);

            DateGroupExpendableAdapter adapter = new DateGroupExpendableAdapter(days, transactions, getContext());

            groupListView.setAdapter(adapter);




        }

    private void groupByYear(){

        Dao dao = new Dao(getActivity());
        List<Transaction>transactions=dao.getTransactions();

        List<String> years=dao.getTransactionYears();
        HashMap<String ,List<String>> months=new HashMap<>();

        for (String year : years) {

            months.put(year,dao.getTransactionMonthsByYear(Integer.valueOf(year)));

        }
        ExpandableListView groupListView = (ExpandableListView) getView().findViewById(R.id.group_listView);

        YearGroupExpandableAdapter adapter = new YearGroupExpandableAdapter(years, months, getContext());

        groupListView.setAdapter(adapter);



    }


}







/*setEndDate(year,month+1,dayOfMonth);
                if (counter3!=0) {

                    setDays(date, 0);
                }
                counter3++;
                if (counter3==2){
                    counter3=0;
                }*/