package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.UI.Activities.GameActivity;
import com.example.dima.battlesheeps.UI.Adapters.RivalBoardAdapter;
import com.example.dima.battlesheeps.UI.UIListeners.GameActivityRivalFieldListener;

import java.util.HashMap;
import java.util.Vector;

import static com.example.dima.battlesheeps.UI.Utils.Utils.getCoordinateByPosition;

public class RivalFieldBoardView extends GridView implements GameActivityRivalFieldListener {

    private final String TAG = "RivalFieldBoardView";

    private Game mGame;
    private GameActivity mContext;
    RivalBoardAdapter adapter;

    public RivalFieldBoardView(Context context) {
        super(context);
        mContext = (GameActivity) context;
        mContext.registerGameActivityRivalEventListener(this);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setClickListeners(this);
    }

    public void init(Game game){
        mGame = game;
        adapter = new RivalBoardAdapter(getContext(), mGame);
        this.setAdapter(adapter);
        this.setNumColumns(mGame.getBoardSize());
    }

    private void setClickListeners(GridView gv){
        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                HashMap<String, Integer> coordinates = getCoordinateByPosition(position, mGame.getBoardSize());
                mContext.playerPlays(coordinates.get("x"), coordinates.get("y"));
            }
        });
    }

    @Override
    public void onPlayerPlayed(int x, int y, String status, boolean isGameOver, boolean isPlayerWon, boolean isRivalWon) {
        adapter.notifyDataSetChanged();
    }
}
