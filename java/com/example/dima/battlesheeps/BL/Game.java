package com.example.dima.battlesheeps.BL;

import java.io.Serializable;

/**
 * Created by Dima on 28/03/2016.
 */
public class Game implements Serializable{

    private Board mBoard;
    private int mDifficulty;

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
    }
}
