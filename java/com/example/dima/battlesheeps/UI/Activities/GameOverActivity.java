package com.example.dima.battlesheeps.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Constants.Constants;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        View v;
        String whoWon =  getIntent().getExtras().getString(Constants.WINNER_KEY);
        if(whoWon.equals("Player")){
            v = findViewById(R.id.winnerPlayer);
        } else {
            v = findViewById(R.id.winnerRival);
        }
        v.setVisibility(View.VISIBLE);

        Button btn = (Button) findViewById(R.id.backToMenu);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                GameOverActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}
