package com.example.dima.battlesheeps.BL;
//package battleship;
//import android.util.Log;

import com.example.dima.battlesheeps.MVCListeners.GameEventListener;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class Game implements Serializable {
    private final String TAG = "BL.Game";
    public Board mPlayerBoard;
    public Board mComputerBoard;
    private Player mPlayer = new Player("Human");
    private int[] mLastHit=new int[2];
    private boolean isLastTurnHit=false;
    private int[] direction=new int[2];; //right,down,left,up
    private Difficulty mDifficulty; // Normal default
    final int[] mBoardSize={8,10,12};
    private boolean isComputerWon=false;
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

    public boolean getPlayerIsFreeByCoordinate(int x, int y){
        return mPlayerBoard.board[x][y].isFree();
    }

    public boolean getRivalIsFreeByCoordinate(int x, int y){
        return mComputerBoard.board[x][y].isFree();
    }

    
    public String computerPlay() {
        Random r = new Random();
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
        return status;
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
