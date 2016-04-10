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
        mGameActivity.registerBattleFieldActivityListener(this);
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
    public void playerPlays(int x, int y) {
        String status = mGame.playersPlay(x,y);
        boolean gameOver = mGame.isGameOver();
        boolean playerWins = mGame.isPlayerWinner();
        boolean rivalWins = mGame.isComputerWinner();
        int[] shipCount = mGame.ComputerShipsLeft();
        mGameActivity.onPlayerPlayed(x, y, status, gameOver, playerWins, rivalWins, shipCount);
    }

    @Override
    public void rivalPlays() {
        String status = mGame.computerPlay();
        boolean gameOver = mGame.isGameOver();
        boolean playerWins = mGame.isPlayerWinner();
        boolean rivalWins = mGame.isComputerWinner();
        mGameActivity.onRivalPlayed(status, gameOver, playerWins, rivalWins);
    }
}
