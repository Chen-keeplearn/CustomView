package com.al.custom_view.loading_view

import android.animation.*
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import com.al.custom_view.extension.dp2px

/**
 * 三个圆形view
 * 两个view分别向两边做平移动画
 * 监听动画并设置颜色
 */
class LoadingViewLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : RelativeLayout(context, attributeSet, defaultAttr) {

    private val mLeftView: CircleView
    private val mCenterView: CircleView
    private val mRightView: CircleView

    init {
        mLeftView = getCircleView()
        mCenterView = getCircleView()
        mRightView = getCircleView()

        mLeftView.setColor(Color.BLUE)
        mCenterView.setColor(Color.GREEN)
        mRightView.setColor(Color.RED)

        addView(mLeftView)
        addView(mRightView)
        addView(mCenterView)
    }

    private fun outAnim() {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(mLeftView, "translationX", 0f, -80f),
            ObjectAnimator.ofFloat(mRightView, "translationX", 0f, 80f)
        )
        animatorSet.duration = 450
        animatorSet.start()
        //animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                innerAnim()
                //设置颜色交换
                val leftColor = mLeftView.getColor()
                val centerColor = mCenterView.getColor()
                val rightColor = mRightView.getColor()
                mLeftView.setColor(centerColor)
                mCenterView.setColor(rightColor)
                mRightView.setColor(leftColor)
            }
        })
    }

    private fun innerAnim() {
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(mLeftView, "translationX", -80f, 0f),
            ObjectAnimator.ofFloat(mRightView, "translationX", 80f, 0f)
        )
        animatorSet.duration = 450
        animatorSet.start()
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                outAnim()
            }
        })
    }

    private fun getCircleView(): CircleView {
        val circleView = CircleView(context)
        val params = LayoutParams(context.dp2px(10f), context.dp2px(10f))
        //将view设置在中间
        params.addRule(CENTER_IN_PARENT)
        circleView.layoutParams = params
        return circleView
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        outAnim()
    }

}