package com.example.dima.battlesheeps.UI.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Views.BoardView;

public class PlayerContainerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.player_container, container, false);
        RelativeLayout playerContainer = (RelativeLayout) v.findViewById(R.id.playerContainer);
        BoardView board = new BoardView(getActivity());
        playerContainer.addView(board);

        return v;
    }
}
