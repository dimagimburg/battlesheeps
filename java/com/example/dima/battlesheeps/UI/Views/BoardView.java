package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.GridView;


import com.example.dima.battlesheeps.UI.Adapters.BoardAdapter;

public class BoardView extends GridView {
    public BoardView(Context context) {
        super(context);
        this.setBackgroundColor(0xAAAAAA00);
        this.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setAdapter(new BoardAdapter(context));
        this.setNumColumns((int) Math.sqrt(64));
    }
}
