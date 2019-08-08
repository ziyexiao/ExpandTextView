package com.expand.demo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.expand.demo.expandtextview.ExpandTextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String desc = "这是一个可折叠的TextView,有仿微博的<a href='{type:0, desc:\"个人主页\"}'>@用户</a>和<a href='{type:1,desc:\"话题主页\"}'>#热门话题#</a>功能具体配置需要查看自定义属性.用一季幽香,温暖了多少惆怅,让心事穿越时光,谱一曲爱的畅想. 今生不离携手翱翔,红尘有你伴潇湘,千千深情藏, 桃源十里正芳香, 映红汝美妆, 轻舞痴狂, 用三世的美景染软红十丈,做你的衣裳,一澜相思溢心房,夜未央,独凭栏处思绪荡,一帘明月照东墙,几重伤,谁把誓言忘,留我独自徬徨,红尘渡口谁在望,犹记前程渺茫,花落纷纷扬,拈来注酒尝,微醺愁肠,卷帘玎珰,惊了谁的慌,步踉跄,斜坐几案旁,又见红烛泪淌,挥毫赋诗又凄凉,红笺之上小字躺,怎续凤求凰,故人去远方,鸳鸯不成双,心语呢喃伥,累字自己扛,缘分的山岗,印记着你的模样,相思酿,你背起行囊,远走他乡,从此无信航,负情东流怎丈量,谁解开爱的绳缰,是我来不及提防,却被你埋葬,荡不起缘分的桨,望断天涯彼岸夯,不肯喝孟婆的汤,来生化作佛前琳琅,祥瑞降,一生不弃续缘长";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandTextView etvMain = findViewById(R.id.etv_main);

        etvMain.setText(desc)
                .setExpandEnable(true)  //是否可折叠, 默认为true
                .setTextColor(Color.GRAY)       //内容文本颜色
                .setTextSize(14)        //文本字体大小
                .setAnimationDuration(500)  //动画执行时长
                .setBtnExpandText("点击收起")   //折叠文字
                .setBtnSpreadText("点击展开")       //展开文字
                .setBtnGravity(Gravity.END)     //按钮位置
                .setBtnTextColor(Color.BLACK)     //按钮文本颜色
                .setBtnTextSize(18)            //按钮文本大小
                .setShowIcon(true)  //显示箭头
                .setSpanClickable(true, new ExpandTextView.TextSpanClickListener() {  //设置有标签或@某人的点击, 默认为false
                    @Override
                    public void onTextSpanClick(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int type = jsonObject.getInt("type");
                            String desc = jsonObject.getString("desc");


                            Intent intent = new Intent();
                            switch (type) {
                                case 0:      //个人主页
                                    intent.setClass(MainActivity.this, UserCenterActivity.class);
                                    break;
                                case 1:      //话题主页
                                    intent.setClass(MainActivity.this, TopicCenterActivity.class);
                                    break;
                            }
                            intent.putExtra("desc", desc);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
