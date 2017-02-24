package sr.unasat.financialapp.activities.main.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Transaction;

import static sr.unasat.financialapp.util.DateUtil.convertDate;

public class BalanceFragment extends Fragment {

    int startyear,startmonth,startday,endyear,endmonth,endday;int counter=0;int counter2=0,counter3=0; Date date ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_balance, container, false);

        counter = counter2 = counter3=0;
        Dao dao = new Dao(getActivity());
        final String spinnerItem1 ="this month";
        final String spinnerItem2 ="last month";
        final String spinnerItem3 ="custom date range";

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
                        setDays(date,0);
                        break;
                    case spinnerItem2:
                        int month=convertDate(date)[1];
                        if (month==1){
                            setDays(date,12);
                        }else{
                            setDays(date,month-1);
                        }
                        break;

                    case spinnerItem3:
                        int[] array = convertDate(date);
                        openDatePicker(array[0],array[1],array[2]);

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

    private void setDays(Date date,int month){
        Dao dao=new Dao(getActivity());

        if (month==0&&counter2!=0) {

            List<String> days = new ArrayList<>();
            int[] years = new int[(endyear-startyear)+1];
            int nyear=startyear;
            for (int i=1;i<years.length;i++){
                years[i]=nyear+1;
                nyear++;
            }
            years[0]=startyear;
            HashMap<String, List<String>> transactions = new HashMap<>();
            List<String> tranNames;

            for (int i =1;i<years.length;i++) {
                for (int year:years) {
                    List<String> listDays = dao.getDays(i, year);

                    for (String theDay : listDays) {

                        String[] array = theDay.split("\\s");
                        int day = Integer.valueOf(array[0]);
                        tranNames = new ArrayList<>();
                        List<Transaction> list = dao.getTransactionsByDay(day, i, year);
                        for (Transaction transaction : list) {

                            tranNames.add(transaction.getTran_name());
                            transactions.put(theDay, tranNames);
                        }
                        days.add(theDay);
                    }
                }
            }


            ExpandableListView groupListView = (ExpandableListView) getView().findViewById(R.id.group_listView);

            DateGroupExpendableAdapter adapter = new DateGroupExpendableAdapter(days, transactions, getContext());

            groupListView.setAdapter(adapter);


        }
        else {


            counter2++;
            int[] dateArr = convertDate(date);

            if (month == 0) {
                month = dateArr[1];
            }
            List<String> days = dao.getDays(month, dateArr[0]);


            //day+" "+int_to_month(month)+" "+year;


            HashMap<String, List<String>> transactions = new HashMap<>();

            List<String> tranNames;


            for (String theDay : days) {

                String[] array = theDay.split("\\s");
                int day = Integer.valueOf(array[0]);
                tranNames = new ArrayList<>();
                List<Transaction> list = dao.getTransactionsByDay(day, month, dateArr[0]);
                for (Transaction transaction : list) {

                    tranNames.add(transaction.getTran_name());
                    transactions.put(theDay, tranNames);

                }


            }


            ExpandableListView groupListView = (ExpandableListView) getView().findViewById(R.id.group_listView);

            DateGroupExpendableAdapter adapter = new DateGroupExpendableAdapter(days, transactions, getContext());

            groupListView.setAdapter(adapter);
        }

    }




    private void openDatePicker(int year1,int month1,int day1){

        Dialog startdiag = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setStartDate(year,month+1,dayOfMonth);

                if (counter>0){
                    openEndDatePicker(startyear,startmonth,startday);
                }
                counter++;

            }

        }, year1, month1, day1);
        startdiag.setTitle("start date");
        startdiag.show();


}

    private void openEndDatePicker(int year1,int month1,int day1){
        Dialog diag = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setEndDate(year,month+1,dayOfMonth);
                if (counter3!=0) {

                    setDays(date, 0);
                }
                counter3++;
                if (counter3==2){
                    counter3=0;
                }
            }
        }, year1, month1, day1);
        diag.setTitle("end date");
        diag.show();
    }


    void setStartDate(int year, int month, int day){
        startyear = year;
        startmonth = month;
        startday = day;

    }
    void setEndDate(int year, int month, int day){
        endyear = year;
        endmonth = month;
        endday = day;

    }


}
