package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridView;


import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.UI.Adapters.PlayerBoardAdapter;

public class PlayerBoardView extends GridView {

    private Game mGame;

    public PlayerBoardView(Context context) {
        super(context);
        this.setBackgroundColor(0xAAAAAA00);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void init(Game game){
        mGame = game;
        this.setAdapter(new PlayerBoardAdapter(getContext(), mGame));
        this.setNumColumns((int) Math.sqrt(mGame.getBoardSize()));

    }
}
