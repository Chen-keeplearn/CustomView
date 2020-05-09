package com.al.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.al.base.R;

import androidx.annotation.NonNull;


/**
 * 自定义dialog
 */
public class ALDialog extends Dialog {

    private AlertController mAlert;

    public ALDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }

    /**
     * 设置文本
     *
     * @param viewId viewId
     * @param text   text
     */
    public void setText(int viewId, CharSequence text) {
        mAlert.setText(viewId, text);
    }

    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }

    /**
     * 设置点击事件
     *
     * @param viewId        viewId
     * @param clickListener clickListener
     */
    public void setOnclickListener(int viewId, View.OnClickListener clickListener) {
        mAlert.setOnclickListener(viewId, clickListener);
    }


    public static class Builder {
        private AlertController.AlertParams p;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            p = new AlertController.AlertParams(context, themeResId);
        }

        /**
         * 设置contentView布局内容
         */
        public Builder setContentView(View view) {
            p.mView = view;
            p.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置contentView的layout布局
         */
        public Builder setContentView(int layoutId) {
            p.mView = null;
            p.mViewLayoutResId = layoutId;
            return this;
        }

        /**
         * 设置对应view的文本,可能会有多个,链式调用,返回的this
         *
         * @param viewId view的id
         * @param text   text
         */
        public Builder setText(int viewId, CharSequence text) {
            p.mTextArray.put(viewId, text);
            return this;
        }

        /**
         * 设置点击事件
         */
        public Builder setOnClickListener(int viewId, View.OnClickListener clickListener) {
            p.mListenerArray.put(viewId, clickListener);
            return this;
        }


        public Builder setCancelable(boolean cancelable) {
            p.mCancelable = cancelable;
            return this;
        }


        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            p.mOnCancelListener = onCancelListener;
            return this;
        }


        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            p.mOnDismissListener = onDismissListener;
            return this;
        }


        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            p.mOnKeyListener = onKeyListener;
            return this;
        }

        //设置全屏显示
        public Builder setFullWidth() {
            p.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        //设置从底部弹出,并是否有动画
        public Builder setShowFromBottom(boolean isAnimation) {
            if (isAnimation) {//有动画
                p.mAnimation = R.style.dialog_from_bottom_anim;
            }
            p.mGravity = Gravity.BOTTOM;
            return this;
        }

        //设置宽
        public Builder setWidth(int dpWidth) {
            p.mWidth = dpWidth;
            return this;
        }

        //设置高
        public Builder setHeight(int dpHeight) {
            p.mHeight = dpHeight;
            return this;
        }

        //设置宽高
        public Builder setWidthAndHeight(int dpWidth, int dpHeight) {
            p.mWidth = dpWidth;
            p.mHeight = dpHeight;
            return this;
        }

        //设置默认动画
        public Builder setDefaultAnimation() {
            p.mAnimation = R.style.dialog_scale_anim;
            return this;
        }

        //设置动画
        public Builder setAnimation(int animStyle) {
            p.mAnimation = animStyle;
            return this;
        }

        /**
         * 创建dialog create方法
         */
        public ALDialog create() {
            ALDialog dialog = new ALDialog(p.mContext, p.mThemeResId);
            //设置绑定的参数
            p.apply(dialog.mAlert);
            //设置Cancelable
            dialog.setCancelable(p.mCancelable);
            if (p.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            //设置cancel监听
            dialog.setOnCancelListener(p.mOnCancelListener);
            //设置消失监听
            dialog.setOnDismissListener(p.mOnDismissListener);
            //设置OnKeyListener
            if (p.mOnKeyListener != null) {
                dialog.setOnKeyListener(p.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * dialog show方法
         */
        public Dialog show() {
            ALDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
