package sr.unasat.financialapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Transaction;
import sr.unasat.financialapp.dto.User;

/**
 * Created by Jair on 3/11/2017.
 */

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.RecyclerViewHolder>  {

    Context context;

    public BalanceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_balance,parent,false);
        return new RecyclerViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Dao dao=new Dao(context);
        User user  = dao.getUserById(1);
        double value;
        dao.getIncome();
        switch (position){
            case 0:
                Bitmap icon = ((BitmapDrawable)(ContextCompat.getDrawable(context,R.drawable.opening))).getBitmap();

                holder.icon.setImageBitmap(icon);
                holder.balanceType.setText("opening");
                holder.balanceAmount.setText((String.valueOf(user.getOpening())));
                break;
            case 1:
                value=0;
                Bitmap icon1 = ((BitmapDrawable)(ContextCompat.getDrawable(context,R.drawable.income_balance))).getBitmap();
                holder.icon.setImageBitmap(icon1);
                holder.balanceType.setText("income");
                for (Transaction transaction:dao.getIncome()){
                    value = value+transaction.getTran_amount();
                }
                holder.balanceAmount.setText((String.valueOf(value)));
                break;
            case 2:
                value=0;
                Bitmap icon2 = ((BitmapDrawable)(ContextCompat.getDrawable(context,R.drawable.expense))).getBitmap();
                holder.icon.setImageBitmap(icon2);
                for(Transaction transaction:dao.getTransactions()){
                    if (!dao.getIncome().contains(transaction)){
                        value=value+transaction.getTran_amount();
                    }
                }
                holder.balanceType.setText("expense");
                holder.balanceAmount.setText(String.valueOf(value));
                break;
            case 3:
                Bitmap icon3 = ((BitmapDrawable)(ContextCompat.getDrawable(context,R.drawable.closing))).getBitmap();
                holder.icon.setImageBitmap(icon3);
                holder.balanceType.setText("closing");
                holder.balanceAmount.setText(String.valueOf(user.getClosing()));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView balanceType, balanceAmount;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            icon= (ImageView) itemView.findViewById(R.id.balance_icon);
            balanceType=(TextView)itemView.findViewById(R.id.balance_type);
            balanceAmount=(TextView)itemView.findViewById(R.id.balance_type_amount);
        }
    }

}
