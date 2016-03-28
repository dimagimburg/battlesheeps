package com.example.dima.battlesheeps.Controllers;

import android.util.Log;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.MVCListeners.MainActivityEventListener;
import com.example.dima.battlesheeps.MVCListeners.GameSettingsEventListener;
import com.example.dima.battlesheeps.UI.Activities.MainActivity;
import com.example.dima.battlesheeps.UI.Fragments.DifficultyFragment;

public class GameController implements GameSettingsEventListener, MainActivityEventListener {

    private final String TAG = "GameController";

    private Game mGame;

    public MainActivity getmMainActivity() {
        return mMainActivity;
    }

    public void setmMainActivity(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
    }

    private MainActivity mMainActivity;

    public GameController(Game game, MainActivity difficultyFragment){
        setmMainActivity(difficultyFragment);
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
}
