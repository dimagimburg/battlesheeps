package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Activities.GameActivity;
import com.example.dima.battlesheeps.UI.Adapters.RivalBoardAdapter;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.example.dima.battlesheeps.UI.UIListeners.GameActivityRivalFieldListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import static com.example.dima.battlesheeps.UI.Utils.Utils.getCoordinateByPosition;

public class RivalFieldBoardView extends GridView implements GameActivityRivalFieldListener, Serializable {

    private final String TAG = "RivalFieldBoardView";

    private Game mGame;
    private GameActivity mContext;
    RivalBoardAdapter adapter;

    public RivalFieldBoardView(Context context) {
        super(context);
        mContext = (GameActivity) context;
        mContext.registerGameActivityRivalEventListener(this);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setPadding(Constants.RIVAL_BOARD_PADDING, Constants.RIVAL_BOARD_PADDING, Constants.RIVAL_BOARD_PADDING, Constants.RIVAL_BOARD_PADDING);
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
                String tileStatus = mGame.getComputerTileStatus(coordinates.get("x"), coordinates.get("y"));
                if (!(tileStatus.equals("Hit") || tileStatus.equals("Miss") || tileStatus.equals("Sunk"))) {
                    mContext.playerPlays(coordinates.get("x"), coordinates.get("y"));
                }
            }
        });
    }

    @Override
    public void onPlayerPlayed(int x, int y, String status, boolean isGameOver, boolean isPlayerWon, boolean isRivalWon, int[] shipCount) {
        adapter.notifyDataSetChanged();
        TextView vShipFour = (TextView) mContext.findViewById(R.id.numberOfSheeps_4);
        TextView vShipThree = (TextView) mContext.findViewById(R.id.numberOfSheeps_3);
        TextView vShipTwo = (TextView) mContext.findViewById(R.id.numberOfSheeps_2);
        TextView vShipOne = (TextView) mContext.findViewById(R.id.numberOfSheeps_1);

        vShipFour.setText(shipCount[3] + "");
        vShipThree.setText(shipCount[2] + "");
        vShipTwo.setText(shipCount[1] + "");
        vShipOne.setText(shipCount[0] + "");
        if(isGameOver && isPlayerWon){
            mContext.playerWon();
        }
    }
}
