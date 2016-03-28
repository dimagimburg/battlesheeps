package com.example.dima.battlesheeps.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dima.battlesheeps.BL.Board;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Views.BoardView;

public class GameActivity extends AppCompatActivity {

    private Board mBoard;
    private int mDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mBoard = (Board) getIntent().getExtras().getSerializable("board");
        mDifficulty = getIntent().getExtras().getInt("difficulty");

        BoardView lBoardView = (BoardView) findViewById(R.id.BoardView);

    }
}
