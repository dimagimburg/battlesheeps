package com.example.dima.battlesheeps.UI.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.UI.Utils.Utils;

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
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            //textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //textView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }

        HashMap<String, Integer> coordinate = Utils.getCoordinateByPosition(position, (int) Math.sqrt(getCount()));
        String tileStatus = mGame.getComputerTileStatus(coordinate.get("x"),coordinate.get("y"));

        if(tileStatus.equals("Free") || tileStatus.equals("Occupied")){
            textView.setText("h");
        } else {
            if(tileStatus.equals("Hit")){
                textView.setText("hit");
            } else if(tileStatus.equals("Miss")) {
                textView.setText("miss");
            } else if(tileStatus.equals("Sunk")){
                textView.setText("sunk");
            }

        }

        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (parent.getHeight() / Math.sqrt(getCount()))));
        return textView;
    }

}
