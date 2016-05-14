package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;

import com.example.dima.battlesheeps.R;

public class RivalTileView extends ImageView {

    public RivalTileView(Context context) {
        super(context);
        this.setScaleType(ScaleType.FIT_XY);
        this.setBackgroundColor(Color.WHITE);
        this.setPadding(1, 1, 1, 1);
    }

}
