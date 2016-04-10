package com.example.dima.battlesheeps.BL;
//package battleship;
//import android.util.Log;

import com.example.dima.battlesheeps.MVCListeners.GameEventListener;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class Game implements Serializable {
    public Board mPlayerBoard;
    public Board mComputerBoard;
    private Player mPlayer = new Player("Human");
    private int[] mLastHit=new int[2];
    private int[] mLastFirstHit=new int[2];
    private boolean isLastTurnHit=false;
    private int[] direction={0,0,0,0}; //right,down,left,up
    private Difficulty mDifficulty; // Normal default
    final int[] mBoardSize={8,10,12};
    private boolean isComputerWon=false;
    private int coordX;
    private int coordY;
    private String status;
    Random r = new Random();
    private Vector<GameEventListener> mListeners = new Vector<>();

    public Game(int difficulty){
        this.mDifficulty=new Difficulty(difficulty);
        this.mPlayerBoard = new Board(this.mDifficulty.getBoardSize(),mDifficulty.mDefaultShipNumber);
        this.mComputerBoard = new Board(this.mDifficulty.getBoardSize(),mDifficulty.mDefaultShipNumber);
        mPlayer.setGameStatus(false);
    }

    public int getBoardSize() {
        return mPlayerBoard.getSize();
    }

    public String playersPlay(int coordX,int coordY) {
        String status=mComputerBoard.Play(coordX, coordY);
        if (status.equals("Win")){
            mPlayer.addWin();
            mPlayer.setGameStatus(true);
        }
        return status;
    }
    public String getPlayerTileStatus(int coordX,int coordY) {
        return mPlayerBoard.getTileStatus(coordX, coordY);
    }
    public String getComputerTileStatus(int coordX,int coordY) {
        return mComputerBoard.getTileStatus(coordX, coordY);
    }
    public boolean isGameOver(){
        if (mPlayer.isWinner())
            return mPlayer.isWinner();
        else
            return isComputerWon;
    }
    public boolean isPlayerWinner(){
        return mPlayer.isWinner();
    }

    public boolean isComputerWinner(){
        return isComputerWon;
    }

    public int[] PlayerShipsLeft(){
        return ShipsLeft(mPlayerBoard);
    }

    public int[] ComputerShipsLeft(){
        return ShipsLeft(mComputerBoard);
    }

    private int[] ShipsLeft(Board board){
        int[] numOfShips = {0,0,0,0};
        for (Ship ship : board.getShips()) {
            if(!ship.isSunk()){
                if(ship.getSize()==1)
                    numOfShips[0]++;
                else if(ship.getSize()==2)
                    numOfShips[1]++;
                else if(ship.getSize()==3)
                    numOfShips[2]++;
                else
                    numOfShips[3]++;
            }
        }
        return numOfShips;
    }

    public int getNumberOfRivalSheeps(){
        int count = 0;
        for(int i = 0; i < mComputerBoard.getSize() * mComputerBoard.getSize(); i++){
            if(!mComputerBoard.getTileStatus((int) (i / mComputerBoard.getSize()), i % mComputerBoard.getSize()).equals("Free")){
                count++;
            }
        }
        return count;
    }


    public String computerPlay() {
        coordX = r.nextInt(mPlayerBoard.getSize()); //random x coordinate
        coordY = r.nextInt(mPlayerBoard.getSize()); //random y coordinate
        if(!isLastTurnHit && direction[0]==0 && direction[1]==0 && direction[2]==0 && direction[3]==0)//random play
            status=mPlayerBoard.Play(coordX,coordY);
        else if(isLastTurnHit)//last turn was hit
            if(direction[0]==1 && mLastHit[0]+1<mPlayerBoard.getSize()-1){
                status=mPlayerBoard.Play(mLastHit[0]+1,mLastHit[1]);
                if (status.equals("Hit"))
                    mLastHit[0]++;
            }
            else if(direction[1]==1 && mLastHit[1]<mPlayerBoard.getSize()-1){
                status=mPlayerBoard.Play(mLastHit[0],mLastHit[1]+1);
                if (status.equals("Hit"))
                    mLastHit[1]++;
            }
            else if(direction[2]==1 && mLastHit[0]>0){
                status=mPlayerBoard.Play(mLastHit[0]-1,mLastHit[1]);
                if (status.equals("Hit"))
                    mLastHit[0]--;
            }
            else if(direction[3]==1 && mLastHit[1]>0){
                status=mPlayerBoard.Play(mLastHit[0],mLastHit[1]-1);
                if (status.equals("Hit"))
                    mLastHit[1]--;
            }
            else{
                status=mPlayerBoard.Play(coordX,coordY);
            }
        while (status.equals("fired")){
            coordX = r.nextInt(mPlayerBoard.getSize()); //random x coordinate
            coordY = r.nextInt(mPlayerBoard.getSize()); //random y coordinate
            status=mPlayerBoard.Play(coordX,coordY);
        }
        if (status.equals("Win")){
            mPlayer.addLoss();
            isComputerWon=false;
        }
        if (status.equals("Hit")&& direction[0]==0 && direction[1]==0 && direction[2]==0 && direction[3]==0){
            mLastFirstHit[0]=coordX;
            mLastFirstHit[1]=coordY;
            mLastHit[0]=coordX;
            mLastHit[1]=coordY;
        }
        if (status.equals("Hit")){
            //mLastHit[0]=coordX;
            //mLastHit[1]=coordY;
            isLastTurnHit=true;
            direction[0]=1;
            direction[1]=1;
            direction[2]=1;
            direction[3]=1;
        }


        if (status.equals("Miss") && (direction[0]==1 || direction[1]==1 || direction[2]==1 || direction[3]==1)){
            if(direction[0]==1){
                direction[0]=0;
                mLastHit[0]=mLastFirstHit[0];
                mLastHit[1]=mLastFirstHit[1];
            }
            else if(direction[1]==1){
                direction[1]=0;
                mLastHit[0]=mLastFirstHit[0];
                mLastHit[1]=mLastFirstHit[1];
            }
            else if(direction[2]==1){
                direction[2]=0;
                mLastHit[0]=mLastFirstHit[0];
                mLastHit[1]=mLastFirstHit[1];
            }
            else if(direction[3]==1){
                direction[3]=0;
                mLastHit[0]=mLastFirstHit[0];
                mLastHit[1]=mLastFirstHit[1];
                isLastTurnHit=false;
            }
        }
        if (status.equals("Sunk")){
            isLastTurnHit=false;
            direction[0]=0;
            direction[1]=0;
            direction[2]=0;
            direction[3]=0;

        }
        return status;

        /*Random r = new Random();
        int coordX;
        int coordY;
        String status;
        coordX = r.nextInt(mPlayerBoard.getSize()); //random x coordinate
            coordY = r.nextInt(mPlayerBoard.getSize()); //random y coordinate
        status=mPlayerBoard.Play(coordX,coordY);
        while (status.equals("fired")){
            coordX = r.nextInt(mPlayerBoard.getSize()); //random x coordinate
            coordY = r.nextInt(mPlayerBoard.getSize()); //random y coordinate
            status=mPlayerBoard.Play(coordX,coordY);
        }
        if (status.equals("Win")){
            mPlayer.addLoss();
            isComputerWon=false;
        }
        return status;*/
        /*Random r = new Random();
        int coordX;
        int coordY;
        String status=null;
        //if(mLastHit==null) {
        if(!isLastTurnHit){
            coordX = r.nextInt(mPlayerBoard.getSize()); //random x coordinate
            coordY = r.nextInt(mPlayerBoard.getSize()); //random y coordinate
        }
        else{

            if(direction[0]==1&&mLastHit[0]<mPlayerBoard.getSize()){
                System.out.println(direction[0]);
                coordX=mLastHit[0]+1;
                coordY=mLastHit[1];
            }
            else if(direction[1]==1&&mLastHit[1]<mPlayerBoard.getSize()){
                System.out.println(direction[1]);
                coordX=mLastHit[0];
                coordY=mLastHit[1]+1;
            }
            else if(direction[2]==1&&mLastHit[0]>0){
                System.out.println(direction[2]);
                coordX=mLastHit[0]-1;
                coordY=mLastHit[1];
            }
            else if(direction[3]==1&&mLastHit[1]>0){
                System.out.println(direction[3]);
                coordX=mLastHit[0];
                coordY=mLastHit[1]-1;
            }
            else{
                coordX = r.nextInt(mPlayerBoard.getSize()); //random x coordinate
                coordY = r.nextInt(mPlayerBoard.getSize()); //random y coordinate
                    }
        }
            status=mPlayerBoard.Play(coordX, coordY);
            if (status.equals("Hit")) {
                if(!isLastTurnHit){//new hit
                mLastHit[0] = coordX;
                mLastHit[1] = coordY;
                for (int i = 0; i < direction.length; i++) {
                    direction[i]=1;
                }
                }
                else{ //more than one hit in the row
                    mLastHit[0] = coordX;
                    mLastHit[1] = coordY;
                    try {
                        if(direction[0]==1){//continie the frivious direction
                        direction[1]=0;
                        direction[2]=0;
                        direction[3]=0;
                    }
                    else if(direction[1]==1){
                        direction[2]=0;
                        direction[3]=0;
                    }
                    else if(direction[2]==1){
                        direction[3]=0;
                    }
                    else
                        direction[3]=0;
                    } catch (Exception e) {
                        direction=new int[4];
                        direction[0]=0;
                        direction[1]=0;
                        direction[2]=0;
                        direction[3]=0;
                    }
                }
                isLastTurnHit=true;
            }
            if (status.equals("fired")){
                computerPlay();
            }
            if (status.equals("Miss")) {
                if (isLastTurnHit){
                    if(direction[0]==1)
                        direction[0]=0;
                    else if(direction[1]==1)
                        direction[1]=0;
                    else if(direction[2]==1)
                        direction[2]=0;
                    else
                        direction[3]=0;
                }
                isLastTurnHit=false;
                mLastHit=new int[2];
            }
            if (status.equals("Sunk")){
                for (int i = 0; i < direction.length; i++) {
                    direction[i]=0;
                }
            }
            return status;
        }*/
    }

    public Difficulty getDifficulty() {
        return mDifficulty;
    }


    public void registerListener(GameEventListener gel){
        mListeners.add(gel);
    }

}
enum status {
    Hidden, Hit, Miss
}

enum  shipName{
    Private, Corpral, Sergant
}
