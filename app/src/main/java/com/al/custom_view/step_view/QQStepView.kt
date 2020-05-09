package com.al.custom_view.step_view

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.al.custom_view.R
import com.al.custom_view.extension.dp2px
import com.al.custom_view.extension.px2sp
import com.al.custom_view.extension.sp2px

/**
 * 1.圆弧颜色
 * 2.圆弧宽度
 * 3.步数弧度颜色
 * 4.字体大小
 * 5.字体颜色
 */
class QQStepView : View {

    private var mNormalColor: Int = Color.BLUE
    private var mStepColor: Int
    private var mBorderWidth: Int
    private var mStepTextSize: Int
    private var mStepTextColor: Int

    private var mPaint: Paint = Paint()//外圆弧画笔

    private var mStepMax = 0
    private var mCurStep = 0//当前步数
    private var mStepPaint: Paint = Paint()//内圆弧画笔
    private var mTextPaint: Paint = Paint()//文字画笔


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet? = null) : this(
        context,
        attributeSet!!,
        0
    )

    @TargetApi(Build.VERSION_CODES.M)
    constructor(context: Context, attributeSet: AttributeSet, defaultAttr: Int) : super(
        context,
        attributeSet,
        defaultAttr
    ) {
        //1.分析效果
        //2.定义自定义属性
        //3.获取自定义属性
        //4.onMeasure测量大小
        //5.画圆弧、文字
        //6.其它

        mStepColor = context.resources.getColor(R.color.colorAccent, null)
        mBorderWidth = context.dp2px(6f)
        mStepTextSize = context.sp2px(16f)
        mStepTextColor = context.resources.getColor(R.color.colorAccent, null)


        //获取自定义属性
        val array = context.obtainStyledAttributes(attributeSet, R.styleable.QQStepView)
        mNormalColor = array.getColor(R.styleable.QQStepView_normalColor, mNormalColor)
        mStepColor = array.getColor(R.styleable.QQStepView_stepColor, mStepColor)
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor)
        mStepTextSize =
            array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize)
        mBorderWidth = array.getDimension(
            R.styleable.QQStepView_borderWidth,
            mBorderWidth.toFloat()
        ).toInt()

        array.recycle()

        mPaint.color = mNormalColor
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND//圆角
        mPaint.strokeWidth = mBorderWidth.toFloat()

        mStepPaint.color = mStepColor
        mStepPaint.style = Paint.Style.STROKE
        mStepPaint.isAntiAlias = true
        mStepPaint.strokeCap = Paint.Cap.ROUND//圆角
        mStepPaint.strokeWidth = mBorderWidth.toFloat()

        mTextPaint.color = mStepTextColor
        mTextPaint.style = Paint.Style.STROKE
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = mStepTextSize.toFloat()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //调用者有可能会在布局中设置wrap_content
        //获取AT_MOST模式,当设置wrap_content时要么抛出异常提示设置固定值,要么在该模式下设置默认值
        //判断宽高,取最小值,保证是一个正方形的圆弧

        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        //设置默认
        val widthModel = MeasureSpec.getMode(widthMeasureSpec)
        val heightModel = MeasureSpec.getMode(heightMeasureSpec)
        if (widthModel == MeasureSpec.AT_MOST || heightModel == MeasureSpec.AT_MOST) {
            width = context.dp2px(200f)
            height = context.dp2px(200f)
        }
        setMeasuredDimension(
            if (width > height) height else width,
            if (width > height) height else width
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //描边有宽度 半径应当减去
        //val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val rectF = RectF(
            (mBorderWidth / 2).toFloat(),
            (mBorderWidth / 2).toFloat(),
            width.toFloat() - mBorderWidth / 2,
            height.toFloat() - mBorderWidth / 2
        )
        //画外圆弧 可以看出是从135度开始扫,直到270度为止
        canvas.drawArc(rectF, 135f, 270f, false, mPaint)

        //画外圆弧,不能写死,调用传入参数,百分比
        if (mStepMax == 0) return
        val sweepAnglePercent = mCurStep.toFloat() / mStepMax.toFloat()
        canvas.drawArc(rectF, 135f, 270f * sweepAnglePercent, false, mStepPaint)

        //画文字
        //文字显示正中间,文字绘制的起始位置是在view宽度的一半减去文字宽度的一半
        //获取文字的宽度
        val rect = Rect()
        mTextPaint.getTextBounds(mCurStep.toString(), 0, mCurStep.toString().length, rect)
        val start = width / 2 - rect.width() / 2
        //基线 y
        val fontMetrics = mTextPaint.fontMetricsInt
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val textBaseline = height / 2 + dy
        canvas.drawText(mCurStep.toString(), start.toFloat(), textBaseline.toFloat(), mTextPaint)
    }

    fun setMaxStep(maxStep: Int) {
        mStepMax = maxStep
    }

    fun setCurrentStep(currentStep: Int) {
        mCurStep = currentStep
        //重绘
        invalidate()
    }

}