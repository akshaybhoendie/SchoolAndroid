package sr.unasat.financialapp.arrayadapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Transaction;

public class TransactionArrayAdapter extends ArrayAdapter<String> {

    public TransactionArrayAdapter(Context context, List<String> transactions) {

        super(context, R.layout.transaction_card, transactions);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Dao dao = new Dao(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.transaction_card,parent,false);

        TextView tranName = (TextView)customView.findViewById(R.id.transaction_name);
        TextView tranDescr = (TextView) customView.findViewById(R.id.transaction_descr);
        TextView tranVal = (TextView) customView.findViewById(R.id.transaction_value);


        String cat_name=getItem(position);
        Transaction transaction = dao.getTransactionByName(cat_name);
        tranName.setText(transaction.getTran_name());
        tranDescr.setText(transaction.getCategory().getName());
        tranVal.setText(String.valueOf(transaction.getTran_amount()));




        return customView;
    }
}
