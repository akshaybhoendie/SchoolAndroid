package sr.unasat.financialapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.BalanceFragment;
import sr.unasat.financialapp.activities.main.fragments.CategoriesFragment;
import sr.unasat.financialapp.activities.main.fragments.dialogs.AddCategoryDialog;
import sr.unasat.financialapp.activities.main.fragments.dialogs.ConfirmFragment;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;


import static sr.unasat.financialapp.activities.main.MainActivity.addCategoryDialog;
import static sr.unasat.financialapp.activities.main.MainActivity.confirmFragment;
import static sr.unasat.financialapp.activities.main.MainActivity.fragmentAction;
import static sr.unasat.financialapp.util.IconUtil.getImage;


public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.RecyclerViewHolder> {

    private List<String> category_names;
    Context context;
    FragmentManager fragmentManager;


    public CategoryRecyclerAdapter(List<String> category_names,Context context,FragmentManager fragmentManager){

        this.context=context;
        category_names.remove("no_category");
        category_names.remove("income");
        this.fragmentManager=fragmentManager;
        this . category_names=category_names;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category,parent,false);
        return new RecyclerViewHolder(view,context,fragmentManager);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        String name= category_names.get(position);
        Category category=new Dao(context).getCategoryByName(name);
        String id=String.valueOf(category.getId());
        holder.category.setText(name);
        holder.category_id.setText(id);
        if (category.getIcon()!=null) {
            holder.icon.setImageBitmap(getImage(category.getIcon()));
        }
    }

    @Override
    public int getItemCount() {
        return category_names.size();
    }


     public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener{

         TextView category;
         TextView category_id;
         Context context;
         FragmentManager fragmentManagerthis;
         ImageView icon;


        public RecyclerViewHolder(View itemView, Context context, FragmentManager fragmentManager) {
            super(itemView);
            this.fragmentManagerthis=fragmentManager;
            icon=(ImageView)itemView.findViewById(R.id.category_icon);
            this.context=context;
            itemView.setOnClickListener(this);
            category= (TextView)itemView.findViewById(R.id.budget_category_name_cat);

            category_id=(TextView)itemView.findViewById(R.id.category_id);

            ImageButton deleteButton=(ImageButton) itemView.findViewById(R.id.delete_category);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    addCategoryDialog.categoryToEditID = Integer.valueOf(String.valueOf(category_id.getText()));
                    confirmFragment = new ConfirmFragment();
                    confirmFragment.show(fragmentManagerthis,"confirm");
                    fragmentAction = "category";

                }
            });

            ImageButton editButton=(ImageButton) itemView.findViewById(R.id.edit_category);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCategoryDialog=new AddCategoryDialog();
                    addCategoryDialog.categoryToEditID = Integer.valueOf(String.valueOf(category_id.getText()));
                    addCategoryDialog.show(fragmentManagerthis,"add");




                }
            });

        }


         @Override
         public void onClick(View v) {

             Dao dao=new Dao(context);
             BalanceFragment fragment=new BalanceFragment();
             fragment.category=dao.getCategoryByName(String.valueOf(category.getText()));
             fragmentManagerthis.beginTransaction().replace(R.id.main_container, fragment).addToBackStack("see trans by cat").commit();

             Toast.makeText(context, "see transactions by category", Toast.LENGTH_SHORT).show();

         }
     }

}
