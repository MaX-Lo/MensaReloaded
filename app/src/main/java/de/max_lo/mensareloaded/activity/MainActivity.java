package de.max_lo.mensareloaded.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupMensaButtons();
    }

    private void setupMensaButtons() {
        ImageButton btnAlteMensa = findViewById(R.id.btnAlteMensa);
        btnAlteMensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMealActivity(Mensa.ALTE_MENSA);
            }
        });

        ImageButton btnZeltmensa = findViewById(R.id.btnZeltmensa);
        btnZeltmensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMealActivity(Mensa.ZELTMENSA);
            }
        });

        ImageButton btnSiedepunkt = findViewById(R.id.btnSiedepunkt);
        btnSiedepunkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMealActivity(Mensa.SIEDEPUNKT);
            }
        });

        ImageButton btnWuEins = findViewById(R.id.btnWuEins);
        btnWuEins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMealActivity(Mensa.WU_EINS);
            }
        });
    }

    private void startMealActivity(Mensa mensa) {
        Intent intent = new Intent(MainActivity.this, MealActivity.class);
        intent.putExtra("mensa", mensa);
        startActivity(intent);
    }


}
