package com.example.dima.battlesheeps.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/* TODO: FROM FELIX */
import com.example.dima.battlesheeps.BL.Board;
import com.example.dima.battlesheeps.BL.Game;

public class PlayerBoardAdapter extends BaseAdapter {

    private Context mContext;

    private Game mGame;

    public PlayerBoardAdapter(Context ctx, Game game){
        this.mContext = ctx;
        mGame = game;
    }

    @Override
    public int getCount() {
        return mGame.getBoardSize();
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

        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight()/((int) Math.sqrt(getCount()))));

        textView.setText("p");
        return textView;
    }
}
