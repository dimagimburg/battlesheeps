package com.example.dima.battlesheeps.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/* TODO: FROM FELIX */
import com.example.dima.battlesheeps.BL.Board;

public class BoardAdapter extends BaseAdapter {

    private Context mContext;

    /* TODO: FROM FELIX */
    private Board board = new Board();

    public BoardAdapter(Context ctx){
        this.mContext = ctx;
    }

    @Override
    public int getCount() {
        return board.getBoard().length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            //textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //textView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }

        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight()/((int) Math.sqrt(getCount()))));

        textView.setText("a");
        return textView;
    }
}
