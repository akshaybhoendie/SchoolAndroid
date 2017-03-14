package sr.unasat.financialapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.dto.Transaction;

import static sr.unasat.financialapp.util.IconUtil.getImage;


public class TransactionRecycledAdapter extends RecyclerView.Adapter<TransactionRecycledAdapter.RecyclerViewHolder> {

    private List<Transaction> transactions;
    private Context context;

    public TransactionRecycledAdapter(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction,parent,false);
        return new RecyclerViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Transaction transaction = transactions.get(position);


        holder.transactionName.setText(transaction.getTran_name());
        holder.amount.setText("$ "+String.valueOf(transaction.getTran_amount()));
        holder.description.setText(transaction.getCategory().getName());
        holder.icon.setImageBitmap(getImage(transaction.getCategory().getIcon()));


    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView transactionName;
        TextView description;
        TextView amount;
        ImageView icon;

        public RecyclerViewHolder(View itemView,Context context) {
            super(itemView);
            transactionName=(TextView)itemView.findViewById(R.id.tran_name);
            description=(TextView)itemView.findViewById(R.id.transaction_descr);
            amount=(TextView)itemView.findViewById(R.id.transaction_value);
            icon=(ImageView)itemView.findViewById(R.id.category_icon_at_balance);

        }
    }
}
