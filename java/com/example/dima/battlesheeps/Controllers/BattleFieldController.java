package com.example.dima.battlesheeps.Controllers;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.MVCListeners.BattleFieldActivityEvenListener;
import com.example.dima.battlesheeps.MVCListeners.GameEventListener;
import com.example.dima.battlesheeps.UI.Activities.GameActivity;

import java.io.Serializable;

public class BattleFieldController implements GameEventListener, BattleFieldActivityEvenListener, Serializable{

    private final String TAG = "BattleFieldController";

    private Game mGame;
    private GameActivity mGameActivity;

    public BattleFieldController(Game game, GameActivity gameActivity){
        setmGame(game);
        setmGameActivity(gameActivity);

        mGame.registerListener(this);
        mGameActivity.registerListener(this);
    }

    public Game getmGame() {
        return mGame;
    }

    public void setmGame(Game mGame) {
        this.mGame = mGame;
    }

    public GameActivity getmGameActivity() {
        return mGameActivity;
    }

    public void setmGameActivity(GameActivity mGameActivity) {
        this.mGameActivity = mGameActivity;
    }

    @Override
    public void changedDifficulty(int difficulty) {
        // nothing to do here
    }
}
