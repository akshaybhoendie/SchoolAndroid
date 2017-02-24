package sr.unasat.financialapp.activities.main.fragments;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;
import sr.unasat.financialapp.dto.Transaction;

import static android.content.ContentValues.TAG;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.DATE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_AMOUNT;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_NAME;

public class AddTransactionDialog extends DialogFragment {

    Category category;Dao dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_transaction_dialog, container, false);
        dao = new Dao(getActivity());
        List<Category> categories = dao.getCategories();
        List<String>categorynames = new ArrayList<>();
        for (Category category:categories){

            categorynames.add(category.getName());

        }

        Spinner spinner = (Spinner)view.findViewById(R.id.trandialog_category_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,R.id.spinner_item, categorynames);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String name = String.valueOf(parent.getItemAtPosition(position));
                category = dao.getCategoryByName(name);

                Toast.makeText(getActivity(), parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                category = dao.getCategoryByName("no category");

            }

        });


        return view;
    }

    public void addTransaction(){

        View view = getView();
        if (view!= null) {
            EditText transactionNameView = (EditText) view.findViewById(R.id.transaction_name_input);
            EditText transactionAmountView = (EditText) view.findViewById(R.id.transaction_amount_input);

            String transactionName= String.valueOf(transactionNameView.getText());
            double transactionAmount = Double.valueOf(String.valueOf(transactionAmountView.getText()));


            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            String customDate=null;
            try {
                customDate = dateFormat.format(date);

            }catch (Exception e){
                Log.i(TAG, "addTransaction:"+e);
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(TRAN_NAME,transactionName);
            contentValues.put(TRAN_AMOUNT,transactionAmount);
            contentValues.put(DATE,customDate);
            contentValues.put(CAT_ID,category.getId());

            if (dao.insertTransaction(contentValues))
                Toast.makeText(getActivity(), "transaction inserted", Toast.LENGTH_SHORT).show();
            else{
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
            dao.close();


            Toast.makeText(getActivity(), transactionName+"\n"+transactionAmount, Toast.LENGTH_SHORT).show();
            getDialog().dismiss();

        }else{

            Log.i(TAG, "addTransaction: view NULL pointer exception");

        }
    }

}
