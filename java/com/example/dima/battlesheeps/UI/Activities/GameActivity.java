package com.example.dima.battlesheeps.UI.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dima.battlesheeps.BL.Board;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Fragments.PlayerContainerFragment;
import com.example.dima.battlesheeps.UI.Views.BoardView;

public class GameActivity extends AppCompatActivity {

    //private Board mBoard;
    //private int mDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //mBoard = (Board) getIntent().getExtras().getSerializable("board");
        //mDifficulty = getIntent().getExtras().getInt("difficulty");

       // BoardView lBoardView = (BoardView) findViewById(R.id.BoardView);

        if (savedInstanceState == null) {
            //Fragment newFragment = new PlayerContainerFragment();
            //FragmentTransaction ft = getFragmentManager().beginTransaction();
            //ft.add(R.id.PlayerBoardHolder, newFragment).commit();
        }
    }

}
