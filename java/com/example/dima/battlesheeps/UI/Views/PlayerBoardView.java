package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
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

import static com.example.dima.battlesheeps.UI.Utils.Utils.getCoordinateByPosition;

public class PlayerBoardView extends GridView {

    private Game mGame;

    public PlayerBoardView(Context context) {
        super(context);
        this.setBackgroundColor(0xBBBAAA00);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void init(Game game){
        mGame = game;
        this.setAdapter(new PlayerBoardAdapter(getContext(), mGame));
        this.setNumColumns(mGame.getBoardSize());
        this.setGravity(Gravity.CENTER_VERTICAL);
    }

}
