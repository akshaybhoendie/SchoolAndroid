package sr.unasat.financialapp.activities.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.dialogs.AddTransactionDialog;
import sr.unasat.financialapp.activities.main.fragments.dialogs.EditOrDeleteFragment;
import sr.unasat.financialapp.activities.main.fragments.dialogs.MonthPickerDialog;
import sr.unasat.financialapp.adapters.TransactionExpendableAdapter;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;
import sr.unasat.financialapp.dto.Transaction;
import sr.unasat.financialapp.dto.User;

import static sr.unasat.financialapp.activities.main.MainActivity.addTransactionDialog;
import static sr.unasat.financialapp.activities.main.MainActivity.editOrDeleteFragment;
import static sr.unasat.financialapp.activities.main.MainActivity.monthPickerDialog;
import static sr.unasat.financialapp.util.DateUtil.convertDate;

public class BalanceFragment extends Fragment {

    Date date ;
    public Category category;
    Spinner spinner;
    View view;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_fragment_balance, container, false);

        Dao dao = new Dao(getActivity());
        final String spinnerItem1 ="this month";
        final String spinnerItem2 ="last month";
        final String spinnerItem3 ="choose month";

        spinner = (Spinner)view.findViewById(R.id.balance_month_spinner);
        final String[] items = {spinnerItem1,spinnerItem2,spinnerItem3};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.spinner_item, items);

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               date=Calendar.getInstance().getTime();


                switch (String.valueOf(parent.getItemAtPosition(position))){

                    case spinnerItem1:

                        if (bundle!=null){
                            int month = (int) bundle.get("month");
                            int year = (int) bundle.get("year");
                            setDays(date,month,year);
                        }else{
                            setDays(date,0,0);
                        }
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
                        monthPickerDialog=new MonthPickerDialog();
                        monthPickerDialog.pickMonthFor="balance";
                        monthPickerDialog.show(getActivity().getFragmentManager(),"monthpicker");


                        break;



                }

                Toast.makeText(getActivity(), String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextView closingView = (TextView)view.findViewById(R.id.balance_closing_value);

        TextView transactionView = (TextView)view.findViewById(R.id.transactions_value);

        TextView openingView = (TextView)view.findViewById(R.id.starting_value);

        User user=dao.getUserById(1);

        closingView.setText(String.valueOf(user.getClosing()));
        transactionView.setText(String.valueOf(user.getTransactions()));
        openingView.setText(String.valueOf(user.getOpening()));



        return view;
    }

    public void setDays(Date date,int month,int year){
        view.findViewById(R.id.group_listViewMain).refreshDrawableState();
        Dao dao=new Dao(getContext());

                int[] dateArr = convertDate(date);

            if (month == 0&&year==0) {
                month = dateArr[1];

            }

        if (year==0){
            year = dateArr[0];
        }



        List<String> days;
        if (category!=null){
            days = dao.getDaysByCategory(month, year,category);
            spinner.setVisibility(View.GONE);

        }else{
            days = dao.getDays(month, year);
        }


            //day+" "+int_to_month(month)+" "+year;


            HashMap<String, List<String>> transactions = new HashMap<>();

            List<String> tranNames;


            for (String theDay : days) {

                String[] array = theDay.split("\\s");
                int day = Integer.valueOf(array[0]);
                tranNames = new ArrayList<>();
                List<Transaction>list;
                if (category!=null){
                    list=dao.getTransactionsByDayAndCategory(day, month, year,category);
                }else {
                    list = dao.getTransactionsByDay(day, month, year);
                }
                for (Transaction transaction : list) {

                        tranNames.add(String.valueOf(transaction.getTran_id()));
                        transactions.put(theDay, tranNames);



                }


                Collections.reverse(tranNames);

            }
        final List<String>thedays=days;
        final HashMap<String, List<String>>tran=transactions;


        Collections.reverse(days);
        ExpandableListView groupListView = (ExpandableListView) view.findViewById(R.id.group_listViewMain);
        TransactionExpendableAdapter adapter = new TransactionExpendableAdapter(days, transactions, getContext());

        groupListView.setAdapter(adapter);


        groupListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                editOrDeleteFragment = new EditOrDeleteFragment();
                editOrDeleteFragment.show(getFragmentManager(),"edit/delete frag");


                Toast.makeText(getActivity(), String.valueOf(childPosition) + " " + String.valueOf(groupPosition), Toast.LENGTH_SHORT).show();


                //addTransactionDialog = new AddTransactionDialog();

                String day = thedays.get(groupPosition);
                List<String> testList =tran.get(day);
                String tranId = testList.get(childPosition);

                addTransactionDialog = new AddTransactionDialog();
                addTransactionDialog.transactionToEditID = Integer.valueOf(tranId);


                //addTransactionDialog.show(getFragmentManager(), "editTransaction");


                return false;
            }
        });

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