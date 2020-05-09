package com.al.base.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * dialog view的辅助处理类
 */
class DialogViewHelper {
    private View mContentView = null;
    //WeakReference防止内存泄漏
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context context, int layoutId) {
        this();//初始化mViews
        mContentView = LayoutInflater.from(context).inflate(layoutId, null);
    }


    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    /**
     * 设置布局
     *
     * @param contentView contentView
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    /**
     * 设置文本
     *
     * @param viewId viewId
     * @param text   text
     */
    public void setText(int viewId, CharSequence text) {
        //每次findViewById,减少findViewById的次数
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    <T extends View> T getView(int viewId) {
        WeakReference<View> viewReference = mViews.get(viewId);
        View view = null;
        if (viewReference != null) {
            view = viewReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置点击事件
     *
     * @param viewId        viewId
     * @param clickListener clickListener
     */
    public void setOnclickListener(int viewId, View.OnClickListener clickListener) {
        View v = getView(viewId);
        if (v != null) {
            v.setOnClickListener(clickListener);
        }
    }

    public View getContentView() {
        return mContentView;
    }
}
