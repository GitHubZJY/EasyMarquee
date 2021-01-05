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

    private EasyMarqueeView easyMarqueeView1, easyMarqueeView2, easyMarqueeView3, easyMarqueeView4;
    private MarqueeAdapter<String> adapter1 = new CustomMarqueeAdapter(this);
    private MarqueeAdapter<String> adapter2 = new CustomMarqueeAdapter(this);
    private MarqueeAdapter<String> adapter3 = new CustomMarqueeAdapter(this);
    private MarqueeAdapter<String> adapter4 = new CustomMarqueeAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyMarqueeView1 = findViewById(R.id.marquee_view_1);
        easyMarqueeView2 = findViewById(R.id.marquee_view_2);
        easyMarqueeView3 = findViewById(R.id.marquee_view_3);
        easyMarqueeView4 = findViewById(R.id.marquee_view_4);
        initData(easyMarqueeView1, 1);
        initData(easyMarqueeView2, 2);
        initData(easyMarqueeView3, 3);
        initData(easyMarqueeView4, 4);
    }

    private void initData(EasyMarqueeView view, int position) {
        MarqueeAdapter adapter = null;

        switch (position) {
            case 1:
                adapter = adapter1;
                break;
            case 2:
                adapter = adapter2;
                break;
            case 3:
                adapter = adapter3;
                break;
            case 4:
                adapter = adapter4;
                break;
        }
        view.setMarqueeAdapter(adapter);
        view.setMarqueeListener(new MarqueeListener() {
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

//        view.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dataList.add("title5");
//                dataList.add("title6");
//                adapter.setData(dataList);
//            }
//        }, 10000);

        view.startFlip();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        easyMarqueeView1.stopFlip();
        easyMarqueeView2.stopFlip();
        easyMarqueeView3.stopFlip();
        easyMarqueeView4.stopFlip();
    }
}