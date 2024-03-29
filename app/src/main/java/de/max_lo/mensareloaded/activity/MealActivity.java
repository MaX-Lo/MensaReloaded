package de.max_lo.mensareloaded.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import de.max_lo.mensareloaded.MealViewAdapter;
import de.max_lo.mensareloaded.view_model.MealsViewModel;
import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.R;
import de.max_lo.mensareloaded.database.entity.Meal;

public class MealActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mealsRecyclerView;
    private MealViewAdapter mealsViewAdapter;

    private MealsViewModel mealsViewModel;

    private ImageView ivNoMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mealsRecyclerView = findViewById(R.id.meals_recycler_view);
        ivNoMeals = findViewById(R.id.iv_no_meals);

        Mensa mensa = (Mensa) getIntent().getSerializableExtra("mensa");

        getSupportActionBar().setTitle(getMensaString(mensa));

        mealsViewModel = ViewModelProviders.of(this).get(MealsViewModel.class);
        mealsViewModel.setMensa(mensa);
        mealsViewModel.getMeals().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(@Nullable List<Meal> meals) {
                if (meals == null || meals.size() == 0) {
                    setNoMealsHintVisible();
                } else {
                    mealsViewAdapter.setMeals(meals);
                    setNoMealsHintInvisible();
                }
            }
        });

        initMealsView();
    }

    private void setNoMealsHintVisible() {
        ivNoMeals.setVisibility(View.VISIBLE);
    }

    private void setNoMealsHintInvisible() {
        ivNoMeals.setVisibility(View.INVISIBLE);
    }

    private String getMensaString(Mensa mensa) {
        return getString(getResources().getIdentifier(mensa.name().toLowerCase(), "string", getPackageName()));
    }

    private void initMealsView() {
        mLayoutManager = new LinearLayoutManager(this);
        mealsRecyclerView.setLayoutManager(mLayoutManager);

        mealsViewAdapter = new MealViewAdapter(this, mealsViewModel.getMeals().getValue());
        mealsRecyclerView.setAdapter(mealsViewAdapter);
    }
}
