package com.example.dima.battlesheeps.UI.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.dima.battlesheeps.BL.Board;
import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.Controllers.BattleFieldController;
import com.example.dima.battlesheeps.MVCListeners.BattleFieldActivityEvenListener;
import com.example.dima.battlesheeps.MVCListeners.MainActivityEventListener;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Fragments.PlayerContainerFragment;
import com.example.dima.battlesheeps.UI.Views.BoardView;

import java.util.Vector;

public class GameActivity extends AppCompatActivity {

    private final String TAG = "GameActivity";

    private Game mGame;
    private Vector<BattleFieldActivityEvenListener> mMCVListeners = new Vector<>();
    private BattleFieldController mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGame = (Game) getIntent().getExtras().getSerializable("game");
        mController = new BattleFieldController(mGame, this);
        setContentView(R.layout.activity_game);
       // BoardView lBoardView = (BoardView) findViewById(R.id.BoardView);
        if (savedInstanceState == null) {
            Fragment newFragment = new PlayerContainerFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.playerField, newFragment).commit();
        }
    }

    public void registerListener(BattleFieldActivityEvenListener l){
        mMCVListeners.add(l);
    }

}
