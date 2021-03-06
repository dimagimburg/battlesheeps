package com.example.dima.battlesheeps.UI.Activities;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.BL.DBHelper;
import com.example.dima.battlesheeps.Controllers.BattleFieldController;
import com.example.dima.battlesheeps.MVCListeners.BattleFieldActivityEvenListener;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.example.dima.battlesheeps.UI.Fragments.PlayerContainerFragment;
import com.example.dima.battlesheeps.UI.Fragments.RivalContainerFragment;
import com.example.dima.battlesheeps.UI.Services.OrientationService;
import com.example.dima.battlesheeps.UI.UIListeners.GameActivityPlayerFieldListener;
import com.example.dima.battlesheeps.UI.UIListeners.GameActivityRivalFieldListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class GameActivity extends AppCompatActivity implements Serializable {

    private final String TAG = "GameActivity";

    private Game mGame;
    private Vector<BattleFieldActivityEvenListener> mMCVListeners = new Vector<>();
    private Vector<GameActivityRivalFieldListener> mRivalFieldListeners = new Vector<>();
    private Vector<GameActivityPlayerFieldListener> mPlayerFieldListeners = new Vector<>();
    private BattleFieldController mController;
    private int shipsCounter;
    private int sheepsDead = 0;
    private OrientationService orientationService;
    private boolean mOrientaionServiceBound = false;
    private float initialOrientation;
    private boolean initialOrientationSet = false;
    private int difficulty;
    BroadcastReceiver receiver;
    private int score = 0;
    private Chronometer mChronometer;
    private boolean startedChronometer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate");
        DBHelper dbhelper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        HashMap settings = (HashMap) getIntent().getExtras().getSerializable(Constants.BUNDLE_SETTINGS_KEY);
        try {
            difficulty = Integer.parseInt((String) settings.get(Constants.SETTINGS_DIFFICULTY_KEY));
            Log.e(TAG,"diff: " + difficulty);
            mGame = new Game(difficulty,dbhelper);
        } catch (NullPointerException e){
            Log.e(TAG, e.getMessage() + " [ no setting with key : " + Constants.SETTINGS_DIFFICULTY_KEY + " ]");
        }

        mController = new BattleFieldController(mGame, this);
        setContentView(R.layout.activity_game);

        TextView v = (TextView) findViewById(R.id.remainngSheepsCount);
        shipsCounter = mGame.getNumberOfRivalSheeps();
        v.setText(shipsCounter + "");


        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putParcelable("game", mGame);

            FragmentTransaction ftRival = getFragmentManager().beginTransaction();
            RivalContainerFragment rivalContainerFragment = new RivalContainerFragment();
            rivalContainerFragment.setArguments(args);
            ftRival.add(R.id.rivalFieldWrapper, rivalContainerFragment).commit();

            FragmentTransaction ftPlayer = getFragmentManager().beginTransaction();
            PlayerContainerFragment playerContainerFragment = new PlayerContainerFragment();
            playerContainerFragment.setArguments(args);
            ftPlayer.add(R.id.playerFieldWrapper, playerContainerFragment).commit();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView orientationNumber = (TextView) findViewById(R.id.orientationNumber);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                float f = intent.getFloatExtra("orientation", 0);
                if(!initialOrientationSet){
                    initialOrientationSet = true;
                    initialOrientation = f;
                }

                float currentOrientation = initialOrientation - f;
                if(Math.abs(currentOrientation) < Constants.ORIENTATION_DIFF_ACCEPTED / 2){
                    orientationNumber.setTextColor(Color.GREEN);
                    mChronometer = null;
                    startedChronometer = false;
                } else if(Math.abs(currentOrientation) < Constants.ORIENTATION_DIFF_ACCEPTED){
                    orientationNumber.setTextColor(Color.YELLOW);
                    mChronometer = null;
                    startedChronometer = false;
                } else {
                    if(!startedChronometer){
                        mChronometer = new Chronometer(getBaseContext());
                        mChronometer.setBase(SystemClock.elapsedRealtime());
                        mChronometer.start();
                        startedChronometer = true;
                    } else {
                        if(SystemClock.elapsedRealtime() - mChronometer.getBase() > 2000){
                            Log.e(TAG, "time elapsed: " + (SystemClock.elapsedRealtime() - mChronometer.getBase()));
                            mGame.mComputerBoard.decreaseRandomHit();
                            score += (Constants.HIT_SCORE * (-1) * (difficulty + 1)) - 1;
                            updateScore();
                            mGame.mComputerBoard.shuffleShip();
                            fireRivalBoardChanged();
                            mChronometer = null;
                            startedChronometer = false;
                        }
                    }
                    orientationNumber.setTextColor(Color.RED);
                }
                orientationNumber.setText((int) currentOrientation + "");
            }
        };

        Intent intent = new Intent(this, OrientationService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter("orientation")
        );
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onPause();
        if (mOrientaionServiceBound) {
            unbindService(mServiceConnection);
            mOrientaionServiceBound = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

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

    private void fireRivalBoardChanged(){
        for(GameActivityRivalFieldListener l : mRivalFieldListeners){
            l.onUpdateBoard();
        }
    }

    private void firePlayerPlays(int x, int y) {
        for(BattleFieldActivityEvenListener l : mMCVListeners){
            l.playerPlays(x, y);
        }
    }

    private void firePlayerPlayed(int x, int y, String status, boolean isGameOver, boolean playerWon, boolean rivalWon, int[] shipCount){
        for(GameActivityRivalFieldListener l : mRivalFieldListeners){
            l.onPlayerPlayed(x, y, status, isGameOver, playerWon, rivalWon, shipCount);
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
        bundle.putInt("score", score);
        bundle.putInt("difficulty",difficulty);
        bundle.putParcelable("game", mGame);
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

    public void onPlayerPlayed(int x, int y, String status, boolean isGameOver, boolean playerWon, boolean rivalWon, int[] shipCount) {
        firePlayerPlayed(x, y, status, isGameOver, playerWon, rivalWon, shipCount);

        if(status.equals("Miss")){
            score += Constants.MISS_SCORE * (difficulty + 1);
        }

        if(status.equals("Hit") || status.equals("Sunk")){

            if(status.equals("Hit")){
                score += Constants.HIT_SCORE * (difficulty + 1);
            } else {
                score += Constants.SUNK_SCORE * (difficulty + 1);
            }

            shipsCounter--;
            sheepsDead++;
            TextView tv1 = (TextView) findViewById(R.id.deadSheepsCount);
            tv1.setText(sheepsDead + "");
            TextView tv2 = (TextView) findViewById(R.id.remainngSheepsCount);
            tv2.setText(shipsCounter + "");
        }

        updateScore();
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
        if(status.equals("Hit") || status.equals("Sunk")){
            score += Constants.RIVAL_HIT_SCORE * (difficulty + 1);
        }
        updateScore();
    }

    public void updateScore(){
        TextView tv = (TextView) findViewById(R.id.score);
        tv.setText(score + "");
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mOrientaionServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            OrientationService.OrientationServiceBinder myBinder = (OrientationService.OrientationServiceBinder) service;
            orientationService = myBinder.getService();
            mOrientaionServiceBound = true;
        }
    };
}
