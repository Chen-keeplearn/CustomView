package com.al.custom_view.dashboard

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.al.custom_view.utils.Util
import kotlin.math.cos
import kotlin.math.sin

/**
 * 仪表盘
 * 1.圆弧
 */
class DashboardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : View(context, attributeSet, defaultAttr) {

    //抗锯齿画笔
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //仪表盘圆弧半径
    private val mRadius = Util.dp2px(150f)

    //起始点与结束点和圆心之间的夹角
    private val mAngel = 120f

    //画笔宽度
    private val mPaintWidth = Util.dp2px(2f)

    //绘制虚线刻度的画笔
    private val mDashPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //刻度宽度
    private val mScaleWidth = Util.dp2px(2f)

    //绘制指针画笔
    private val mPointerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //指针长度
    private val mPointerLength = Util.dp2px(100f)

    //起始角度
    private val startAngle = mAngel / 2 + 90

    //用于构建PathMeasure的path
    private val path = Path()

    //刻度长宽度的path
    private val dashPath = Path()

    //用于刻度绘制
    private val pathDashPathEffect: PathDashPathEffect

    init {
        mPaint.strokeWidth = mPaintWidth
        mPaint.style = Paint.Style.STROKE

        mDashPaint.strokeWidth = mPaintWidth
        mDashPaint.style = Paint.Style.STROKE


        mPointerPaint.strokeWidth = mPaintWidth
        mPointerPaint.style = Paint.Style.STROKE

        dashPath.addRect(0f, 0f, mScaleWidth, Util.dp2px(10f), Path.Direction.CW)

        path.addArc(
            width / 2 - mRadius,
            height / 2 - mRadius,
            width / 2 + mRadius,
            height / 2 + mRadius, startAngle,
            360 - mAngel
        )
        val pathMeasure = PathMeasure(path, false)
        //通过pathMeasure计算整个圆弧长度
        //val radian = Math.toRadians((360 - mAngel).toDouble())
        //val radian = (360 - mAngel) * (Math.PI / 180)
        //val radian = (360 - mAngel) * (Math.PI / 180) * mRadius
        //Log.i("yl--", "pathMeasure=${pathMeasure.length}\nradian=$radian")
        pathDashPathEffect = PathDashPathEffect(
            dashPath,
            (pathMeasure.length - mScaleWidth) / 20,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //绘制仪表盘弧度
        canvas.drawArc(
            width / 2 - mRadius,
            height / 2 - mRadius,
            width / 2 + mRadius,
            height / 2 + mRadius,
            startAngle,
            360 - mAngel,
            false,
            mPaint
        )
        //绘制刻度
        mDashPaint.pathEffect = pathDashPathEffect
        canvas.drawArc(
            width / 2 - mRadius,
            height / 2 - mRadius,
            width / 2 + mRadius,
            height / 2 + mRadius,
            startAngle,
            360 - mAngel,
            false,
            mDashPaint
        )
        //绘制指针
        //mDashPaint.pathEffect = null
        //Log.i("yl--", "angle==${getCurrentAngle(1)}")
        //指针结束点的X坐标就是角度的余弦
        val endX = cos(Math.toRadians(getCurrentAngle(2))) * mPointerLength + width / 2
        //指针结束点的Y坐标就是角度的正弦
        val endY = sin(Math.toRadians(getCurrentAngle(2))) * mPointerLength + height / 2
        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            endX.toFloat(),
            endY.toFloat(),
            mPointerPaint
        )
    }

    /**
     * 根据刻度,得到当前的角度
     * @param mark:当前第几个刻度
     */
    private fun getCurrentAngle(mark: Int): Double {
        //起始角度,通过画图可知道,起始角度为：绘制的夹角/2+90
        val startAngle = 90 + mAngel / 2
        //(360 - mAngel) / 20即绘制的圆弧每个刻度的夹角
        return (startAngle + ((360 - mAngel) / 20 * mark)).toDouble()
    }
}