package com.example.dima.battlesheeps.BL;//package battleship;

import com.example.dima.battlesheeps.BL.Ship;

import java.io.Serializable;

public class Tile implements Serializable{
    private Ship mShip;
    private String mstatus="Free";
    private boolean mfree=true;

    public boolean isFree() {
        return mfree;
    }

    public void setFree(boolean isFree) {
        this.mfree = isFree;
    }

    public Ship getmShip() {
        return mShip;
    }

    public void setShip(Ship mShip) {
        this.mShip = mShip;
    }

    public String getStatus() {
        return mstatus;
    }

    public void setStatus(String mstatus) {
        this.mstatus = mstatus;
    }
}