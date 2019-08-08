# ExpandTextView
可折叠展开的TextView---ExpandTextView

[![](https://jitpack.io/v/ziyexiao/ExpandTextView.svg)](https://jitpack.io/#ziyexiao/ExpandTextView)

[点击查看gif效果图](https://img-blog.csdnimg.cn/20190808104936409.gif)

## 简单使用
* Step 1. 添加如下代码至project的build.gradle里:

	    allprojects {
	    	repositories {
			...
			maven { url 'https://jitpack.io' }
		    }
	    }
	
* Step 2. 添加依赖

	   dependencies {
       	        implementation 'com.github.ziyexiao:ExpandTextView:1.0.1'
       	}
	    
* step 3.在XML布局文件中添加 RoundProgressBar

            <com.xx.expandtextview.ExpandTextView
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:id="@+id/etv_main" />
                    
* step 4.添加代码

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

## 自定义属性说明

|属性|属性说明|类型|
|:--:|:--:|:--:|
|contentTextColor|内容文本颜色|color|
|contentTextSize|内容文本字体大小|dimension|
|btnTextColor|第一行文本大小|color|
|btnExpandText|按钮折叠的描述|string|
|btnSpreadText|按钮展开的描述|string|
|btnTextSize|按钮字体大小|dimension|
|showLines|最大显示行数|integer|
|showIcon|是否显示箭头图标|boolean|
|iconRes|箭头资源|reference|
|animationDuration|动画时长|integer|
|spanClickable|@或者#话题#是否可点击|boolean|
|expandEnable|是否可折叠|boolean|


**博客地址：** [https://blog.csdn.net/ziyexiaoxiao/article/details/98749110](https://blog.csdn.net/ziyexiaoxiao/article/details/98749110)
