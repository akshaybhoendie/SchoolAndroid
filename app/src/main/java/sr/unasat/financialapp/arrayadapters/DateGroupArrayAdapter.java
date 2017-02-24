package sr.unasat.financialapp.arrayadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Transaction;

public class DateGroupArrayAdapter extends ArrayAdapter <String>{
    public DateGroupArrayAdapter(Context context, List<String> date) {
        super(context, R.layout.group_bydate_card, date);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.group_bydate_card,parent,false);
        Dao dao=new Dao(getContext());

        TextView tranDate = (TextView )customView.findViewById(R.id.tran_date_onCard);

        tranDate.setText(getItem(position));

        ListView listView = (ListView)customView.findViewById(R.id.transaction_listView);

        List<Transaction> transactionList = dao.getTransactions();
        List<String> transactionNames=new ArrayList<>();

        for (Transaction transaction: transactionList){
            transactionNames.add(transaction.getTran_name());
        }

        TransactionArrayAdapter adapter = new TransactionArrayAdapter(getContext(),transactionNames);
        listView.setAdapter(adapter);


        return customView;
    }
}
