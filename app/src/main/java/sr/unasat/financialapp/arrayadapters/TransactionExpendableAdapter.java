package sr.unasat.financialapp.arrayadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Transaction;

public class TransactionExpendableAdapter extends BaseExpandableListAdapter {

    private List<String> date;
    private HashMap<String,List<String>> transactions;
    private Context context;

    public TransactionExpendableAdapter(List<String> date, HashMap<String, List<String>> transactions, Context context) {

        this.date = date;
        this.transactions = transactions;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return date.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        try {
            return transactions.get(date.get(groupPosition)).size();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return date.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return transactions.get(date.get(groupPosition)).get(childPosition);
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

        String date = (String)getGroup(groupPosition);
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =   inflater.inflate(R.layout.card_date,null);
        }

        TextView dateView= (TextView)convertView.findViewById(R.id.tran_date_onCard3);
        dateView.setText(date);

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition,true);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Dao dao = new Dao(context);
        int transactionID = Integer.valueOf((String)getChild(groupPosition,childPosition));

        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =   inflater.inflate(R.layout.card_transaction,null);
        }

        TextView tranName = (TextView)convertView.findViewById(R.id.category_name);
        TextView tranDescr = (TextView) convertView.findViewById(R.id.transaction_descr);
        TextView tranVal = (TextView) convertView.findViewById(R.id.transaction_value);
        TextView tranID = (TextView) convertView.findViewById(R.id.transaction_id_onCard);

        Transaction transaction = dao.getTransactionByID(transactionID);
        tranName.setText(transaction.getTran_name());
        tranDescr.setText(transaction.getCategory().getName());
        tranVal.setText(String.valueOf(transaction.getTran_amount()));
        tranID.setText(String.valueOf(transactionID));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
