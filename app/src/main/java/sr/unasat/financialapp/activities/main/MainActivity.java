package sr.unasat.financialapp.activities.main;

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

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.AddTransactionDialog;
import sr.unasat.financialapp.activities.main.fragments.BalanceFragment;
import sr.unasat.financialapp.activities.main.fragments.BudgetsFragment;
import sr.unasat.financialapp.activities.main.fragments.CategoriesFragment;
import sr.unasat.financialapp.activities.main.fragments.OverviewFragment;
import sr.unasat.financialapp.activities.main.fragments.ReportsFragment;
import sr.unasat.financialapp.activities.main.fragments.SettingsFragment;
import sr.unasat.financialapp.db.dao.Dao;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle actionBarDrawerToggle;//hamburger icon
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    AddTransactionDialog addTransactionDialog;

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

    public void cancelTransactionEvent(View view){
        addTransactionDialog.getDialog().dismiss();
        Toast.makeText(this, "no transaction added", Toast.LENGTH_SHORT).show();
    }

    public void okTransactionEvent(View view){

        addTransactionDialog.addTransaction();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new BalanceFragment()).commit();

        getSupportActionBar().setTitle(getResources().getString(R.string.balance));

    }
}
