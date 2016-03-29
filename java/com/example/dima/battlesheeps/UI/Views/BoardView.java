package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.example.dima.battlesheeps.BL.Board;

import com.example.dima.battlesheeps.UI.Adapters.BoardAdapter;

public class BoardView extends GridView {

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setAdapter(new BoardAdapter(context));
        this.setNumColumns(64);
    }


}
