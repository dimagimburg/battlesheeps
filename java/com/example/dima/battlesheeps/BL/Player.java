package com.example.dima.battlesheeps.BL;
//package battleship;
import java.io.Serializable;

/**
 * Created by Felix on 02.04.2016.
 */
public class Player implements Serializable {
    private String mName;
    private boolean gameStatus;
    private int mwins=0;
    private int mlosses=0;

    public Player(String mName){
        this.mName=mName;
        gameStatus=false;
    }

    public String getName() {
        return mName;
    }

    public boolean isWinner() {
        return gameStatus;
    }

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getWins() {
        return mwins;
    }

    public void addWin() {
        this.mwins++;
    }

    public int getLosses() {
        return mlosses;
    }

    public void addLoss() {
        this.mlosses++;
    }
    
}
