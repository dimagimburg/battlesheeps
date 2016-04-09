package com.example.dima.battlesheeps.UI.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Utils.Utils;
import com.example.dima.battlesheeps.UI.Views.PlayerTileView;
import com.example.dima.battlesheeps.UI.Views.RivalTileView;

import java.util.HashMap;

public class RivalBoardAdapter extends BaseAdapter {

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
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            tileView = new RivalTileView(mContext);
            //textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //textView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //textView.setPadding(8, 8, 8, 8);
        } else {
            tileView = (RivalTileView) convertView;
        }

        HashMap<String, Integer> coordinate = Utils.getCoordinateByPosition(position, (int) Math.sqrt(getCount()));
        String tileStatus = mGame.getComputerTileStatus(coordinate.get("x"),coordinate.get("y"));

        if(tileStatus.equals("Free") || tileStatus.equals("Occupied")){
            tileView.setImageResource(R.drawable.field);
        } else {
            if(tileStatus.equals("Hit")){
                tileView.setImageResource(R.drawable.rivals_field_hit);
            } else if(tileStatus.equals("Miss")) {
                tileView.setImageResource(R.drawable.rivals_field_miss);
            } else if(tileStatus.equals("Sunk")){
                tileView.setImageResource(R.drawable.rivals_field_sunk);
            }

        }

        tileView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (parent.getHeight() / Math.sqrt(getCount()))));
        return tileView;
    }

}
