package com.al.custom_view.loading_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : View(context, attributeSet, defaultAttr) {

    private val mPaint = Paint()
    private var mColor: Int = -1

    init {
        mPaint.color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //mPaint.color = Color.BLUE
        //mPaint.style = Paint.Style.FILL
        //抗锯齿
        mPaint.isAntiAlias = true
        //防抖动
        mPaint.isDither = true
        //半径是正方形的一半
        //圆心就是正方形中心
        val cx = (width / 2).toFloat()
        val cy = (height / 2).toFloat()
        canvas.drawCircle(cx, cy, cx, mPaint)
    }


    fun setColor(color: Int) {
        mColor = color
        mPaint.color = color
        invalidate()
    }

    fun getColor(): Int {
        return mColor
    }

}