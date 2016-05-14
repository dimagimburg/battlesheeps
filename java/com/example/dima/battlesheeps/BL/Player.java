package com.example.dima.battlesheeps.BL;
//package battleship;
import java.io.Serializable;

/**
 * Created by Felix on 02.04.2016.
 */
public class Player implements Serializable {
    private String mName;
    private boolean gameStatus;
    private int mtries=0;
    private int mlosses=0;
    private int mwins=0;
    private long mLatitude=0;
    private long mLongitude=0;


    public Player(String mName){
        this.mName=mName;
        gameStatus=false;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
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
    public int getTries() {
        return mtries;
    }
    public void addLoss() {
        this.mlosses++;
    }
    public void addTry() {
        this.mtries++;
    }
    public long getLatitude() {
        return mLatitude;
    }
    public void setLatitude(long num) {
        this.mLatitude=num;
    }
    public long getLongitude() {
        return mLongitude;
    }
    public void setLongitude(long num) {
        this.mLongitude=num;
    }
    
}
