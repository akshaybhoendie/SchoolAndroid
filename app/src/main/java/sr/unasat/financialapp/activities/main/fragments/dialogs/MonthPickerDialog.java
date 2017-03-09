package sr.unasat.financialapp.activities.main.fragments.dialogs;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import java.util.Calendar;
import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.BalanceFragment;
import sr.unasat.financialapp.activities.main.fragments.report.PieChartFragment;

import static sr.unasat.financialapp.util.DateUtil.convertDate;


public class MonthPickerDialog extends DialogFragment {

    public String pickMonthFor;
    public static String pieGraphType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_month_picker, container, false);

        NumberPicker yearPicker= (NumberPicker)view.findViewById(R.id.year_picker);
        NumberPicker monthPicker=(NumberPicker)view.findViewById(R.id.month_picker);

        yearPicker.setMinValue(2010);
        yearPicker.setMaxValue(convertDate(Calendar.getInstance().getTime())[0]);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        yearPicker.setWrapSelectorWheel(true);


        return view;
    }

    public void showData(FragmentManager manager) {
        View view= getView();
        NumberPicker monthPicker = (NumberPicker)view.findViewById(R.id.month_picker);
        NumberPicker yearPicker = (NumberPicker)view.findViewById(R.id.year_picker);

        int month = monthPicker.getValue();
        int year = yearPicker.getValue();

        Fragment fragment;
        if (pickMonthFor.equals("balance")){
             fragment=new BalanceFragment();


        }else{

            PieChartFragment pieChartFragment=new PieChartFragment();
            pieChartFragment.bartype= pieGraphType;
            fragment = pieChartFragment;

        }
        Bundle args = new Bundle();
        args.putInt("month",month);
        args.putInt("year",year);
        fragment.setArguments(args);
        manager.beginTransaction().replace(R.id.main_container,fragment).commit();
    }
}
