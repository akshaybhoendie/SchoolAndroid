package sr.unasat.financialapp.activities.main.fragments.dialogs;


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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.MainActivity;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;

import static android.content.ContentValues.TAG;

import static sr.unasat.financialapp.activities.main.MainActivity.confirmFragment;
import static sr.unasat.financialapp.activities.main.MainActivity.fragmentAction;
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


    static public Integer categoryToEditID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.add_or_edit_category, container, false);

        TextView catname=(TextView)view.findViewById(R.id.category_name_input);
        TextView budget=(TextView)view.findViewById(R.id.category_budget_input);
        if (categoryToEditID!=null){
            Category category=new Dao(getContext()).getCategoryById(categoryToEditID);
            catname.setText(category.getName());
            budget.setText(String.valueOf(category.getBudget()));
        }

        return view;

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

            if (categoryToEditID!=null){

                if(dao.editCategory(categorynName,null,budget, categoryToEditID)){
                    Toast.makeText(getActivity(), "category updated", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), "category not updated", Toast.LENGTH_SHORT).show();
                }

            }else{

                if(dao.insertCategory(categorynName,null,budget)){
                    Toast.makeText(getActivity(), "category added", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), "category not added", Toast.LENGTH_SHORT).show();
                }

            }
        } else{

            Log.i(TAG, "addTransaction: view NULL pointer exception");

        }

        categoryToEditID=null;
        getDialog().dismiss();
    }


}
