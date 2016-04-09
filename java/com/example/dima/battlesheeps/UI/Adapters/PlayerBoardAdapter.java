package com.example.dima.battlesheeps.UI.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Utils.Utils;
import com.example.dima.battlesheeps.UI.Views.PlayerTileView;
import com.example.dima.battlesheeps.UI.Views.RivalTileView;

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
        PlayerTileView tileView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            tileView = new PlayerTileView(mContext);
            //textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //textView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //textView.setPadding(8, 8, 8, 8);
        } else {
            tileView = (PlayerTileView) convertView;
        }

        HashMap<String, Integer> coordinate = Utils.getCoordinateByPosition(position, (int) Math.sqrt(getCount()));
        String tileStatus = mGame.getPlayerTileStatus(coordinate.get("x"), coordinate.get("y"));

        if(tileStatus.equals("Free") || tileStatus.equals("Occupied")){
            tileView.setImageResource(R.drawable.field);
        } else {
            switch (tileStatus) {
                case "Hit":
                    tileView.setImageResource(R.drawable.players_field_hit);
                    break;
                case "Miss":
                    tileView.setImageResource(R.drawable.players_field_miss);
                    break;
                case "Sunk":
                    tileView.setImageResource(R.drawable.players_field_sunk);
                    break;
            }
        }

        tileView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (parent.getHeight() / Math.sqrt(getCount()))));
        return tileView;
    }
}
