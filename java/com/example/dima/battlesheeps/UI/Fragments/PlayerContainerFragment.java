package com.example.dima.battlesheeps.UI.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Views.PlayerFieldBoardView;


public class PlayerContainerFragment extends Fragment {

    private Game mGame;

    public PlayerContainerFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.player_field_container, container, false);
        RelativeLayout playerContainer = (RelativeLayout) v.findViewById(R.id.playerFieldContainer);
        Bundle args = getArguments();
        mGame = (Game) args.getSerializable("game");
        PlayerFieldBoardView boardView = new PlayerFieldBoardView(getActivity());
        boardView.init(mGame);
        playerContainer.addView(boardView);
        return v;
    }

}
