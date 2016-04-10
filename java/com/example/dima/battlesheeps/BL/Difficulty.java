package com.example.dima.battlesheeps.BL;
//package battleship;

import java.io.Serializable;

public class Difficulty implements Serializable {
    final int[] mDefaultBoardSize={8,10,12};
    final int[] mDefaultShipNumber={1,2,3,4};
    enum  mDifficultyEnum{
        Easy, Normal, Hard
    }

    private mDifficultyEnum mName;
    private int mDifficulty;
    private int mBoardSize;
    public Difficulty(int difficulty){
        this.mDifficulty=difficulty;
        this.mBoardSize=mDefaultBoardSize[difficulty];
        switch(difficulty){
            case 0:this.mName=mDifficultyEnum.Easy;
                break;
            case 1:this.mName=mDifficultyEnum.Normal;
                break;
            case 2:this.mName=mDifficultyEnum.Hard;
                break;
        }

    }

    public int getBoardSize() {
        return mBoardSize;
    }

}
