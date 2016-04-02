package com.example.dima.battlesheeps.UI.Activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.Controllers.BattleFieldController;
import com.example.dima.battlesheeps.MVCListeners.BattleFieldActivityEvenListener;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.example.dima.battlesheeps.UI.Fragments.PlayerContainerFragment;
import com.example.dima.battlesheeps.UI.Fragments.RivalContainerFragment;

import java.util.HashMap;
import java.util.Vector;

public class GameActivity extends AppCompatActivity {

    private final String TAG = "GameActivity";

    private Game mGame;
    private Vector<BattleFieldActivityEvenListener> mMCVListeners = new Vector<>();
    private BattleFieldController mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap settings = (HashMap) getIntent().getExtras().getSerializable(Constants.BUNDLE_SETTINGS_KEY);
        try {
            mGame = new Game(Integer.parseInt((String) settings.get(Constants.SETTINGS_DIFFICULTY_KEY)));
        } catch (NullPointerException e){
            Log.e(TAG, e.getMessage() + " [ no setting with key : " + Constants.SETTINGS_DIFFICULTY_KEY + " ]");
        }
        mController = new BattleFieldController(mGame, this);
        setContentView(R.layout.activity_game);
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putSerializable("game", mGame);

            FragmentTransaction ftPlayer = getFragmentManager().beginTransaction();
            Fragment playerContainerFragment = new PlayerContainerFragment();
            playerContainerFragment.setArguments(args);
            ftPlayer.add(R.id.playerField, playerContainerFragment).commit();

            FragmentTransaction ftRival = getFragmentManager().beginTransaction();
            Fragment rivalContainerFragment = new RivalContainerFragment();
            rivalContainerFragment.setArguments(args);
            ftRival.add(R.id.rivalField, rivalContainerFragment).commit();

        }
    }

    public void registerListener(BattleFieldActivityEvenListener l){
        mMCVListeners.add(l);
    }

}
