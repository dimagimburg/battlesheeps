package com.example.dima.battlesheeps.UI.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Views.PlayerBoardView;
import com.example.dima.battlesheeps.UI.Views.RivalBoardView;

public class RivalContainerFragment extends Fragment {

    public RivalContainerFragment(){}

    Game mGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rival_field_container, container, false);
        RelativeLayout playerContainer = (RelativeLayout) v.findViewById(R.id.rivalFieldContainer);
        Bundle args = getArguments();
        mGame = (Game) args.getSerializable("game");
        RivalBoardView boardView = new RivalBoardView(getActivity());
        boardView.init(mGame);
        playerContainer.addView(boardView);
        return v;
    }

}
