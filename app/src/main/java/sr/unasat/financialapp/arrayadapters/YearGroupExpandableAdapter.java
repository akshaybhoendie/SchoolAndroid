package sr.unasat.financialapp.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.BalanceFragment;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Transaction;

import static sr.unasat.financialapp.util.DateUtil.convertDate;
import static sr.unasat.financialapp.util.DateUtil.int_to_month;


public class YearGroupExpandableAdapter extends BaseExpandableListAdapter {

    private List<String> year;
    private HashMap<String,List<String>> months;
    private Context context;

    public YearGroupExpandableAdapter(List<String> year, HashMap<String, List<String>> months, Context context) {
        this.year = year;
        this.months = months;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return year.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return months.get(year.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return year.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return months.get(year.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String year = (String)getGroup(groupPosition);
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =   inflater.inflate(R.layout.group_bydate_card,null);
        }

        TextView dateView= (TextView)convertView.findViewById(R.id.tran_date_onCard);
        dateView.setText(year);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =   inflater.inflate(R.layout.group_bydate_card2,null);
        }

        String month = (String) getChild(groupPosition,childPosition);
        String year = (String) getGroup(groupPosition);
        String monthStr = int_to_month(Integer.valueOf(month));
        TextView dateView= (TextView)convertView.findViewById(R.id.tran_date_onCard2);
        dateView.setText(monthStr);

        Dao dao=new Dao(context);

        List<String> days = dao.getDays(Integer.valueOf(month), Integer.valueOf(year));

        //day+" "+int_to_month(month)+" "+year;

        HashMap<String, List<String>> transactions = new HashMap<>();

        List<String> tranNames;


        for (String theDay : days) {

            String[] array = theDay.split("\\s");
            int day = Integer.valueOf(array[0]);
            tranNames = new ArrayList<>();
            List<Transaction> list = dao.getTransactionsByDay(day, Integer.valueOf(month),  Integer.valueOf(year));
            for (Transaction transaction : list) {

                tranNames.add(transaction.getTran_name());
                transactions.put(theDay, tranNames);

            }


        }


        ExpandableListView groupListView = (ExpandableListView) convertView.findViewById(R.id.group_listView);

        DateGroupExpendableAdapter adapter = new DateGroupExpendableAdapter(days, transactions, context);

        groupListView.setAdapter(adapter);



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}