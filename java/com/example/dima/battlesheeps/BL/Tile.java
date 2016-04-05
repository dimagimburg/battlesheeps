/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.io.Serializable;

public class Tile implements Serializable{
    private Ship mShip;
    private String mstatus;
    private boolean mfree=true;

    public boolean isFree() {
        return mfree;
    }

    public void setFree(boolean isFree) {
        this.mfree = isFree;
    }

    public Ship getmShip() {
        return mShip;
    }

    public void setShip(Ship mShip) {
        this.mShip = mShip;
    }

    public String getStatus() {
        return mstatus;
    }

    public void setStatus(String mstatus) {
        this.mstatus = mstatus;
    }
}
