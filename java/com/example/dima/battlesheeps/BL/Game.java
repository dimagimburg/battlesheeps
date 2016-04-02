package com.example.dima.battlesheeps.BL;

import android.util.Log;

import com.example.dima.battlesheeps.MVCListeners.GameEventListener;

import java.io.Serializable;
import java.util.Vector;

public class Game implements Serializable {

    private final String TAG = "BL.Game";
    private Board mBoard;
    private int mDifficulty = 1; // default
    private Vector<GameEventListener> mListeners = new Vector<>();

    public Game(int difficulty){
        mBoard = new Board();
        mDifficulty = difficulty;
    }

    public Board getmBoard() {
        return mBoard;
    }

    public void setmBoard(Board mBoard) {
        this.mBoard = mBoard;
    }

    public int getmDifficulty() {
        return mDifficulty;
    }

    public void setmDifficulty(int mDifficulty) {
        this.mDifficulty = mDifficulty;
        Log.e(TAG, "setmDifficulty(" + mDifficulty + ")");
        fireGameDifficultyChanged(mDifficulty);
    }

    public void registerListener(GameEventListener gel){
        mListeners.add(gel);
    }

    private void fireGameDifficultyChanged(int difficulty){
        for(GameEventListener l : mListeners){
            l.changedDifficulty(difficulty);
        }
    }

    public int getBoardSize(){
        switch (mDifficulty){
            case 1:
                default:
                return 16;
            case 2:
                return 64;
            case 3:
                return 100;
        }
    }


}
