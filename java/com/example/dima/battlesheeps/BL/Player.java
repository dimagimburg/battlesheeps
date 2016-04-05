//package com.example.dima.battlesheeps.BL;
package battleship;
import java.io.Serializable;

/**
 * Created by Felix on 02.04.2016.
 */
public class Player implements Serializable {
    private String mName;
    private int mwins=0;
    private int mlosses=0;

    public Player(String mName){
        this.mName=mName;
    }
}
