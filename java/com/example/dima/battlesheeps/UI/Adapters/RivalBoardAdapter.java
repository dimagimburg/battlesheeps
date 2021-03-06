package com.example.dima.battlesheeps.UI.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Activities.GameActivity;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.example.dima.battlesheeps.UI.Utils.Utils;
import com.example.dima.battlesheeps.UI.Views.PlayerTileView;
import com.example.dima.battlesheeps.UI.Views.RivalTileView;

import java.io.Serializable;
import java.util.HashMap;

public class RivalBoardAdapter extends BaseAdapter implements Serializable {

    private Context mContext;

    private Game mGame;

    public RivalBoardAdapter(Context ctx, Game game){
        this.mContext = ctx;
        mGame = game;
    }

    @Override
    public int getCount() {
        return (mGame.getBoardSize() * mGame.getBoardSize());
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RivalTileView tileView;

        HashMap<String, Integer> coordinate = Utils.getCoordinateByPosition(position, (int) Math.sqrt(getCount()));
        String tileStatus = mGame.getComputerTileStatus(coordinate.get("x"),coordinate.get("y"));

        if (convertView == null) {
            tileView = new RivalTileView(mContext);
        } else {
            tileView = (RivalTileView) convertView;
        }


        if(tileStatus.equals("Free") || tileStatus.equals("Occupied")){
            tileView.setBackgroundResource(R.drawable.field);
        } else {
            switch (tileStatus) {
                case "Hit":
                    tileView.setBackgroundResource(R.drawable.hit);
                    ((AnimationDrawable) tileView.getBackground()).start();
                    break;
                case "Miss":
                    tileView.setBackgroundResource(R.drawable.miss);
                    ((AnimationDrawable) tileView.getBackground()).start();
                    break;
                case "Sunk":
                    tileView.setBackgroundResource(R.drawable.sunk);
                    ((AnimationDrawable) tileView.getBackground()).start();
                    break;
            }
        }

        tileView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ((parent.getHeight() - (2 * Constants.RIVAL_BOARD_PADDING))  / Math.sqrt(getCount()))));
        return tileView;
    }



}
