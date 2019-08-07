package com.expand.demo.expandtextview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 43844 on 2019/8/2.
 *
 */

public class ExpandTextView extends LinearLayout {

    //折叠状态, true为折叠, false为展开
    private boolean expand = true;

    //文本内容
    private String text;
    //文本颜色
    private int textColor;
    //字体大小
    private float textSize;

    //按钮折叠文本内容
    private String btnExpandText;

    //按钮展开内容
    private String btnSpreadText;

    //按钮字体颜色
    private int btnTextColor;
    //折叠后显示的行数
    private int showLines;
    //按钮字体大小
    private float btnTextSize;

    //是否显示箭头图标
    private boolean showIcon;
    //图标icon
    private int iconRes;
    //内容文本
    private TextView mTextView;
    //展开折叠控制按钮
    private TextView mBtnText;
    //展开高度
    private int mTextSpreadHeight;
    //折叠高度
    private int mTextExpandHeight;

    //动画时长
    private long mAnimationDuration;

    //按钮位置
    private int btnGravity = Gravity.CENTER;

    private ImageView mIvArrow;

    private LinearLayout mLlBtn;

    public ExpandTextView(Context context) {
        this(context, null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView);
        textColor = typedArray.getColor(R.styleable.ExpandTextView_contentTextColor, context.getResources().getColor(R.color.six_three));
        textSize = typedArray.getDimension(R.styleable.ExpandTextView_contentTextSize, 15);

        btnExpandText = typedArray.getString(R.styleable.ExpandTextView_btnExpandText);
        btnSpreadText = typedArray.getString(R.styleable.ExpandTextView_btnSpreadText);
        btnTextSize = typedArray.getDimension(R.styleable.ExpandTextView_btnTextSize, 18);
        btnTextColor = typedArray.getColor(R.styleable.ExpandTextView_btnTextColor, context.getResources().getColor(R.color.six_three));

        showLines = typedArray.getColor(R.styleable.ExpandTextView_showLines, 3);

        showIcon = typedArray.getBoolean(R.styleable.ExpandTextView_showIcon, true);

        iconRes = typedArray.getInt(R.styleable.ExpandTextView_iconRes, R.drawable.ic_arrow_down_gray);

        mAnimationDuration = typedArray.getInt(R.styleable.ExpandTextView_animationDuration, 250);

        if (TextUtils.isEmpty(btnExpandText)) {
            btnExpandText = "收起";
        }

        if (TextUtils.isEmpty(btnSpreadText)) {
            btnSpreadText = "展开";
        }

        typedArray.recycle();
        init(context);
    }

    private void init(final Context context) {
        setOrientation(VERTICAL);

        //内容显示文本
        mTextView = new TextView(context);
        mTextView.setText(text);
        mTextView.setTextSize(textSize);
        mTextView.setTextColor(textColor);
        mTextView.setEllipsize(TextUtils.TruncateAt.END);

        addView(mTextView);

        LayoutParams paramsLlBtn = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLlBtn = new LinearLayout(context);
        mLlBtn.setOrientation(HORIZONTAL);
        mLlBtn.setGravity(Gravity.CENTER);
        paramsLlBtn.gravity = btnGravity;
        mLlBtn.setLayoutParams(paramsLlBtn);
        mLlBtn.setPadding(15, 15, 15, 15);


        //按钮图标
        LayoutParams paramsImg = new LayoutParams(dip2px(context, 20), dip2px(context, 20));
        mIvArrow = new ImageView(context);
        mIvArrow.setLayoutParams(paramsImg);
        mIvArrow.setImageResource(iconRes);

        if (showIcon) {
            mLlBtn.addView(mIvArrow);
        }

        //控制按钮文本
        mBtnText = new TextView(context);
        mBtnText.setTextColor(btnTextColor);
        mBtnText.setText(btnSpreadText);
        mBtnText.setTextSize(btnTextSize);
        mLlBtn.addView(mBtnText);


        mLlBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                expandByStatus();
            }
        });

        addView(mLlBtn);
    }

    private void expandByStatus() {

        if (expand) {       //折叠状态, 展开

            expandByAnimation(mTextExpandHeight, mTextSpreadHeight);

            mBtnText.setText(btnExpandText);

        } else {        //展开状态, 折叠
            expandByAnimation(mTextSpreadHeight, mTextExpandHeight);

            mBtnText.setText(btnSpreadText);
        }

        expand = !expand;
    }

    /**
     * 折叠与展开动画
     *
     * @param start 开始值
     * @param end   结束值
     */
    private void expandByAnimation(final int start, final int end) {
        mLlBtn.setClickable(false);

        final LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.height = (int) animation.getAnimatedValue();
                mTextView.setLayoutParams(layoutParams);

                //图标旋转
                if (start < end) {  //展开, 从0度到180度
                    mIvArrow.setRotation(180 * (1 - (1f * Math.abs(end - (int) animation.getAnimatedValue()) / Math.abs(end - start))));
                } else {    //折叠, 从180度到0度
                    mIvArrow.setRotation(180 * (1f * Math.abs(end - (int) animation.getAnimatedValue()) / Math.abs(end - start)));
                }
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (start < end) {  //展开, 在展开动画开始之前设置行数, 防止文本部分空白展示
                    mTextView.setMaxLines(Integer.MAX_VALUE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (start > end) {  //折叠
                    mTextView.setMaxLines(showLines);
                }

                mLlBtn.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.setDuration(mAnimationDuration);
        valueAnimator.start();
    }

    //只需要测量一次展开高度,测量后置为false
    private boolean canMeasure = true;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (expand) {
            //测量TextView折叠后的高度
            mTextExpandHeight = mTextView.getMeasuredHeight();
        }

        if (canMeasure) {

            //测量TextView展开高度
            mTextSpreadHeight = mTextView.getMeasuredHeight();

            //文本显示行数大于折叠行数, 显示按钮, 否则隐藏
            mLlBtn.setVisibility(mTextView.getLineCount() > showLines ? VISIBLE : GONE);


            //测量后再设置显示行数
            mTextView.setMaxLines(showLines);

            canMeasure = false;
        }
    }


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public ExpandTextView setText(String text) {
        this.text = text;
        mTextView.setText(text);
        return this;
    }


    public ExpandTextView setTextColor(int textColor) {
        this.textColor = textColor;
        mTextView.setTextColor(textColor);
        return this;
    }


    public ExpandTextView setTextSize(float textSize) {
        this.textSize = textSize;
        mTextView.setTextSize(textSize);
        return this;
    }

    public ExpandTextView setBtnTextColor(int btnTextColor) {
        this.btnTextColor = btnTextColor;
        mBtnText.setTextColor(btnTextColor);
        return this;

    }

    public ExpandTextView setBtnTextSize(int btnTextSize) {
        this.btnTextSize = btnTextSize;
        mBtnText.setTextSize(btnTextSize);
        return this;
    }

    public ExpandTextView setBtnExpandText(String btnExpandText) {
        this.btnExpandText = btnExpandText;
        mBtnText.setText(btnExpandText);
        return this;

    }

    public ExpandTextView setBtnSpreadText(String btnSpreadText) {
        this.btnSpreadText = btnSpreadText;
        mBtnText.setText(btnSpreadText);
        return this;
    }


    public ExpandTextView setAnimationDuration(long animationDuration) {
        mAnimationDuration = animationDuration;
        return this;
    }


    public ExpandTextView setExpand(boolean expand) {
        this.expand = expand;
        return this;
    }

    public ExpandTextView setBtnGravity(int btnGravity) {
        this.btnGravity = btnGravity;
        return this;
    }


    public ExpandTextView setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
        return this;
    }

}
