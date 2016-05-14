package com.example.dima.battlesheeps.BL;//package battleship;

import com.example.dima.battlesheeps.BL.Ship;

import java.io.Serializable;

public class Coordinate implements Serializable{
    private int x;
    private int y;

    public Coordinate(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}