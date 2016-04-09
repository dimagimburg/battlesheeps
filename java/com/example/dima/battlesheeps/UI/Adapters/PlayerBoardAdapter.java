package com.example.dima.battlesheeps.UI.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.UI.Views.PlayerTileView;

import java.util.HashMap;

import static com.example.dima.battlesheeps.UI.Utils.Utils.getCoordinateByPosition;

public class PlayerBoardAdapter extends BaseAdapter {

    private Context mContext;

    private Game mGame;

    public PlayerBoardAdapter(Context ctx, Game game){
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
        HashMap<String, Integer> coordinates = getCoordinateByPosition(position, mGame.getBoardSize());
        PlayerTileView tileView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            if(mGame.getPlayerIsFreeByCoordinate(coordinates.get("x"), coordinates.get("y"))){
                tileView = new PlayerTileView(mContext);
            } else {
                tileView = new PlayerTileView(mContext);
            }
            //textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //textView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //textView.setPadding(8, 8, 8, 8);
        } else {
            if(mGame.getPlayerIsFreeByCoordinate(coordinates.get("x"), coordinates.get("y"))){
                tileView = (PlayerTileView) convertView;
            } else {
                tileView = (PlayerTileView) convertView;
            }
        }

        tileView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() / mGame.getBoardSize()));

        //textView.setText(mGame.getRivalIsFreeByCoordinate(coordinates.get("x"), coordinates.get("y")) ? "o" : "x");
        //textView.setTextSize(10);
        return tileView;
    }
}
