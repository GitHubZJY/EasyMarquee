package com.zjy.easymarquee;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zjy.marqueelib.MarqueeAdapter;

public class CustomMarqueeAdapter extends MarqueeAdapter<String> {

    private Context mContext;

    public CustomMarqueeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(int position, String data) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
    }

    @Override
    public void onBindView(final int position, View view, final String data) {
        TextView titleView = view.findViewById(R.id.item_title);
        if (position % 2 == 0) {
            view.setBackgroundColor(Color.LTGRAY);
        } else {
            view.setBackgroundColor(Color.RED);
        }
        titleView.setText(data);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
