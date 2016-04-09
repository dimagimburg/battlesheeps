package com.example.dima.battlesheeps.UI.Views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class PlayerFieldRegularView extends View {

    public PlayerFieldRegularView(Context context) {
        super(context);
        //this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.GREEN);
    }

    public PlayerFieldRegularView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
