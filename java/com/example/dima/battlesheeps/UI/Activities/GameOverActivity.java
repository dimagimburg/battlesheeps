package com.example.dima.battlesheeps.UI.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.DBHelper;
import com.example.dima.battlesheeps.BL.Difficulty;
import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Constants.Constants;

import org.w3c.dom.Text;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        View v;
        String whoWon =  getIntent().getExtras().getString(Constants.WINNER_KEY);
        if(whoWon.equals("Player")){
            Game mGame = (Game) getIntent().getExtras().getParcelable("game");
            int score = getIntent().getExtras().getInt("score");
            int difficulty = getIntent().getExtras().getInt("difficulty");
            mGame.mPlayer.setScore(score);
            mGame.mDifficulty = new Difficulty(difficulty);
            DBHelper dbhelper = new DBHelper(this);
            mGame.dbhelper = dbhelper;
            v = findViewById(R.id.winnerPlayer);
            TextView scoreText = (TextView) findViewById(R.id.scoreText);
            scoreText.setText("Score: " + score);
            if(mGame.isHighScore()){
                final String[] name = new String[1];
                LayoutInflater li = LayoutInflater.from(GameOverActivity.this);
                View promptsView = li.inflate(R.layout.set_name_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameOverActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        name[0] = userInput.getText().toString();
                                        Log.e("NAMEEEEE",name[0]);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
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
