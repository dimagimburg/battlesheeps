package com.example.dima.battlesheeps.BL;
//package battleship;
//import android.util.Log;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.dima.battlesheeps.MVCListeners.GameEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Game implements Parcelable {
    public Board mPlayerBoard;
    public Board mComputerBoard;
    public Player mPlayer = new Player("Human");
    private int[] mLastHit=new int[2];
    private int[] mLastFirstHit=new int[2];
    private boolean isLastTurnHit=false;
    private int[] direction={0,0,0,0}; //right,down,left,up
    public Difficulty mDifficulty; // Normal default
    int[] mBoardSize={8,10,12};
    private boolean isComputerWon=false;
    private int coordX;
    private int coordY;
    private String status;
    Random r = new Random();
    Context context = null;
    public DBHelper dbhelper;
    private Vector<GameEventListener> mListeners = new Vector<>();

    public Game(int difficulty,DBHelper dbhelper){
        this.dbhelper=dbhelper;
        this.mDifficulty=new Difficulty(difficulty);
        this.mPlayerBoard = new Board(this.mDifficulty.getBoardSize(),mDifficulty.mDefaultShipNumber);
        this.mComputerBoard = new Board(this.mDifficulty.getBoardSize(),mDifficulty.mDefaultShipNumber);
        mPlayer.setGameStatus(false);
    }

    protected Game(Parcel in) {
        mLastHit = in.createIntArray();
        mLastFirstHit = in.createIntArray();
        isLastTurnHit = in.readByte() != 0;
        direction = in.createIntArray();
        mBoardSize = in.createIntArray();
        isComputerWon = in.readByte() != 0;
        coordX = in.readInt();
        coordY = in.readInt();
        status = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public boolean isHighScore() {
        return dbhelper.isHighScore(mDifficulty.getDifficulty(),mPlayer);
    }

    public void addPlayer() {
        dbhelper.addPlayer(mDifficulty.getDifficulty(),mPlayer);
    }

    public void deletelastPlayer() {
        dbhelper.deletelastPlayer(mDifficulty.getDifficulty(),mPlayer);
    }

    public ArrayList<String> getScoresTable() {
        return dbhelper.getScoresTable(mDifficulty.getDifficulty());
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
        mPlayer.addTry();
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

    }

    public Difficulty getDifficulty() {
        return mDifficulty;
    }


    public void registerListener(GameEventListener gel){
        mListeners.add(gel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(mLastHit);
        dest.writeIntArray(mLastFirstHit);
        dest.writeByte((byte) (isLastTurnHit ? 1 : 0));
        dest.writeIntArray(direction);
        dest.writeIntArray(mBoardSize);
        dest.writeByte((byte) (isComputerWon ? 1 : 0));
        dest.writeInt(coordX);
        dest.writeInt(coordY);
        dest.writeString(status);
    }
}
enum status {
    Hidden, Hit, Miss
}

enum  shipName{
    Private, Corpral, Sergant
}
