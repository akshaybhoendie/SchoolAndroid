package sr.unasat.financialapp.arrayadapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import sr.unasat.financialapp.R;

public class ReportRecyclerAdapter extends RecyclerView.Adapter<ReportRecyclerAdapter.RecyclerViewHolder> {


    private List<String> reports;
    public ReportRecyclerAdapter(List<String> reports){

        this.reports = reports;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_report,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportRecyclerAdapter.RecyclerViewHolder holder, int position) {

        holder.report.setText(reports.get(position));

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView report;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            report = (TextView)itemView.findViewById(R.id.report_text);

        }
    }

}
