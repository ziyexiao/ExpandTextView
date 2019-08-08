package com.xx.expandtextview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by 43844 on 2019/8/8.
 */

public class UserCenterActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        String desc = getIntent().getStringExtra("desc");



        TextView tvUserCenter = findViewById(R.id.tv_user_center);
        tvUserCenter.setText(desc);

    }
}
