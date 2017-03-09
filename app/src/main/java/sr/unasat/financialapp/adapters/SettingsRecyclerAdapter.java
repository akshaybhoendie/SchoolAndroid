package sr.unasat.financialapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sr.unasat.financialapp.R;


public class SettingsRecyclerAdapter  extends RecyclerView.Adapter<SettingsRecyclerAdapter.RecyclerViewHolder>  {

    private List<String> options;
    private Context context;

    public SettingsRecyclerAdapter(List<String> options, Context context) {
        this.options = options;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_report,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.option.setText(options.get(position));
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView option;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            option=(TextView)itemView.findViewById(R.id.report_text);
        }
    }

}
