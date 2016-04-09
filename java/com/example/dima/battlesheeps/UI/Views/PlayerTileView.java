package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.example.dima.battlesheeps.R;

public class PlayerTileView extends ImageView {
    public PlayerTileView(Context context) {
        super(context);
        this.setImageResource(R.drawable.field);
        this.setScaleType(ScaleType.FIT_XY);
        this.setBackgroundColor(Color.WHITE);
        this.setPadding(1, 1, 1, 1);
    }
}
