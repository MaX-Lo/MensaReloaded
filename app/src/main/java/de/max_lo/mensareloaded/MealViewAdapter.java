package de.max_lo.mensareloaded;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.max_lo.mensareloaded.database.entity.Meal;

public class MealViewAdapter extends RecyclerView.Adapter<MealViewAdapter.MealViewHolder> {
    private List<Meal> mealsData;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MealViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDescription;
        public TextView tvPrice;
        public TextView tvCategory;

        public MealViewHolder(View v) {
            super(v);
            tvDescription = v.findViewById(R.id.tvDescription);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvCategory = v.findViewById(R.id.tvCategory);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MealViewAdapter(List<Meal> meals) {
        mealsData = meals;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MealViewAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_view_item, parent, false);

        return new MealViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        if (mealsData != null) {
            holder.tvDescription.setText(mealsData.get(position).getDescription());
            holder.tvPrice.setText(mealsData.get(position).getStudentPrice());
            holder.tvCategory.setText(mealsData.get(position).getCategory());
        } else {
            holder.tvDescription.setText("no meals data available");
        }

        Log.d("MealAdapter","bound view text is:" + holder.tvDescription.getText().toString());
    }

    public void setMeals(List<Meal> meals) {
        mealsData = meals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mealsData == null) {
            return 0;
        }
        return mealsData.size();
    }

}
