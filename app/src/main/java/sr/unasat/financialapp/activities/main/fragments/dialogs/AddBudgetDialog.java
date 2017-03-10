package sr.unasat.financialapp.activities.main.fragments.dialogs;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;

import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.BUDGET;

/**
 */
public class AddBudgetDialog extends DialogFragment {


    Dao dao;Category category;public Integer budgetCategoryToEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.dialog_add_budget, container, false);

        dao = new Dao(getActivity());

        List<Category> categories = dao.getCategories();
        List<String>categorynames = new ArrayList<>();
        for (Category category:categories){

            if (category.getBudget()==0){
                categorynames.add(category.getName());
            }
        }
        categorynames.remove("no_category");
        categorynames.remove("income");
        Spinner spinner = (Spinner)view.findViewById(R.id.budget_cat_spinner);

        if (budgetCategoryToEdit !=null){
            category = dao.getCategoryById(budgetCategoryToEdit);
            EditText budget=(EditText)view.findViewById(R.id.budget_dialog_input);
            budget.setText(String.valueOf(category.getBudget()));

            categorynames.clear();
            categorynames.add(category.getName());
            spinner.setSelection(0);
            spinner.setEnabled(false);

        }


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



            }

        });



        return view;
    }

    public void addBudget(){


        View view=getView();
        EditText budgetV = (EditText)view.findViewById(R.id.budget_dialog_input);
        double budget= Double.valueOf(String.valueOf(budgetV.getText()));

        ContentValues contentValues=new ContentValues();
        contentValues.put(BUDGET,budget);

        dao = new Dao(getContext());
        if (dao.editCategory(contentValues,category.getId())){
            Toast.makeText(getContext(), "add budget complete", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "add budget failed", Toast.LENGTH_SHORT).show();
        }

        getDialog().dismiss();
    }
}
