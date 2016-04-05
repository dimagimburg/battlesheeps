//package com.example.dima.battlesheeps.BL;
package battleship;
//import android.util.Log;

//import com.example.dima.battlesheeps.MVCListeners.GameEventListener;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class Game implements Serializable {
    private final String TAG = "BL.Game";
    public Board mPlayerBoard;
    public Board mComputerBoard;
    private Player mPlayer;
    private int[] mLastHit=null;
    private boolean isLastTurnHit=false;
    private boolean direction=true; //true=right false=down
    private Difficulty mDifficulty; // Normal default
    final int[] mBoardSize={8,10,12};
    //private Vector<GameEventListener> mListeners = new Vector<>();

    public Game(int difficulty,Player player){
        this.mPlayer=player;
        this.mDifficulty=new Difficulty(difficulty);
        this.mPlayerBoard = new Board(this.mDifficulty.getBoardSize(),mDifficulty.mDefaultShipNumber);
        this.mComputerBoard = new Board(this.mDifficulty.getBoardSize(),mDifficulty.mDefaultShipNumber);
    }

    public int getBoardSize() {
        return mPlayerBoard.getSize();
    }
    public String playersPlay(int coordX,int coordY) {
        String status=mComputerBoard.Play(coordX, coordY);
        return status;
    }

    public String computerPlay() {
        Random r = new Random();
        int coordX;
        int coordY;
        String status=null;
        if(mLastHit==null) {
            coordX = r.nextInt(mPlayerBoard.getSize()); //random x coordinate
            coordY = r.nextInt(mPlayerBoard.getSize()); //random y coordinate
            status=mPlayerBoard.Play(coordX, coordY);
            if (status=="Hit") {
                mLastHit[0] = coordX;
                mLastHit[0] = coordY;
                isLastTurnHit=true;
            }
            if (status=="Miss") {
                mLastHit[0] = coordX;
                mLastHit[0] = coordY;
                isLastTurnHit=true;
            }
            return status;
        }
        else{
            if(direction)
            return mPlayerBoard.Play(mLastHit[0], mLastHit[1]+1);
            else{

            }
        }
        return status;
    }

    public Difficulty getDifficulty() {
        return mDifficulty;
    }


    /*public void registerListener(GameEventListener gel){
        mListeners.add(gel);
    }

    private void fireGameDifficultyChanged(int difficulty){
        for(GameEventListener l : mListeners){
            l.changedDifficulty(difficulty);
        }
    }*/
}
enum status {
    Hidden, Hit, Miss
}

enum  shipName{
    Private, Corpral, Sergant
}
