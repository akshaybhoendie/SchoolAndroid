package sr.unasat.financialapp.activities.main.fragments;


import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;

import static android.content.ContentValues.TAG;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.BUDGET;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_ID;
import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.CAT_TABLE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.DATE;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_AMOUNT;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_DESCR;
import static sr.unasat.financialapp.db.schema.Schema.SchemaTransaction.TRAN_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryDialog extends DialogFragment {


    public AddCategoryDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_or_edit_category, container, false);
    }

    public void addCategory() {

        View view = getView();
        if (view != null) {
            EditText categoryNameView = (EditText) view.findViewById(R.id.category_name_input);
            EditText budgetView = (EditText) view.findViewById(R.id.category_budget_input);

            String categorynName = String.valueOf(categoryNameView.getText());
            double budget = Double.valueOf(String.valueOf(budgetView.getText()));

            ContentValues contentValues = new ContentValues();
            contentValues.put(CAT_TABLE, categorynName);
            contentValues.put(BUDGET, budget);
            Dao dao=new Dao(getActivity());
            if(dao.insertCategory(categorynName,null,budget)){
                Toast.makeText(getActivity(), "category added", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "category not added", Toast.LENGTH_SHORT).show();
            }
        }else{

            Log.i(TAG, "addTransaction: view NULL pointer exception");

        }
        getDialog().dismiss();
    }
}
