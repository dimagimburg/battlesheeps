package com.example.dima.battlesheeps.BL;
//package battleship;
import java.io.Serializable;

public class Ship implements Serializable {
    private String mshipType;
    private int msize;
    private boolean misSunk=false;
    private int mhits=0;
    public Ship(String shipType,int size){
        this.mshipType=shipType;
        this.msize=size;
    }

    public String getShipType() {
        return mshipType;
    }

    public int getHits() {
        return mhits;
    }

    public void addHit() {
        this.mhits++;
        if (mhits==msize)
            misSunk=true;
    }

    public int getSize() {
        return msize;
    }

    public boolean isSunk() {
        return misSunk;
    }

}

