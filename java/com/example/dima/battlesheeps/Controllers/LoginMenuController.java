package com.example.dima.battlesheeps.Controllers;

import android.util.Log;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.MVCListeners.MainActivityEventListener;
import com.example.dima.battlesheeps.MVCListeners.GameEventListener;
import com.example.dima.battlesheeps.UI.Activities.MainActivity;

import java.io.Serializable;

public class LoginMenuController implements GameEventListener, MainActivityEventListener, Serializable {

    private final String TAG = "LoginMenuController";

    private Game mGame;

    private MainActivity mMainActivity;

    public LoginMenuController(Game game, MainActivity mainActivity){
        setmMainActivity(mainActivity);
        setmGame(game);

        mMainActivity.registerMVCListener(this);
        mGame.registerListener(this);
    }

    public Game getmGame() {
        return mGame;
    }

    public void setmGame(Game mGame) {
        this.mGame = mGame;
    }

    public MainActivity getmMainActivity() {
        return mMainActivity;
    }

    public void setmMainActivity(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
    }

    @Override
    public void difficultyChangedOnDialog(int difficulty) {
        Log.e(TAG,"difficultyChangedOnDialog");
        mGame.setmDifficulty(difficulty);
    }

    @Override
    public void changedDifficulty(int difficulty) {
        Log.e(TAG, "changedDifficulty");
        mMainActivity.setNewDifficultyOnView(difficulty);
    }

    public void destroy(){
        mGame = null;
        mMainActivity = null;
    }
}
