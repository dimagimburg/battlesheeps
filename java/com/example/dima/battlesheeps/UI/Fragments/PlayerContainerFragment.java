package com.example.dima.battlesheeps.UI.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dima.battlesheeps.R;
import com.example.dima.battlesheeps.UI.Views.BoardView;

public class PlayerContainerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_container, container, false);
    }
}
