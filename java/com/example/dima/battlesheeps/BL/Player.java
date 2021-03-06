package com.example.dima.battlesheeps.BL;
//package battleship;
import java.io.Serializable;

/**
 * Created by Felix on 02.04.2016.
 */
public class Player implements Serializable {
    private String mName;
    private boolean gameStatus;
    private int Score=0;
    private int mtries=0;
    private int mlosses=0;
    private int mwins=0;
    private double mLatitude=0;
    private double mLongitude=0;


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
    public double getLatitude() {
        return mLatitude;
    }
    public void setLatitude(double num) {
        this.mLatitude=num;
    }
    public double getLongitude() {
        return mLongitude;
    }
    public void setLongitude(double num) {
        this.mLongitude=num;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}