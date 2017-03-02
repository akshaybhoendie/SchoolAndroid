package sr.unasat.financialapp.activities.main;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.dialogs.AddBudgetDialog;
import sr.unasat.financialapp.activities.main.fragments.dialogs.AddCategoryDialog;
import sr.unasat.financialapp.activities.main.fragments.dialogs.AddTransactionDialog;
import sr.unasat.financialapp.activities.main.fragments.BalanceFragment;
import sr.unasat.financialapp.activities.main.fragments.BudgetsFragment;
import sr.unasat.financialapp.activities.main.fragments.CategoriesFragment;
import sr.unasat.financialapp.activities.main.fragments.dialogs.ConfirmFragment;
import sr.unasat.financialapp.activities.main.fragments.dialogs.EditOrDeleteFragment;
import sr.unasat.financialapp.activities.main.fragments.OverviewFragment;
import sr.unasat.financialapp.activities.main.fragments.report.ReportsFragment;
import sr.unasat.financialapp.activities.main.fragments.SettingsFragment;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;

import static sr.unasat.financialapp.db.schema.Schema.SchemaCategory.BUDGET;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle actionBarDrawerToggle;//hamburger icon
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    static public AddTransactionDialog addTransactionDialog;
    static public EditOrDeleteFragment editOrDeleteFragment;
    static public ConfirmFragment confirmFragment;
    static public AddCategoryDialog addCategoryDialog;
    static public AddBudgetDialog addBudgetDialog;
    public static String fragmentAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //new Dao(this).getReadableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,new OverviewFragment()).commit();
        getSupportActionBar().setTitle(R.string.overview);

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment newfragment = null;
        String title = null;
        switch (item.getItemId()) {
            case R.id.overview_item:
                newfragment = new OverviewFragment();
                title = getResources().getString(R.string.overview);
                break;

            case R.id.balance_item:
                newfragment = new BalanceFragment();
                title = getResources().getString(R.string.balance);
                break;
            case R.id.budgets_item:
                newfragment = new BudgetsFragment();
                title = getResources().getString(R.string.budgets);
                break;
            case R.id.reports_item:
                newfragment = new ReportsFragment();
                title = getResources().getString(R.string.reports);
                break;
            case R.id.categories:
                newfragment = new CategoriesFragment();
                title = getResources().getString(R.string.categories);
                break;

            case R.id.settings_item:
                newfragment = new SettingsFragment();
                title = getResources().getString(R.string.settings);
                break;
            default:
                newfragment = new OverviewFragment();
                title = getResources().getString(R.string.overview);
                break;

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, newfragment).commit();

        getSupportActionBar().setTitle(title);
        drawerLayout.closeDrawers();

        return true;
    }


    public void balance_floatingButtonEvent(View view){
        addTransactionDialog = new AddTransactionDialog();
        addTransactionDialog.show(getSupportFragmentManager(),"add_tran_diag");
    }
    public void category_floatingButtonEvent(View view){
        addCategoryDialog.categoryToEditID=null;
        addCategoryDialog=new AddCategoryDialog();
        addCategoryDialog.show(getSupportFragmentManager(),"add category dialog");
    }
    public void budget_floatingButtonEvent(View view){

        Dao dao = new Dao(this);

        List<Category> categories = dao.getCategories();
        List<String>categorynames = new ArrayList<>();
        for (Category category:categories){

            if (category.getBudget()==0){
                categorynames.add(category.getName());
            }
        }
        categorynames.remove("no category");
        categorynames.remove("income");

        if (categorynames.isEmpty()){
            Toast.makeText(this, "all categories have budgets", Toast.LENGTH_SHORT).show();


        }else {
            addBudgetDialog = new AddBudgetDialog();
            addBudgetDialog.show(getSupportFragmentManager(), "add budget dialog");
        }
    }


    public void okTransactionDialogEvent(View view) {

        addTransactionDialog.addTransaction();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new BalanceFragment()).commit();

        getSupportActionBar().setTitle(getResources().getString(R.string.balance));

    }
    public void okCategoryDialogEvent(View view){

        addCategoryDialog.addCategory();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new CategoriesFragment()).commit();

        getSupportActionBar().setTitle(getResources().getString(R.string.categories));

    }
    public void okBudgetDialogEvent(View view){


        addBudgetDialog.addBudget();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new BudgetsFragment()).commit();

        getSupportActionBar().setTitle(getResources().getString(R.string.budgets));
    }


    public void cancelTransactionDialogEvent(View view){
        addTransactionDialog.getDialog().dismiss();
        addTransactionDialog.transactionToEditID=null;

    }
    public void cancelCategoryDialogEvent(View view){
        addCategoryDialog.dismiss();Toast.makeText(this, "no category added", Toast.LENGTH_SHORT).show();
    }
    public void cancelBudgetDialogEvent(View view){

        addBudgetDialog.dismiss();Toast.makeText(this, "no budget added", Toast.LENGTH_SHORT).show();

    }

    public void confirmDelete(View view){


        Dao dao=new Dao(this);
        switch(fragmentAction) {

            case "transaction":
                dao.deleteTransaction(addTransactionDialog.transactionToEditID);
                addTransactionDialog.transactionToEditID = null;
                confirmFragment.dismiss();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new BalanceFragment()).commit();

                getSupportActionBar().setTitle(getResources().getString(R.string.balance));
                fragmentAction=null;
                break;

            case "category":

                if (dao.deleteCategory(addCategoryDialog.categoryToEditID)){
                    Toast.makeText(this, "delete succesful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "delete unsuccesful", Toast.LENGTH_SHORT).show();
                }

                confirmFragment.dismiss();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new CategoriesFragment()).commit();

                getSupportActionBar().setTitle(getResources().getString(R.string.categories));
                fragmentAction=null;
                addCategoryDialog.categoryToEditID=null;
                break;

            case "budget":
                ContentValues contentValues=new ContentValues();
                contentValues.put(BUDGET,0);
                if (dao.editCategory(contentValues,addBudgetDialog.budgetCategoryToEdit)){
                    Toast.makeText(this, "budget removed", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "budget not removed", Toast.LENGTH_SHORT).show();
                }
                confirmFragment.dismiss();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new BudgetsFragment()).commit();

                getSupportActionBar().setTitle(getResources().getString(R.string.budgets));
                fragmentAction=null;
                addBudgetDialog.budgetCategoryToEdit=null;
                break;


        }

    }
    public void cancelDelete(View view){
        confirmFragment.dismiss();
        addCategoryDialog.categoryToEditID=null;
        addBudgetDialog.budgetCategoryToEdit=null;
        addTransactionDialog.transactionToEditID=null;
        fragmentAction=null;
    }

    public void editTransaction(View view){

        editOrDeleteFragment.dismiss();
        addTransactionDialog.show(getSupportFragmentManager(),"edit");


    }
    public void deleteTransaction(View view){
        confirmFragment=new ConfirmFragment();
        confirmFragment.show(getSupportFragmentManager(),"deleteTransaction");
        editOrDeleteFragment.dismiss();
        fragmentAction="transaction";


    }









}
