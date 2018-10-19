package de.max_lo.mensareloaded;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.max_lo.mensareloaded.database.entity.Meal;

public class MealViewAdapter extends RecyclerView.Adapter<MealViewAdapter.MealViewHolder> {

    private Context context;
    private List<Meal> mealsData;

    public static class MealViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDescription;
        public TextView tvPrice;
        public TextView tvCategory;
        public LinearLayout llCard;

        public MealViewHolder(View v) {
            super(v);
            tvDescription = v.findViewById(R.id.tvDescription);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvCategory = v.findViewById(R.id.tvCategory);
            llCard = v.findViewById(R.id.ll_card);
        }
    }

    public MealViewAdapter(Context context, List<Meal> meals) {
        this.context = context;
        mealsData = meals;
    }

    @NonNull
    @Override
    public MealViewAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_view_item, parent, false);

        return new MealViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        if (mealsData != null) {
            Meal mealData = mealsData.get(position);
            holder.tvDescription.setText(mealData.getDescription());
            holder.tvPrice.setText(mealData.getStudentPrice());
            holder.tvCategory.setText(mealData.getCategory());

            int bgColor = getBackgroundColor(getKeyword(mealData.getDescription() + mealData.getCategory()));
            holder.llCard.setBackgroundColor(context.getColor(bgColor));
        } else {
            holder.tvDescription.setText("no meals data available");
        }
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

    private String getKeyword(String description) {
        List<String> keywords = Arrays.asList("Pasta", "Pizza", "Terrine", "Auflauf");
        for (String keyword : keywords) {
            if (description.contains(keyword)) {
                return keyword;
            }
        }
        return "";
    }

    private int getBackgroundColor(String keyword) {
        switch(keyword) {
            case "Pasta":
                return R.color.color_light_green;
            case "Pizza":
                return R.color.color_light_brown;
            case "Terrine":
                return R.color.color_light_blue;
            default:
                return R.color.colorWhite;
        }
    }

}
