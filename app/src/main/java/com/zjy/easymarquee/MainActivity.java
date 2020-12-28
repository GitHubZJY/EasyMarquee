package com.zjy.easymarquee;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zjy.marqueelib.MarqueeAdapter;
import com.zjy.marqueelib.EasyMarqueeView;
import com.zjy.marqueelib.MarqueeListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EasyMarqueeView easyMarqueeView;
    private MarqueeAdapter<String> adapter = new MarqueeAdapter<String>() {
        @Override
        public View onCreateView(int position, String data) {
            return LayoutInflater.from(MainActivity.this).inflate(R.layout.item_layout, null);
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
                    Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyMarqueeView = findViewById(R.id.marquee_view);
        easyMarqueeView.setMarqueeAdapter(adapter);
        easyMarqueeView.setMarqueeListener(new MarqueeListener() {
            @Override
            public void onMarquee(int position) {
                Log.d("onMarquee", "position: " + position);
            }
        });

        final List<String> dataList = new ArrayList<>();
        dataList.add("title1");
        dataList.add("title2");
        dataList.add("title3");
        dataList.add("title4");
        adapter.setData(dataList);

        easyMarqueeView.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataList.add("title5");
                dataList.add("title6");
                adapter.setData(dataList);
            }
        }, 10000);

        easyMarqueeView.startFlip();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        easyMarqueeView.stopFlip();
    }
}