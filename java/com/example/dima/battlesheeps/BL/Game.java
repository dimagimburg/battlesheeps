package com.example.dima.battlesheeps.BL;

import com.example.dima.battlesheeps.MVCListeners.GameSettingsEventListener;

import java.io.Serializable;
import java.util.Vector;

public class Game implements Serializable{

    private Board mBoard;
    private int mDifficulty = 1; // default
    private Vector<GameSettingsEventListener> mListeners = new Vector<>();

    public Game(){
        mBoard = new Board();
        mDifficulty = 1;
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
        fireGameDifficultyChanged(mDifficulty);

    }

    public void registerListener(GameSettingsEventListener gel){
        mListeners.add(gel);
    }

    private void fireGameDifficultyChanged(int difficulty){
        for(GameSettingsEventListener l : mListeners){
            l.changedDifficulty(difficulty);
        }
    }
}
