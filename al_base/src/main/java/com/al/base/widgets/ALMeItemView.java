package com.al.base.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.al.base.R;

import androidx.annotation.Nullable;

/**
 * 自定义的类似“我的”item布局
 */
public class ALMeItemView extends FrameLayout {
    //左侧图标资源id
    private int leftIconResId;
    //右侧图标资源id
    private int rightIconResId;
    //是否显示左侧图标
    private boolean showLeftIcon = true;
    //是否显示右侧图标
    private boolean showRightIcon = true;
    //左侧文字
    private String leftText;
    //右侧描述文字
    private String rightText;

    private View mView;
    private ImageView mLeftIcon;
    private ImageView mRightMoreIcon;
    private TextView mTvLeftText;
    private TextView mTvRightDesc;

    public ALMeItemView(Context context) {
        this(context, null);
    }

    public ALMeItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ALMeItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     */
    private void init(Context c, AttributeSet attrs) {
        //setClickable设置此属性时,点击的水波纹才会有效果,也在xml中设置
        setClickable(true);
        //初始化attr属性
        TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.ALMeItemView);
        leftIconResId = typedArray.getResourceId(R.styleable.ALMeItemView_left_icon, 0);
        rightIconResId = typedArray.getResourceId(R.styleable.ALMeItemView_right_icon, R.drawable.i_more);
        leftText = typedArray.getString(R.styleable.ALMeItemView_left_text);
        rightText = typedArray.getString(R.styleable.ALMeItemView_desc_text);
        showLeftIcon = typedArray.getBoolean(R.styleable.ALMeItemView_show_left_icon, true);
        showRightIcon = typedArray.getBoolean(R.styleable.ALMeItemView_show_right_icon, true);
        typedArray.recycle();

        initView(c);
    }

    /**
     * 初始化View
     */
    private void initView(Context c) {
        mView = LayoutInflater.from(c).inflate(R.layout.widget_layout_al_me_item_view, null);
        mLeftIcon = mView.findViewById(R.id.iv_me_item_view_left_icon);
        mRightMoreIcon = mView.findViewById(R.id.iv_me_item_view_right_more_icon);
        mTvLeftText = mView.findViewById(R.id.tv_me_item_view_text_title);
        mTvRightDesc = mView.findViewById(R.id.tv_me_item_view_text_desc);
        addView(mView);

        if (leftIconResId == 0 || !showLeftIcon) {
            mLeftIcon.setVisibility(GONE);
        }
        if (rightIconResId == 0 || !showRightIcon) {
            mRightMoreIcon.setVisibility(GONE);
        }

        mTvLeftText.setText(leftText);

        mTvRightDesc.setText(rightText);
    }

    /**
     * 设置左侧文字内容
     */
    public void setLeftText(String str) {
        mTvLeftText.setText(str);
    }

    public void setLeftText(int strResId) {
        mTvLeftText.setText(strResId);
    }

    /**
     * 设置右侧描述文字内容
     */
    public void setRightDesc(String str) {
        mTvRightDesc.setText(str);
    }

    public void setRightDesc(int strResId) {
        mTvRightDesc.setText(strResId);
    }

    /**
     * 设置左侧图标
     */
    public void setLeftIcon(int iconResId) {
        if (iconResId != 0){
            mLeftIcon.setImageResource(iconResId);
            mLeftIcon.setVisibility(VISIBLE);
        }

    }

    /**
     * 设置右侧图标
     */
    public void setRightIcon(int iconResId) {
        mRightMoreIcon.setImageResource(iconResId);
    }

    /**
     * 设置左侧图标是否显示
     */
    public void setLeftIconVisibility(boolean isShow) {
        if (isShow) {
            mLeftIcon.setVisibility(VISIBLE);
        } else {
            mLeftIcon.setVisibility(GONE);
        }
        showLeftIcon = isShow;
    }

    /**
     * 设置右侧图标是否显示
     */
    public void setRightIconVisibility(boolean isShow) {
        if (isShow) {
            mRightMoreIcon.setVisibility(VISIBLE);
        } else {
            mRightMoreIcon.setVisibility(GONE);
        }
        showRightIcon = isShow;
    }
}
