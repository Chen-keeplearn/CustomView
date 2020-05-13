package com.al.custom_view.text_view_color_track

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView
import com.al.custom_view.R

/**
 * 颜色跟踪的textView
 * 一个文字,两种颜色
 */
class ColorTrackTextView : TextView {

     var selectColor: Int
     var normalColor: Int

    //绘制不变色字体的画笔
    private var mPaintOrigin = Paint()

    //绘制变色字体的画笔
    private var mPaintSelect = Paint()

    //颜色变化的朝向
    private var mDirection = Direction.LEFT_TO_RIGHT

    //表示当前滑动的进度
    private var mCurrentProgress = 0f

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet? = null) : this(
        context,
        attributeSet,
        0
    )

    constructor(context: Context, attributeSet: AttributeSet? = null, defaultAttr: Int) : super(
        context,
        attributeSet,
        defaultAttr
    ) {

        val array = context.obtainStyledAttributes(attributeSet, R.styleable.ColorTrackTextView)

        normalColor = array.getColor(R.styleable.ColorTrackTextView_originColor, Color.BLACK)
        selectColor = array.getColor(R.styleable.ColorTrackTextView_selectColor, Color.RED)

        array.recycle()

        mPaintOrigin = getPaint(normalColor)

        mPaintSelect = getPaint(selectColor)

    }

    private fun getPaint(color: Int): Paint {
        val paint = Paint()
        paint.color = color
        paint.isAntiAlias = true
        paint.isDither = true
        paint.textSize = this.textSize
        return paint
    }


    /**
     * 绘制另外的颜色使用canvas.clipRect()裁剪区域
     */
    override fun onDraw(canvas: Canvas) {

        //根据进度计算中间值(即两个颜色的中间值,上一个颜色结束,下一个颜色开始的地方)
        val middle = (width * mCurrentProgress).toInt()
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawTextOriginDirectionLeft(canvas, middle)
            drawTextSelectDirectionLeft(canvas, middle)
        }
        if (mDirection == Direction.RIGHT_TO_LEFT) {
            drawTextOriginDirectionRight(canvas, middle)
            drawTextSelectDirectionRight(canvas, middle)
        }
    }

    private fun drawTextOriginDirectionLeft(canvas: Canvas, middle: Int) {
        drawText(canvas, mPaintSelect, 0, middle)
    }

    private fun drawTextSelectDirectionLeft(canvas: Canvas, middle: Int) {
        drawText(canvas, mPaintOrigin, middle, width)
    }

    private fun drawTextOriginDirectionRight(canvas: Canvas, middle: Int) {
        drawText(canvas, mPaintSelect, width - middle, width)
    }

    private fun drawTextSelectDirectionRight(canvas: Canvas, middle: Int) {
        drawText(canvas, mPaintOrigin, 0, width - middle)
    }


    private fun drawText(canvas: Canvas, paint: Paint, start: Int, end: Int) {
        val mText = this.text.toString()

        canvas.save()
        canvas.clipRect(start, 0, end, height)

        val bounds = Rect()

        paint.getTextBounds(mText, 0, mText.length, bounds)

        val fontMetrics = paint.fontMetrics
        //得到文字基线
        val baselineY =
            (measuredHeight / 2) + ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom)
        //文字开始绘制的x(即,view的宽度的一半减去文字宽度的一半)
        val x = ((measuredWidth / 2) - (bounds.width() / 2)).toFloat()
        canvas.drawText(mText, x, baselineY, paint)

        canvas.restore()
    }


    /**
     * 定义颜色变化的朝向的枚举
     */
    enum class Direction {
        //选中色,从左开始,右边是正常默认颜色
        LEFT_TO_RIGHT,

        //选中色,从右开始,左边是默认颜色
        RIGHT_TO_LEFT
    }

    fun setDirection(direction: Direction) {
        mDirection = direction
    }

    fun setProgress(progress: Float) {
        mCurrentProgress = progress
        invalidate()
    }

}