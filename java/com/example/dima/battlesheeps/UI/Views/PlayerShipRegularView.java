package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class PlayerShipRegularView extends View {
    public PlayerShipRegularView(Context context) {
        super(context);
        //this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.BLACK);
    }

    public PlayerShipRegularView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
