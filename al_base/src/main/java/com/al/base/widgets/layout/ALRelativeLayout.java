package com.al.base.widgets.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.al.base.R;

import androidx.annotation.Nullable;

public class ALRelativeLayout extends RelativeLayout {
    private int mCorner;//四周圆角
    private int mCornerLeftTop = 0;//左上角圆角
    private int mCornerRightTop = 0;//右上角圆角
    private int mCornerLeftBottom = 0;//左下角圆角
    private int mCornerRightBottom = 0;//右下角圆角
    private int mBorderWidth;//边框宽度
    private int mBorderColor;//边框颜色
    private GradientDrawable mLayoutBackground;
    private int mNormalBackground;

    public ALRelativeLayout(Context context) {
        this(context, null);
    }

    public ALRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ALRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    /**
     * 初始化属性
     */
    private void initAttrs(Context c, AttributeSet attrs) {
        TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.ALRelativeLayout);
        //所有角圆角半径
        mCorner = typedArray.getDimensionPixelSize(R.styleable.ALRelativeLayout_corner, 0);
        //左上角圆角半径
        mCornerLeftTop = typedArray.getDimensionPixelSize(R.styleable.ALRelativeLayout_corner_left_top, 0);
        //右上角圆角半径
        mCornerRightTop = typedArray.getDimensionPixelSize(R.styleable.ALRelativeLayout_corner_right_top, 0);
        //左下角圆角半径
        mCornerLeftBottom = typedArray.getDimensionPixelSize(R.styleable.ALRelativeLayout_corner_left_bottom, 0);
        //右下角圆角半径
        mCornerRightBottom = typedArray.getDimensionPixelSize(R.styleable.ALRelativeLayout_corner_right_bottom, 0);
        //边框宽度
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.ALRelativeLayout_border_width, 0);
        //边框颜色
        mBorderColor = typedArray.getColor(R.styleable.ALRelativeLayout_border_color, Color.TRANSPARENT);
        //背景颜色
        mNormalBackground = typedArray.getColor(R.styleable.ALRelativeLayout_normal_background, Color.TRANSPARENT);
        typedArray.recycle();
        initLayoutBg();
    }

    /**
     * 利用GradientDrawable设置背景
     */
    private void initLayoutBg() {
        //背景
        mLayoutBackground = new GradientDrawable();
        //设置填充颜色
        mLayoutBackground.setColor(mNormalBackground);
        //形状
        mLayoutBackground.setShape(GradientDrawable.RECTANGLE);
        //ordered top-left, top-right, bottom-right, bottom-left
        mLayoutBackground.setCornerRadii(new float[]{
                mCornerLeftTop != 0 ? mCornerLeftTop : mCorner,
                mCornerLeftTop != 0 ? mCornerLeftTop : mCorner,
                mCornerRightTop != 0 ? mCornerRightTop : mCorner,
                mCornerRightTop != 0 ? mCornerRightTop : mCorner,
                mCornerRightBottom != 0 ? mCornerRightBottom : mCorner,
                mCornerRightBottom != 0 ? mCornerRightBottom : mCorner,
                mCornerLeftBottom != 0 ? mCornerLeftBottom : mCorner,
                mCornerLeftBottom != 0 ? mCornerLeftBottom : mCorner});

        //设置边框颜色和边框宽度
        mLayoutBackground.setStroke(mBorderWidth, mBorderColor);
        //设置背景
        setBackground(mLayoutBackground);
    }

    /**
     * 设置背景颜色
     */
    public void setNormalBackground(int color){
        mLayoutBackground.setColor(color);
        setBackground(mLayoutBackground);
    }
}

