package com.al.custom_view.hen_coder_course

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.al.custom_view.utils.Util

class CircleView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : View(context, attributeSet, defaultAttr) {

    private val mRadius = Util.dp2px(80f)
    private val mPadding = Util.dp2px(10f)
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = (mRadius + mPadding) * 2

        val width = resolveSize(size.toInt(), widthMeasureSpec)
        val height = resolveSize(size.toInt(), heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.RED)

        canvas.drawCircle(mPadding + mRadius, mPadding + mRadius, mRadius, mPaint)
    }

}