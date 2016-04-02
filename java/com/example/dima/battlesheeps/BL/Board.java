package com.example.dima.battlesheeps.BL;

import java.io.Serializable;

public class Board implements Serializable {

    private int[] board = new int[64];

    public Board(){

    }

    public int[] getBoard() {
        return board;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }

}
