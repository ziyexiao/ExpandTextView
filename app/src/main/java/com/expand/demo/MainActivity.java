package com.expand.demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

import com.expand.demo.expandtextview.ExpandTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandTextView etvMain = findViewById(R.id.etv_main);

        etvMain.setText(getString(R.string.desc))
                .setTextColor(Color.GRAY)       //内容文本颜色
                .setTextSize(12)        //文本字体大小
                .setAnimationDuration(200)  //动画执行时长
                .setBtnExpandText("点击收起")   //折叠文字
                .setBtnSpreadText("点击展开")       //展开文字
                .setBtnGravity(Gravity.END)     //按钮位置
                .setBtnTextColor(Color.BLACK)     //按钮文本颜色
                .setBtnTextSize(15)            //按钮文本大小
                .setShowIcon(true);     //显示箭头
    }
}
