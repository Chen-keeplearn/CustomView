package com.al.custom_view.small_red_book_start.use_rv

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * 图片宽度充满屏幕,高度按比例生成
 */
class FitImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : AppCompatImageView(context, attributeSet, defaultAttr) {

    private val mDrawable: Drawable? = drawable

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        if (mDrawable != null) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = width * mDrawable.intrinsicHeight / mDrawable.intrinsicWidth
            setMeasuredDimension(width,height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}