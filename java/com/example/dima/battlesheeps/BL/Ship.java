package com.example.dima.battlesheeps.BL;
//package battleship;
import java.io.Serializable;
import java.util.ArrayList;

public class Ship implements Serializable {
    private String mshipType;
    private int msize;
    private boolean horizontal;
    private ArrayList<Integer> tilesX = new ArrayList<>();
    private ArrayList<Integer> tilesY = new ArrayList<>();
    private ArrayList<Coordinate> coordinates = new ArrayList<>();
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
    public void deleteX(int index) {
        this.tilesX.remove(index);
    }
    public void deleteY(int index) {
        this.tilesY.remove(index);
    }
    public void addHit() {
        this.mhits++;
        if (mhits==msize)
            misSunk=true;
    }
    public void decreaseHit() {
        this.mhits--;
    }
    public void addCoordinate(int x,int y) {
        coordinates.add(new Coordinate(x,y));
    }
    public void setCoordinateX(int index,int x) {
        coordinates.get(index).setX(x);
    }
    public void setCoordinateY(int index,int y) {
        coordinates.get(index).setY(y);
    }
    public void setCoordinateX(int index) {
        int x=coordinates.get(index).getX();
        coordinates.get(index).setX(x++);
    }
    public void setCoordinateY(int index) {
        int y=coordinates.get(index).getY();
        coordinates.get(index).setY(y++);
    }
    public Coordinate getCoordinate(int index) {
        return coordinates.get(index);
    }
    public int getSize() {
        return msize;
    }

    public boolean isSunk() {
        return misSunk;
    }
    public boolean isHit() {
        if(mhits>0)
            return true;
        else return false;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }
}

