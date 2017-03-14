package sr.unasat.financialapp.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.dialogs.ConfirmFragment;
import sr.unasat.financialapp.activities.main.fragments.dialogs.EditOrDeleteFragment;

import static sr.unasat.financialapp.activities.main.MainActivity.confirmFragment;
import static sr.unasat.financialapp.activities.main.MainActivity.editOrDeleteFragment;
import static sr.unasat.financialapp.activities.main.MainActivity.fragmentAction;


public class SettingsRecyclerAdapter  extends RecyclerView.Adapter<SettingsRecyclerAdapter.RecyclerViewHolder>  {

    private List<String> options;
    private Context context;
    FragmentManager fragmentManager;

    public SettingsRecyclerAdapter(List<String> options, Context context,FragmentManager fragmentManager) {
        this.options = options;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_report,parent,false);
        return new RecyclerViewHolder(view,fragmentManager);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        if (options.get(position).equals("edit currency")){
            holder.icon.setImageBitmap(((BitmapDrawable)(ContextCompat.getDrawable(context,R.drawable.currency))).getBitmap());
        }else if(options.get(position).equals("reset account")){
            holder.icon.setImageBitmap(((BitmapDrawable)(ContextCompat.getDrawable(context,R.drawable.reset))).getBitmap());
        }
        holder.option.setText(options.get(position));


    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView option;
        ImageView icon;
        FragmentManager fragmentManager;
        public RecyclerViewHolder(View itemView, final FragmentManager fragmentManager) {
            super(itemView);
            option=(TextView)itemView.findViewById(R.id.report_text);
            icon=(ImageView)itemView.findViewById(R.id.report_icon);
            this.fragmentManager=fragmentManager;
            option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmFragment = new ConfirmFragment();
                    fragmentAction="reset";
                    confirmFragment.show(fragmentManager,"edit/delete frag");

                }
            });
        }



    }

}
