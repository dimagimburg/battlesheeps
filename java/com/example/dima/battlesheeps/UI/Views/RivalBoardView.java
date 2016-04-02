package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.UI.Adapters.PlayerBoardAdapter;
import com.example.dima.battlesheeps.UI.Adapters.RivalBoardAdapter;

public class RivalBoardView extends GridView {

    private Game mGame;

    public RivalBoardView(Context context) {
        super(context);
        this.setBackgroundColor(0xAABBAA00);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void init(Game game){
        mGame = game;
        this.setAdapter(new RivalBoardAdapter(getContext(), mGame));
        this.setNumColumns((int) Math.sqrt(mGame.getBoardSize()));

    }
}
