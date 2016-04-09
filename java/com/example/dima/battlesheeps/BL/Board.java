package com.example.dima.battlesheeps.BL;
//package battleship;
import java.io.Serializable;
import java.util.Random;


public class Board implements Serializable {

    public Tile[][] board;
    private int mSize; //board size
    private int[] mShipNumbers; //how much ships per type
    private int mShipLeft; //ship left on board
    //private ArrayList<String> mShipNames = new ArrayList<String>();
    //private ArrayList<Integer> mShipNumbers = new ArrayList<Integer>();
    private String[] mShipNames={"Lieutenant","Sergeant","Corporal","Private"};
    public Board(int size,int[] shipNumber){
        this.mSize=size;//ship size
        mShipNumbers=shipNumber;
        this.mShipLeft=sum(shipNumber);
        BoardInit();
    }

    public int getSize() {
        return mSize;
    }
    public String getTileStatus(int coordX,int coordY) {
        return board[coordX][coordY].getStatus();
    }
    private int sum(int[] arr) {
        int sum=0;
        for (int i:arr)
            sum+=i;
        return sum;
    }

    private void BoardInit(){
        Random r = new Random();
        int coordX;
        int coordY;
        boolean finished=false;
        boolean tileFree=true;
        boolean direction; //true=right false=down
        board=new Tile[this.mSize][this.mSize];
        for(int i=0;i<this.mSize;i++){
            for(int j=0;j<this.mSize;j++){
                board[i][j]=new Tile();
                //Ship sh=new Ship ("",1);
                // board[i][j].setShip(sh);
            }
        }
        for (int num=0;num<mShipNumbers.length;num++) {
            for(int j=0;j<mShipNumbers[num];j++) {
                int ShipSize = num-j+1;
                Ship newship = new Ship(mShipNames[num-j], ShipSize);
                finished=false;
                tileFree=true;
                while (!finished) {
                    tileFree=true;
                    direction = r.nextBoolean(); //true=right false=down
                    if (direction){
                        coordX = r.nextInt(mSize - ShipSize); //random x coordinate
                        coordY = r.nextInt(mSize); //random y coordinate
                    }
                    else{
                        coordX = r.nextInt(mSize); //random x coordinate
                        coordY = r.nextInt(mSize - ShipSize); //random y coordinate
                    }

                    for (int k = 0; k < ShipSize && tileFree; k++) {//check that the tiles are free before allocating the ship
                        if (direction) {
                            if(board[coordX + k][coordY].getmShip()==null || board[coordX + k][coordY].isFree()){
                                tileFree=true;
                                System.out.println("free");
                            }

                            else{
                                tileFree=false;
                                System.out.println("held");
                            }

                        }
                        else {
                            if(board[coordX][coordY + k].getmShip()==null || board[coordX][coordY + k].isFree()){
                                tileFree=true;
                                System.out.println("free");

                            }

                            else{
                                tileFree=false;
                                System.out.println("held");
                            }

                        }
                    }

                    for (int k = 0; k < ShipSize && tileFree; k++) {// if the tiles free allocating the ship
                        if (direction){
                            board[coordX + k][coordY].setShip(newship);
                            board[coordX + k][coordY].setFree(false);
                            board[coordX + k][coordY].setStatus("Occupied");
                        }
                        else {
                            board[coordX][coordY + k].setShip(newship);
                            board[coordX][coordY + k].setFree(false);
                            board[coordX][coordY + k].setStatus("Occupied");
                        }
                    }
                    finished=tileFree;//if allocated, finish the while
                    System.out.println(num);
                }

            }
        }

    }

    public String Play(int coordX,int coordY){
        if (board[coordX][coordY].getStatus()== "Free" || board[coordX][coordY].getStatus()== "Occupied")//if free new tile
            if (board[coordX][coordY].getmShip()==null){ // empty
                board[coordX][coordY].setStatus("Miss");
                return "Miss";
            }
            else {
                board[coordX][coordY].getmShip().addHit();//add hit to ship
                if (board[coordX][coordY].getmShip().isSunk()) {//last hit on the ship== ship sunk
                    mShipLeft--;
                    board[coordX][coordY].setStatus("Sunk");
                    if (mShipLeft==0)
                        return "Win";
                    else {
                        return "Sunk";
                    }
                }
                else {
                    board[coordX][coordY].setStatus("Hit");
                    return "Hit";
                }
            }

        else{ //if not free already played
            return "fired";
        }
    }

}