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
    Vector<GameActivityRivalFieldListener> listeners = new Vector<>();

    public RivalFieldBoardView(Context context) {
        super(context);
        mContext = (GameActivity) context;
        mContext.registerGameActivityRivalEventListener(this);
        this.setBackgroundColor(0xAABBAA00);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setClickListeners(this);
    }

    public void init(Game game){
        mGame = game;
        adapter = new RivalBoardAdapter(getContext(), mGame);
        this.setAdapter(adapter);
        this.setNumColumns(mGame.getBoardSize());
    }

    public void registerGameActivityRivalEventListener(GameActivityRivalFieldListener l){
        listeners.add(l);
    }

    private void setClickListeners(GridView gv){
        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.e(TAG, "tile clicked at position " + position);
                HashMap<String, Integer> coordinates = getCoordinateByPosition(position, mGame.getBoardSize());

                mContext.playerPlays(coordinates.get("x"), coordinates.get("y"));

                //mGame.playersPlay(coordinates.get("x"), coordinates.get("y"));

                /*
                HashMap<String, Integer> coordinates = getCoordinateByPosition(position, mGame.getBoardSize());
                String status = mGame.playersPlay(coordinates.get("x"), coordinates.get("y"));
                switch (status) {
                    case "Miss":
                        v.setBackgroundColor(Color.RED);
                        break;
                    case "Hit":
                        v.setBackgroundColor(Color.GREEN);
                        break;
                    case "Sunk":
                        v.setBackgroundColor(Color.YELLOW);
                        break;
                }
                */


                //TextView tv = (TextView) v;
                //tv.setText(status);
            }
        });
    }

    @Override
    public void onPlayerPlayed(int x, int y, String status, boolean isGameOver, boolean isPlayerWon, boolean isRivalWon) {
        adapter.notifyDataSetChanged();
    }
}
