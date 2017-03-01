package sr.unasat.financialapp.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import sr.unasat.financialapp.R;
import sr.unasat.financialapp.activities.main.fragments.AddCategoryDialog;
import sr.unasat.financialapp.activities.main.fragments.ConfirmFragment;
import sr.unasat.financialapp.db.dao.Dao;
import sr.unasat.financialapp.dto.Category;


import static sr.unasat.financialapp.activities.main.MainActivity.addCategoryDialog;
import static sr.unasat.financialapp.activities.main.MainActivity.confirmFragment;
import static sr.unasat.financialapp.activities.main.MainActivity.fragmentAction;
import static sr.unasat.financialapp.activities.main.fragments.AddCategoryDialog.categoryToEditID;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.RecyclerViewHolder> {

    private List<String> category_names;
    Context context;
    FragmentManager fragmentManager;


    public CategoryRecyclerAdapter(List<String> category_names,Context context,FragmentManager fragmentManager){

        this.category_names=category_names;
        this.context=context;
        category_names.remove("no category");
        this.fragmentManager=fragmentManager;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category,parent,false);
        return new RecyclerViewHolder(view,fragmentManager);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        String name=category_names.get(position);
        Category category=new Dao(context).getCategoryByName(name);
        String id=String.valueOf(category.getId());
        holder.category.setText(name);
        holder.category_id.setText(id);
    }

    @Override
    public int getItemCount() {
        return category_names.size();
    }


     public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener{

        TextView category;
         TextView category_id;


        public RecyclerViewHolder(View itemView, final FragmentManager fragmentManager) {
            super(itemView);
            itemView.setOnClickListener(this);
            category= (TextView)itemView.findViewById(R.id.category_name_cat);

            category_id=(TextView)itemView.findViewById(R.id.category_id);

            ImageButton deleteButton=(ImageButton) itemView.findViewById(R.id.delete_category);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    categoryToEditID = Integer.valueOf(String.valueOf(category_id.getText()));
                    confirmFragment = new ConfirmFragment();
                    confirmFragment.show(fragmentManager,"confirm");
                    fragmentAction = "category";

                }
            });

            ImageButton editButton=(ImageButton) itemView.findViewById(R.id.edit_category);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryToEditID = Integer.valueOf(String.valueOf(category_id.getText()));
                    addCategoryDialog = new AddCategoryDialog();
                    addCategoryDialog.show(fragmentManager,"add");



                }
            });

        }


         @Override
         public void onClick(View v) {



         }
     }

}