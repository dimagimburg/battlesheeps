package com.example.dima.battlesheeps.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.example.dima.battlesheeps.R;

public class HighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Button tableBtn = (Button) findViewById(R.id.tableBtn);
        Button mapBtn = (Button) findViewById(R.id.mapBtn);

        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout mapContainer = (RelativeLayout) findViewById(R.id.mapContainer);
                mapContainer.setVisibility(View.INVISIBLE);
                TableLayout tableContainer = (TableLayout) findViewById(R.id.tableContainer);
                tableContainer.setVisibility(View.VISIBLE);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout mapContainer = (RelativeLayout) findViewById(R.id.mapContainer);
                mapContainer.setVisibility(View.VISIBLE);
                TableLayout tableContainer = (TableLayout) findViewById(R.id.tableContainer);
                tableContainer.setVisibility(View.INVISIBLE);
            }
        });
    }
}
