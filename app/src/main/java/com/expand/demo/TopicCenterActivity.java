package com.expand.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by 43844 on 2019/8/8.
 */

public class TopicCenterActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_center);

        String desc = getIntent().getStringExtra("desc");

        TextView tvTopicCenter = findViewById(R.id.tv_topic_center);
        tvTopicCenter.setText(desc);
    }
}
