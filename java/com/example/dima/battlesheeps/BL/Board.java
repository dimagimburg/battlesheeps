package com.example.dima.battlesheeps.BL;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;



public class Board implements Serializable {

    public Tile[][] board;
    private int mSize; //board size
    private int[] mShipNumbers; //how much ships per type
    private int mShipLeft; //ship left on board
    private int hits=0; //hits on board
    private int totalhits=0; //hits on board
    private ArrayList<Ship> Ships = new ArrayList<>();
    private final String TAG = "Board";
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
    public ArrayList<Ship> getShips() {
        return Ships;
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
    public void shuffleShip() {
        HashSet<Integer> tryes = new HashSet<>();
        if (totalhits == 0)
            return; //ho hits no need to move
        Random r = new Random();
        int numOfShip = r.nextInt(Ships.size());
        int x, y;
        Ship ship = Ships.get(numOfShip);

        boolean finished1 = false;
        boolean finished2 = false;
        boolean free = false;
        int direction = 0;
        while (!free) {
            if(tryes.size()==4){
                //ship = Ships.get(numOfShip);
                //while (!ship.isHit()) {
                numOfShip = r.nextInt(Ships.size());
                ship = Ships.get(numOfShip);
                // }
            }
            free = true;
            finished1 = false;
            finished2 = false;
            direction = r.nextInt(4);//0 up,1 right,2 down,3 left
            Log.e(TAG, Integer.toString(direction));
            if ((direction == 0 && ship.getCoordinate(0).getY() > 0) || (direction == 1 && ship.getCoordinate(ship.getSize() - 1).getX() < mSize - 1) ||
                    (direction == 2 && ship.getCoordinate(ship.getSize() - 1).getY() < mSize - 1) || (direction == 3 && ship.getCoordinate(0).getX() < 0)) //if the direction selected is valid on the board
                finished1 = true;
            else {
                finished1 = false;
                free = false;
                tryes.add(direction);
            }
            if (finished1) { // check that the new place is free
                if (direction == 0) {
                    if (ship.isHorizontal())
                        for (int i = 0; i < ship.getSize() && !finished2; i++) {
                            x = ship.getCoordinate(i).getX();
                            y = ship.getCoordinate(i).getY();

                            if (!board[x][y - 1].isFree()) {
                                finished2 = true;
                                free = false;
                                tryes.add(direction);
                            }

                        }
                    else {
                        x = ship.getCoordinate(0).getX();
                        y = ship.getCoordinate(0).getY();

                        if (!board[x][y - 1].isFree()) {
                            finished2 = true;
                            free = false;
                            tryes.add(direction);
                        }
                    }
                } else if (direction == 1) {
                    if (!ship.isHorizontal())
                        for (int i = ship.getSize() - 1; i >= 0 && !finished2; i--) {
                            x = ship.getCoordinate(i).getX();
                            y = ship.getCoordinate(i).getY();

                            if (!board[x + 1][y].isFree()) {
                                finished2 = true;
                                free = false;
                                tryes.add(direction);
                            }

                        }
                    else {
                        x = ship.getCoordinate(0).getX();
                        y = ship.getCoordinate(0).getY();

                        if (!board[x + 1][y].isFree()) {
                            finished2 = true;
                            free = false;
                            tryes.add(direction);
                        }
                    }
                }
                else if (direction == 2) {
                    if (ship.isHorizontal())
                        for (int i = ship.getSize() - 1; i >= 0 && !finished2; i--) {
                            x = ship.getCoordinate(i).getX();
                            y = ship.getCoordinate(i).getY();

                            if (!board[x][y + 1].isFree()) {
                                finished2 = true;
                                free = false;
                                tryes.add(direction);
                            }

                        }
                    else {
                        x = ship.getCoordinate(0).getX();
                        y = ship.getCoordinate(0).getY();

                        if (!board[x][y + 1].isFree()) {
                            finished2 = true;
                            free = false;
                            tryes.add(direction);
                        }
                    }
                }
                else if (direction == 3) {
                    if (!ship.isHorizontal())
                        for (int i = 0; i < ship.getSize() && !finished2; i++) {
                            x = ship.getCoordinate(i).getX();
                            y = ship.getCoordinate(i).getY();

                            if (!board[x + 1][y].isFree()) {
                                finished2 = true;
                                free = false;
                                tryes.add(direction);
                            }

                        }
                    else {
                        x = ship.getCoordinate(0).getX();
                        y = ship.getCoordinate(0).getY();

                        if (!board[x + 1][y].isFree()) {
                            finished2 = true;
                            free = false;
                            tryes.add(direction);
                        }
                    }
                }

            }
        }
        if(free){
            if (direction == 0) {
                for (int i = 0; i < ship.getSize(); i++) {
                    x = ship.getCoordinate(i).getX();
                    y = ship.getCoordinate(i).getY();

                    board[x][y - 1] = board[x][y];
                    board[x][y] = new Tile();
                    ship.setCoordinateY(i, y - 1);
                }
            } else if (direction == 1)
                for (int i = ship.getSize() - 1; i >= 0; i--) {
                    x = ship.getCoordinate(i).getX();
                    y = ship.getCoordinate(i).getY();

                    board[x + 1][y] = board[x][y];
                    board[x][y] = new Tile();
                    ship.setCoordinateX(i, x + 1);

                }
            else if (direction == 2)
                for (int i = ship.getSize() - 1; i >= 0; i--) {
                    x = ship.getCoordinate(i).getX();
                    y = ship.getCoordinate(i).getY();

                    board[x][y + 1] = board[x][y];
                    board[x][y] = new Tile();
                    ship.setCoordinateY(i, y + 1);

                }
            else if (direction == 3) {
                for (int i = 0; i < ship.getSize(); i++) {
                    x = ship.getCoordinate(i).getX();
                    y = ship.getCoordinate(i).getY();

                    board[x - 1][y] = board[x][y];
                    board[x][y] = new Tile();
                    ship.setCoordinateX(i, x - 1);
                }
            }
        }

    }
    public void decreaseRandomHit() {
        Random r = new Random();
        int numOfShip;
        Ship ship;
        int index,x,y;
        boolean finished=false;
        while(!finished) {
            if (hits > 0){
                numOfShip = r.nextInt(Ships.size());
                ship = Ships.get(numOfShip);
                if (!ship.isSunk() && ship.getHits() > 0) {
                    finished = true;

                    index = r.nextInt(ship.getHits());
                    x = ship.getX(index);
                    y = ship.getY(index);
                    board[x][y].getmShip().deleteX(index);
                    board[x][y].getmShip().deleteY(index);
                    ship.decreaseHit();
                    hits--;
                    board[x][y].setStatus("Occupied");
                }
            }
            else finished=true;
        }

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
            }
        }
        for (int num=0;num<mShipNumbers.length;num++) {
            for(int j=0;j<mShipNumbers[num];j++) {
                int ShipSize = num-j+1;
                Ship newship = new Ship(mShipNames[num-j], ShipSize);
                Ships.add(newship);
                finished=false;
                tileFree=true;
                while (!finished) {
                    tileFree=true;
                    direction = r.nextBoolean(); //true=right false=down
                    if (direction){
                        newship.setHorizontal(true);
                        coordX = r.nextInt(mSize - ShipSize); //random x coordinate
                        coordY = r.nextInt(mSize); //random y coordinate
                    }
                    else{
                        newship.setHorizontal(false);
                        coordX = r.nextInt(mSize); //random x coordinate
                        coordY = r.nextInt(mSize - ShipSize); //random y coordinate
                    }

                    for (int k = 0; k < ShipSize && tileFree; k++) {//check that the tiles are free before allocating the ship
                        if (direction) {
                            if(board[coordX + k][coordY].getmShip()==null || board[coordX + k][coordY].isFree()){
                                tileFree=true;

                            }

                            else{
                                tileFree=false;

                            }

                        }
                        else {
                            if(board[coordX][coordY + k].getmShip()==null || board[coordX][coordY + k].isFree()){
                                tileFree=true;


                            }

                            else{
                                tileFree=false;

                            }

                        }
                    }

                    for (int k = 0; k < ShipSize && tileFree; k++) {// if the tiles free allocating the ship
                        if (direction){
                            newship.addCoordinate(coordX + k,coordY);
                            board[coordX + k][coordY].setShip(newship);
                            board[coordX + k][coordY].setFree(false);
                            board[coordX + k][coordY].setStatus("Occupied");

                        }
                        else {
                            newship.addCoordinate(coordX,coordY + k);
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
                hits++;
                totalhits++;
                board[coordX][coordY].getmShip().addX(coordX);
                board[coordX][coordY].getmShip().addY(coordY);
                if (board[coordX][coordY].getmShip().isSunk()) {//last hit on the ship== ship sunk
                    mShipLeft--;
                    board[coordX][coordY].setStatus("Sunk");
                    hits-=board[coordX][coordY].getmShip().getSize();
                    int x,y;
                    for (int i = 0; i < board[coordX][coordY].getmShip().getSize(); i++) {
                        x=board[coordX][coordY].getmShip().getX(i);
                        y=board[coordX][coordY].getmShip().getY(i);
                        board[x][y].setStatus("Sunk");

                    }
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