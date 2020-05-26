package com.al.custom_view.circle_progress_bar

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.al.custom_view.R
import com.al.custom_view.extension.dp2px
import com.al.custom_view.extension.sp2px
import java.lang.IllegalArgumentException

/**
 * 所需自定义属性 进度条正常颜色、进度条颜色、进度条文字大小、进度条文字颜色、进度条宽度
 * 1.获取自定义属性
 * 2.测量大小
 * 3.绘制
 */
class CircleProgressBar @JvmOverloads constructor(
    c: Context,
    attrs: AttributeSet? = null,
    defaultAttr: Int = 0
) : View(c, attrs, defaultAttr) {
    //正常情况下经度条颜色
    private var mNormalColor: Int

    //进度条颜色
    private var mProgressColor: Int

    //进度条文字颜色
    private var mTextColor: Int

    //进度条文字大小
    private var mTextSize: Int

    //进度条宽度
    private var mBorderWidth: Float

    //正常圆形的画笔
    private lateinit var mPaintNormal: Paint

    //进度条画笔
    private lateinit var mPaintProgress: Paint

    //字体画笔
    private lateinit var mPaintText: Paint

    //当前进度
    private var mCurrentProgress = 0F

    //最大进度
    private var mMaxProgress = 100

    init {
        val array = c.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar)

        mNormalColor = array.getColor(R.styleable.CircleProgressBar_normalColor, Color.BLUE)
        mProgressColor =
            array.getColor(R.styleable.CircleProgressBar_progressColor, Color.CYAN)
        mTextColor =
            array.getColor(R.styleable.CircleProgressBar_android_textColor, Color.BLACK)
        mTextSize = array.getDimensionPixelSize(
            R.styleable.CircleProgressBar_android_textSize,
            c.sp2px(16f)
        )
        mBorderWidth = array.getDimension(
            R.styleable.CircleProgressBar_borderWidth,
            c.dp2px(5f).toFloat()
        )
        mCurrentProgress = array.getFloat(R.styleable.CircleProgressBar_android_progress, 0F)
        array.recycle()

        initPaint()
    }

    private fun initPaint() {
        mPaintNormal = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintNormal.strokeWidth = mBorderWidth
        mPaintNormal.color = mNormalColor
        mPaintNormal.style = Paint.Style.STROKE

        mPaintProgress = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintProgress.strokeWidth = mBorderWidth
        mPaintProgress.color = mProgressColor
        mPaintProgress.style = Paint.Style.STROKE

        mPaintText = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintText.textSize = mTextSize.toFloat()
        mPaintText.color = mTextColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = context.dp2px(200f)
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = context.dp2px(200f)
        }

        //设置是正方形圆
        setMeasuredDimension(
            if (widthSize > heightSize) heightSize else widthSize,
            if (widthSize > heightSize) heightSize else widthSize
        )
    }

    /**
     * 绘制
     * 1.绘制正常圆形
     * 2.绘制圆形进度条
     * 3.绘制文字
     * 绘制时需要注意减去画笔的宽度
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制正常圆环
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            width / 2 - mBorderWidth / 2,
            mPaintNormal
        )

        //绘制圆形进度条
        val progressPath = Path()
        //当前进度百分比(直接int相除,小数转为int类型就省去小数点了)
        val progressPercent = mCurrentProgress / mMaxProgress
        progressPath.addArc(
            mBorderWidth / 2,
            mBorderWidth / 2,
            width - mBorderWidth / 2,
            height - mBorderWidth / 2,
            0f,
            360f * progressPercent
        )
        canvas.drawPath(progressPath, mPaintProgress)


        //绘制进度文字
        val progressStr = "${mCurrentProgress.toInt()}%"
        //文字Rect
        val textBounds = Rect()
        mPaintText.getTextBounds(progressStr, 0, progressStr.length, textBounds)
        //计算文字baseline
        val fontMetrics = mPaintText.fontMetrics
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val mBaseLine = height / 2 + dy
        canvas.drawText(
            progressStr,
            (width / 2).toFloat() - textBounds.width() / 2,
            mBaseLine,
            mPaintText
        )
    }

    fun setProgress(progress: Float) {
        if (progress < 0 || progress > 100){
            throw IllegalArgumentException("progress should be between 0 and 100")
        }
        val anim = ObjectAnimator.ofFloat(0f, progress)
        anim.addUpdateListener {
            val currentProgress = it.animatedValue
            mCurrentProgress = currentProgress as Float
            invalidate()
        }
        anim.duration = 2500
        anim.interpolator = DecelerateInterpolator()
        anim.start()
    }


}