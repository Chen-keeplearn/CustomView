package com.al.base.utils;

import android.view.View;

/**
 * 按钮防重复点击
 * https://blog.csdn.net/zhufuing/article/details/53021835?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-3
 */
public abstract class OnNoFastClickListener implements View.OnClickListener {

    //按钮重复点击的时间点,两次点击的间隔时间
    private static final long MIN_FAST_CLICK_TIME = 600;

    //最后次点击时间
    private long lastClickTime;

    //实现的点击事件
    abstract void onNoFastClick(View v);

    @Override
    public void onClick(View v) {
        //当前点击时间
        long curClickTime = System.currentTimeMillis();
        if (curClickTime - lastClickTime >= MIN_FAST_CLICK_TIME) {
            //赋值最近一次点击时间
            lastClickTime = curClickTime;
            onNoFastClick(v);
        }
    }
}
