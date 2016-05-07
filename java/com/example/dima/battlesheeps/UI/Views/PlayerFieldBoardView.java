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
import com.example.dima.battlesheeps.UI.Activities.GameActivity;
import com.example.dima.battlesheeps.UI.Adapters.PlayerBoardAdapter;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.example.dima.battlesheeps.UI.UIListeners.GameActivityPlayerFieldListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import static com.example.dima.battlesheeps.UI.Utils.Utils.getCoordinateByPosition;

public class PlayerFieldBoardView extends GridView implements GameActivityPlayerFieldListener, Serializable {

    private Game mGame;
    private GameActivity mContext;
    private PlayerBoardAdapter mAdapter;

    public PlayerFieldBoardView(Context context) {
        super(context);
        mContext = (GameActivity) context;
        mContext.registerGameActivityPlayerEventListener(this);
        this.setPadding(Constants.PLAYER_BOARD_PADDING, Constants.PLAYER_BOARD_PADDING, Constants.PLAYER_BOARD_PADDING, Constants.PLAYER_BOARD_PADDING);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void init(Game game){
        mGame = game;
        mAdapter = new PlayerBoardAdapter(getContext(), mGame);
        this.setAdapter(mAdapter);
        this.setNumColumns(mGame.getBoardSize());
        this.setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    public void onRivalPlayed(String status, boolean isGameOver, boolean isPlayerWon, boolean isRivalWon) {
        mAdapter.notifyDataSetChanged();
        if(isGameOver && isRivalWon){
            mContext.rivalWon();
        }
    }
}
