package com.al.base.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.al.base.utils.Utils;


class AlertController {
    private ALDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(ALDialog alertDialog, Window window) {
        this.mDialog = alertDialog;
        this.mWindow = window;
    }

    public void setViewHelper(DialogViewHelper mViewHelper) {
        this.mViewHelper = mViewHelper;
    }

    /**
     * 设置文本
     *
     * @param viewId viewId
     * @param text   text
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    <T extends View> T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param viewId        viewId
     * @param clickListener clickListener
     */
    public void setOnclickListener(int viewId, View.OnClickListener clickListener) {
        mViewHelper.setOnclickListener(viewId, clickListener);
    }

    /**
     * 获取dialog
     */
    public ALDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取dialog的window
     */
    public Window getWindow() {
        return mWindow;
    }

    /**
     * 构造dialog参数,里面的参数都是public
     */
    public static class AlertParams {
        public Context mContext;
        public int mThemeResId;
        //点击dialog以外区域是否能够取消,默认可以取消
        public boolean mCancelable = true;
        //dialog cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //dialog 消失监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //dialog 按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局view
        public View mView;
        //布局layout id
        public int mViewLayoutResId;
        //存放点击事件 SparseArray 比 HashMap<Integer,v> 更高效 https://www.jianshu.com/p/f0d423e3eab3
        public SparseArray<View.OnClickListener> mListenerArray = new SparseArray<>();
        //dialog 布局中的view 字体修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //动画
        public int mAnimation = 0;
        //位置
        public int mGravity = Gravity.CENTER;
        //高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         */
        public void apply(AlertController mAlert) {
            //设置Dialog布局 DialogViewHelper
            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }
            //给dialog设置布局,利用持有引用mAlert
            mAlert.getDialog().setContentView(viewHelper.getContentView());
            //设置AlertController 辅助类
            mAlert.setViewHelper(viewHelper);
            //设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                mAlert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }
            //设置点击事件
            int clickArraySize = mListenerArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                mAlert.setOnclickListener(mListenerArray.keyAt(i), mListenerArray.valueAt(i));
            }
            //配置自定义效果 全屏 、底部弹出、动画 ->window
            Window window = mAlert.getWindow();
            //设置位置
            window.setGravity(mGravity);
            //设置动画
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }
            //设置宽高
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            //加入判断,将传入设置的宽高转换成dp
            if (mWidth != ViewGroup.LayoutParams.WRAP_CONTENT
                    && mWidth != ViewGroup.LayoutParams.MATCH_PARENT) {
                layoutParams.width = Utils.dip2px(mContext, mWidth);
            } else {
                layoutParams.width = mWidth;
            }
            if (mHeight != ViewGroup.LayoutParams.WRAP_CONTENT
                    && mHeight != ViewGroup.LayoutParams.MATCH_PARENT) {
                layoutParams.height = Utils.dip2px(mContext, mHeight);
            } else {
                layoutParams.height = mHeight;
            }
            window.setAttributes(layoutParams);

        }
    }
}
