package com.example.dima.battlesheeps.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dima.battlesheeps.BL.DBHelper;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class HighScore extends AppCompatActivity {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        int difficulty = getIntent().getExtras().getInt("difficulty");

        TextView dif = (TextView) findViewById(R.id.forDifficulty);

        switch(difficulty){
            case 0:
                dif.setText("High Scores For Difficulty: Baby Sheep [0]");
                break;
            case 1:
                dif.setText("High Scores For Difficulty: Rambo Sheep [1]");
                break;
            case 2:
                dif.setText("High Scores For Difficulty: Killer Sheep [2]");
                break;

        }

        DBHelper helper = new DBHelper(this);

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> scores = helper.getScoresTable(difficulty);
        int count = 0;

        ArrayList<LinearLayout> rows = new ArrayList<>();
        String[] forMarker = new String[3];


        for(String s: scores){

            if(count % 4 == 0){
                Log.e("name", s);
                LinearLayout l = new LinearLayout(this);
                l.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                l.setWeightSum(2);
                TextView tv = new TextView(this);
                tv.setText(s);
                tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                tv.setTextSize(25);
                l.addView(tv);
                rows.add(l);
                forMarker[0] = s;
            }
            if(count % 4 == 1){
                forMarker[1] = s;
                Log.e("score", s);
                TextView tv1 = new TextView(this);
                tv1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                tv1.setGravity(Gravity.RIGHT);
                tv1.setText(s);
                tv1.setTextSize(25);
                rows.get(rows.size() - 1).addView(tv1);
            }

            if(count % 4 == 2){
                forMarker[2] = s;
            }

            if(count % 4 == 3){
                double latitude = Double.parseDouble(s);
                double longitude = Double.parseDouble(forMarker[2]);
                String title = "Player: " + forMarker[0] + ", Score: " + forMarker[1];
                MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(title);
                googleMap.addMarker(marker);
            }
            count++;
        }

        Collections.reverse(rows);

        LinearLayout table = (LinearLayout) findViewById(R.id.tableContainer);

        for(LinearLayout l : rows){
            table.addView(l);
        }

        Button tableBtn = (Button) findViewById(R.id.tableBtn);
        Button mapBtn = (Button) findViewById(R.id.mapBtn);

        tableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout mapContainer = (RelativeLayout) findViewById(R.id.mapContainer);
                mapContainer.setVisibility(View.INVISIBLE);
                LinearLayout tableContainer = (LinearLayout) findViewById(R.id.tableContainer);
                tableContainer.setVisibility(View.VISIBLE);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout mapContainer = (RelativeLayout) findViewById(R.id.mapContainer);
                mapContainer.setVisibility(View.VISIBLE);
                LinearLayout tableContainer = (LinearLayout) findViewById(R.id.tableContainer);
                tableContainer.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
