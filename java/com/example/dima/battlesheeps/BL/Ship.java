package com.example.dima.battlesheeps.BL;
//package battleship;
import java.io.Serializable;
import java.util.ArrayList;

public class Ship implements Serializable {
    private String mshipType;
    private int msize;
    private ArrayList<Integer> tilesX = new ArrayList<>();
    private ArrayList<Integer> tilesY = new ArrayList<>();
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
    public void addX(int x) {
        this.tilesX.add(x);
    }
    public void addY(int y) {
        this.tilesY.add(y);
    }
    public int getX(int index) {
        return this.tilesX.get(index);
    }
    public int getY(int index) {
        return this.tilesY.get(index);
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

