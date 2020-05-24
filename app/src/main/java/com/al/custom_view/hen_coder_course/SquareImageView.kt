package com.al.custom_view.hen_coder_course

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.max

/**
 * 方形ImageView
 */
class SquareImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defaultAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)

        //获取测量的宽高
        val measureW = measuredWidth
        val measureH = measuredHeight

        val size = max(measureW, measureH)

        Log.i("yl--", "width==$width\nmeasureW==$measureW")
        setMeasuredDimension(size, size)
    }
}