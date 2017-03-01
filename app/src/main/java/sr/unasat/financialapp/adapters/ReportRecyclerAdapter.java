package sr.unasat.financialapp.adapters;



import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.MainActivity;
import sr.unasat.financialapp.activities.main.fragments.report.BarChartFragment;
import sr.unasat.financialapp.dto.Transaction;




public class ReportRecyclerAdapter extends RecyclerView.Adapter<ReportRecyclerAdapter.RecyclerViewHolder> {


    private List<String> reports;
    private Context context;
    public ReportRecyclerAdapter(List<String> reports, Context context){

        this.reports = reports;
        this.context = context;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_report,parent,false);
        return new RecyclerViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(ReportRecyclerAdapter.RecyclerViewHolder holder, int position) {

        holder.report.setText(reports.get(position));

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener{

        TextView report;FragmentManager fragmentManager;

        public RecyclerViewHolder(View itemView, final Context context) {
            super(itemView);
            report = (TextView)itemView.findViewById(R.id.report_text);

            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String reportType=String.valueOf(report.getText());
                    BarChartFragment barChartFragment=new BarChartFragment();
                    switch (reportType){
                        case "daily expense":
                            barChartFragment.bartype="daily expense";
                            ((MainActivity)context).getSupportFragmentManager().beginTransaction().
                                    replace(R.id.main_container,barChartFragment ).addToBackStack("reportfrag").commit();
                            break;
                        default:
                            break;
                    }


                }
            });

        }

        @Override
        public void onClick(View v) {
            //typeReport= String.valueOf(report.getText());



        }
    }

}
