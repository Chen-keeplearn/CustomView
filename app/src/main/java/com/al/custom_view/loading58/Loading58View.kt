package com.al.custom_view.loading58

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.al.custom_view.extension.dp2px
import kotlin.math.sqrt

class Loading58View : View {

    private var mPaint = Paint()

    private var mCurrentShape = Shape.TRIANGLE

    private var rect: Rect? = null

    private var mTrianglePath: Path? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet? = null) : this(
        context,
        attributeSet!!,
        0
    )

    constructor(context: Context, attributeSet: AttributeSet, defaultAttr: Int) : super(
        context,
        attributeSet,
        defaultAttr
    )

    /**
     * 三角形,圆形,正方形都是在一个正方形内绘制
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx: Float = (width / 2).toFloat()
        val cy: Float = (height  / 2).toFloat()

        when (mCurrentShape) {

            //画正方形
            Shape.SQUARE -> {
                mPaint.color = Color.GREEN
                if (rect == null) {
                    rect = Rect(0, 0, width, height)
                }
                canvas.drawRect(rect!!, mPaint)
            }

            //画圆
            Shape.CIRCLE -> {
                mPaint.color = Color.BLUE
                canvas.drawCircle(cx, cy, cx, mPaint)
            }

            //画三角
            Shape.TRIANGLE -> {
                //需要画等边三角,等腰三角动画跳动不美观
                mPaint.color = Color.RED
                if (mTrianglePath == null) {
                    mTrianglePath = Path()
                    mTrianglePath!!.moveTo(cx, 0f)
                    //第二个点y坐标,即sin 60度,二分之根号三
                    mTrianglePath!!.lineTo(0f, (width * (sqrt(3.toDouble()) / 2)).toFloat())
                    mTrianglePath!!.lineTo(
                        width.toFloat(),
                        (width * (sqrt(3.toDouble()) / 2)).toFloat()
                    )
                    mTrianglePath!!.close()
                }
                canvas.drawPath(mTrianglePath!!, mPaint)
            }
        }
        startUpAnimation()
    }

    private fun startUpAnimation() {
        val animSet = AnimatorSet()
        animSet.playTogether(
            ObjectAnimator.ofFloat(this, "translationY", 0f, -(2 * height).toFloat()),
            ObjectAnimator.ofFloat(this, "rotation", 0f, 180f)
        )
        animSet.duration = 800
        animSet.interpolator = DecelerateInterpolator()
        animSet.start()
        animSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                startDownAnimation()
            }
        })
    }

    private fun startDownAnimation() {
        val downAnimSet = AnimatorSet()
        downAnimSet.playTogether(
            ObjectAnimator.ofFloat(this, "translationY", -(2 * height).toFloat(), 0f)
            //ObjectAnimator.ofFloat(this, "rotation", 360f, 0f)
        )
        downAnimSet.duration = 800
        downAnimSet.interpolator = AccelerateInterpolator()
        downAnimSet.start()
        downAnimSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                exchange()
            }
        })
    }

    private fun exchange() {
        mCurrentShape = when (mCurrentShape) {
            Shape.SQUARE -> Shape.CIRCLE
            Shape.CIRCLE -> Shape.TRIANGLE
            Shape.TRIANGLE -> Shape.SQUARE
        }
        invalidate()
    }

    enum class Shape {
        CIRCLE, SQUARE, TRIANGLE
    }
}