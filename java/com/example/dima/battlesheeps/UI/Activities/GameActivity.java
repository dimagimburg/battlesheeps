package com.example.dima.battlesheeps.UI.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.Controllers.BattleFieldController;
import com.example.dima.battlesheeps.MVCListeners.BattleFieldActivityEvenListener;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.example.dima.battlesheeps.UI.Fragments.PlayerContainerFragment;
import com.example.dima.battlesheeps.UI.Fragments.RivalContainerFragment;
import com.example.dima.battlesheeps.UI.UIListeners.GameActivityPlayerFieldListener;
import com.example.dima.battlesheeps.UI.UIListeners.GameActivityRivalFieldListener;

import java.util.HashMap;
import java.util.Vector;

public class GameActivity extends AppCompatActivity{

    private final String TAG = "GameActivity";

    private Game mGame;
    private Vector<BattleFieldActivityEvenListener> mMCVListeners = new Vector<>();
    private Vector<GameActivityRivalFieldListener> mRivalFieldListeners = new Vector<>();
    private Vector<GameActivityPlayerFieldListener> mPlayerFieldListeners = new Vector<>();
    private BattleFieldController mController;
    private int shipsCounter;
    private int sheepsDead = 0;

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

        TextView v = (TextView) findViewById(R.id.sheeps_left);
        shipsCounter = mGame.getNumberOfRivalSheeps();
        v.setText(shipsCounter + "");

        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putSerializable("game", mGame);

            FragmentTransaction ftPlayer = getFragmentManager().beginTransaction();
            PlayerContainerFragment playerContainerFragment = new PlayerContainerFragment();
            playerContainerFragment.setArguments(args);
            ftPlayer.add(R.id.playerField, playerContainerFragment).commit();

            FragmentTransaction ftRival = getFragmentManager().beginTransaction();
            RivalContainerFragment rivalContainerFragment = new RivalContainerFragment();
            rivalContainerFragment.setArguments(args);
            ftRival.add(R.id.rivalField, rivalContainerFragment).commit();

        }
    }

    public void registerBattleFieldActivityListener(BattleFieldActivityEvenListener l){
        mMCVListeners.add(l);
    }

    public void registerGameActivityRivalEventListener(GameActivityRivalFieldListener l){
        mRivalFieldListeners.add(l);
    }

    public void registerGameActivityPlayerEventListener(GameActivityPlayerFieldListener l){
        mPlayerFieldListeners.add(l);
    }

    private void firePlayerPlays(int x, int y) {
        for(BattleFieldActivityEvenListener l : mMCVListeners){
            l.playerPlays(x, y);
        }
    }

    private void firePlayerPlayed(int x, int y, String status, boolean isGameOver, boolean playerWon, boolean rivalWon){
        for(GameActivityRivalFieldListener l : mRivalFieldListeners){
            l.onPlayerPlayed(x, y, status, isGameOver, playerWon, rivalWon);
        }
    }

    private void fireRivalPlayed(String status, boolean gameOver, boolean playerWins, boolean rivalWins){
        for(GameActivityPlayerFieldListener l : mPlayerFieldListeners){
            l.onRivalPlayed(status, gameOver, playerWins, rivalWins);
        }
    }

    public void fireRivalPlays(){
        for(BattleFieldActivityEvenListener l : mMCVListeners){
            l.rivalPlays();
        }
    }

    public void playerPlays(int x, int y) {
        firePlayerPlays(x, y);
    }

    public void playerWon(){
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.WINNER_KEY, "Player");
        intent.putExtras(bundle);
        GameActivity.this.startActivity(intent);
    }

    public void rivalWon(){
        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.WINNER_KEY, "Rival");
        intent.putExtras(bundle);
        GameActivity.this.startActivity(intent);
    }

    private void showLoader(){
        View v = findViewById(R.id.loader);
        v.setVisibility(View.VISIBLE);
    }

    private void hideLoader(){
        View v = findViewById(R.id.loader);
        v.setVisibility(View.INVISIBLE);
    }

    public void onPlayerPlayed(int x, int y, String status, boolean isGameOver, boolean playerWon, boolean rivalWon) {
        firePlayerPlayed(x, y, status, isGameOver, playerWon, rivalWon);
        if(status.equals("Hit") || status.equals("Sunk")){
            shipsCounter--;
            sheepsDead++;
            TextView tv1 = (TextView) findViewById(R.id.sheeps_dead);
            tv1.setText(sheepsDead + "");
            TextView tv2 = (TextView) findViewById(R.id.sheeps_left);
            tv2.setText(shipsCounter + "");
        }
        showLoader();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fireRivalPlays(); // here the simulation of PC play
                        hideLoader();

                    }
                });

            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void onRivalPlayed(String status, boolean gameOver, boolean playerWins, boolean rivalWins){
        fireRivalPlayed(status, gameOver, playerWins, rivalWins);
    }



}
