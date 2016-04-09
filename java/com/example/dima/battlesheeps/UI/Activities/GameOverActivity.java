package com.example.dima.battlesheeps.UI.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Constants.Constants;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v;
        setContentView(R.layout.activity_game_over);
        String whoWon =  getIntent().getExtras().getString(Constants.WINNER_KEY);
        setContentView(R.layout.activity_game);
        if(whoWon.equals("Player")){
            v = findViewById(R.id.winnerPlayer);
        } else {
            v = findViewById(R.id.winnerRival);
        }
        v.setVisibility(View.VISIBLE);
    }

}
