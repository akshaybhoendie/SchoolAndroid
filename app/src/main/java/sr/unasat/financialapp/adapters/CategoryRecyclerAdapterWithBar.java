package sr.unasat.financialapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.BalanceFragment;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;

import static sr.unasat.financialapp.util.IconUtil.getImage;


public class CategoryRecyclerAdapterWithBar extends RecyclerView.Adapter<CategoryRecyclerAdapterWithBar.RecyclerViewHolder>  {
    private List<Category> categories;
    double totalValue;
    Context context;
    FragmentManager fragmentManager;
    String period;
    int year,month;

    public CategoryRecyclerAdapterWithBar(List<Category> categories, double totalValue,String period,int year,int month, Context context, FragmentManager fragmentManager) {
        this.categories = categories;
        this.totalValue = totalValue;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.period=period;
        this.year=year;
        this.month=month;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category_piechart,parent,false);
        return new RecyclerViewHolder(view,context,fragmentManager);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Dao dao=new Dao(context);
        Category category = categories.get(position);
        double value;
        switch (period){
            case "today":
                 value = dao.getCategoryValuesToDay(category);

                break;
            case "this month":
                value = dao.getAmountUsedByCategoryCurrentMonth(category);

                break;
            case "choose month":
                value = dao.getCategoryValuesByMonth(category,year,month);
                break;
            case "all past transactions":
                value = dao.getCategoryValues(category);
                break;
            default:value=0;
        }

        int percentage = (int)Math.round((value/totalValue)*100);

        holder.categorynameWithPercentage.setText(category.getName()+" ("+percentage+" % )");
        holder.categoryUsed.setText("$ "+String.valueOf(value));
        holder.categoryPercentageBar.setProgress(percentage);
        holder.category=category;
        holder.icon.setImageBitmap(getImage(categories.get(position).getIcon()));
        holder.icon.setTag(category.getId()+" icon ");
        holder.categoryPercentageBar.setTag(category.getId()+" bar ");
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView categorynameWithPercentage, categoryUsed;Category category;
        ProgressBar categoryPercentageBar;Context context1;FragmentManager fragmentManagerThis;
        ImageView icon;
        public RecyclerViewHolder(View itemView,Context context,FragmentManager fragmentManager) {
            super(itemView);
            this.context1=context;
            this.fragmentManagerThis=fragmentManager;
            icon = (ImageView)itemView.findViewById(R.id.category_icon_at_pie);
            categorynameWithPercentage=(TextView)itemView.findViewById(R.id.categoryname_with_percentage);
            categoryUsed=(TextView)itemView.findViewById(R.id.spend_category_value);
            categoryPercentageBar=(ProgressBar)itemView.findViewById(R.id.progressbar_category_percent);
            categoryPercentageBar.setMax(100);
            categoryPercentageBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BalanceFragment fragment=new BalanceFragment();
                    fragment.category=category;
                    fragmentManagerThis.beginTransaction().replace(R.id.main_container, fragment).addToBackStack("see trans by cat").commit();

                    Toast.makeText(context1, "see transactions by category", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onClick(View v) {


        }
    }
}
