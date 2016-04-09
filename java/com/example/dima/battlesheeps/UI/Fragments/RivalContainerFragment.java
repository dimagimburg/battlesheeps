package com.example.dima.battlesheeps.UI.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.dima.battlesheeps.BL.Game;
import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Views.RivalFieldBoardView;

public class RivalContainerFragment extends Fragment {

    public RivalContainerFragment(){}

    private Game mGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rival_field_container, container, false);
        RelativeLayout playerContainer = (RelativeLayout) v.findViewById(R.id.rivalFieldContainer);
        Bundle args = getArguments();
        mGame = (Game) args.getSerializable("game");
        RivalFieldBoardView boardView = new RivalFieldBoardView(getActivity());
        boardView.init(mGame);
        playerContainer.addView(boardView);
        return v;
    }

}
