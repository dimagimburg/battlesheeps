package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Activities.GameActivity;
import com.example.dima.battlesheeps.UI.Adapters.RivalBoardAdapter;
import com.example.dima.battlesheeps.UI.Constants.Constants;
import com.example.dima.battlesheeps.UI.UIListeners.GameActivityRivalFieldListener;
import com.example.dima.battlesheeps.UI.Utils.Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import static com.example.dima.battlesheeps.UI.Utils.Utils.getCoordinateByPosition;

public class RivalFieldBoardView extends GridView implements GameActivityRivalFieldListener, Serializable {

    private final String TAG = "RivalFieldBoardView";

    private Game mGame;
    private GameActivity mContext;
    RivalBoardAdapter adapter;
    private int positionX;
    private int positionY;

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

        gv.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                positionX = (int) event.getX();
                positionY = (int) event.getY();
                return false;
            }
        });

        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final HashMap<String, Integer> coordinates = getCoordinateByPosition(position, mGame.getBoardSize());
                String tileStatus = mGame.getComputerTileStatus(coordinates.get("x"), coordinates.get("y"));
                if (!(tileStatus.equals("Hit") || tileStatus.equals("Miss") || tileStatus.equals("Sunk"))) {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mContext.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    applyMissleAnimation(positionX, positionY, coordinates.get("x"), coordinates.get("y"));
                                }
                            });
                        }
                    });
                    t.start();
                }
            }
        });
    }

    public void applyMissleAnimation(int x, int y, int playedX, int playedY){
        final int PLAYEDX = playedX;
        final int PLAYEDY = playedY;
        Log.e(TAG, "x=" + x + " | y=" + y);
        RelativeLayout rl = (RelativeLayout) mContext.findViewById(R.id.rivalFieldWrapper);
        final ImageView missle = new ImageView(mContext);
        missle.setId(R.id.missle);
        missle.setImageResource(R.drawable.missile);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        int marginLeft = (int) (Math.random() * getWidth());
        int marginTop = (int) (Math.random() * getHeight());
        layoutParams.setMargins(marginLeft, marginTop, 0, 0);
        missle.setLayoutParams(layoutParams);
        missle.getLayoutParams().height = 150;
        missle.getLayoutParams().width = 150;

        int a = x - marginLeft;
        int b = marginTop - y;
        double angle = Math.atan2(a, b);
        Log.e(TAG, "a=" + a + " :: b=" + b + " :: angle=" + angle + " :: degrees=" + (int) Math.toDegrees(angle));
        missle.setRotation((int) Math.toDegrees(angle));

        rl.addView(missle);


        TranslateAnimation ta = new TranslateAnimation(0, x - marginLeft - 75, 0, y - marginTop - 75);
        int duration = (int) ((Math.random() * 1000) + 200);
        ta.setDuration(duration);
        ta.setStartOffset(500);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                missle.setVisibility(GONE);
                mContext.playerPlays(PLAYEDX, PLAYEDY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        missle.startAnimation(ta);

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
