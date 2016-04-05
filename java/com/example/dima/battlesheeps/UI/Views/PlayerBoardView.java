package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.UI.Adapters.PlayerBoardAdapter;

import java.util.HashMap;
import java.util.Set;

public class PlayerBoardView extends GridView {

    private Game mGame;

    public PlayerBoardView(Context context) {
        super(context);
        this.setBackgroundColor(0xAAAAAA00);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setClickListeners(this);
    }

    public void init(Game game){
        mGame = game;
        this.setAdapter(new PlayerBoardAdapter(getContext(), mGame));
        this.setNumColumns((int) Math.sqrt(mGame.getBoardSize()));
    }

    private void setClickListeners(GridView gv){
        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                HashMap<String, Integer> coordinates = getCoordinate(position);
                Toast.makeText(getContext(), "chosen tile: " + position + ", [x=" + coordinates.get("x") + "] :: [y=" + coordinates.get("y") + "]", Toast.LENGTH_LONG).show();
                TextView tv = (TextView) v;
                tv.setText("reveal");
            }
        });
    }

    private HashMap<String, Integer> getCoordinate(int position){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("x",(position % (int) Math.sqrt(mGame.getBoardSize())));
        map.put("y",(position / (int) Math.sqrt(mGame.getBoardSize())));
        return map;
    }


}
