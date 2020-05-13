package com.al.custom_view.dashboard

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.al.custom_view.utils.Util

class Dashboard(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    var paint =
        Paint(Paint.ANTI_ALIAS_FLAG)
    var dash = Path()
    var effect: PathDashPathEffect? = null

    companion object {
        private const val ANGLE = 120
        private val RADIUS: Float = Util.dp2px(150f)
        private val LENGTH: Float = Util.dp2px(100f)
    }

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Util.dp2px(2f)
        dash.addRect(0f, 0f, Util.dp2px(2f), Util.dp2px(10f), Path.Direction.CW)
        val arc = Path()
        arc.addArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            90 + ANGLE / 2.toFloat(),
            360 - ANGLE.toFloat()
        )
        val pathMeasure = PathMeasure(arc, false)
        effect = PathDashPathEffect(
            dash,
            (pathMeasure.length - Util.dp2px(2f)) / 20,
            0f,
            PathDashPathEffect.Style.ROTATE
        )
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画线
        canvas.drawArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            90 + ANGLE / 2.toFloat(),
            360 - ANGLE.toFloat(),
            false,
            paint
        )

        // 画刻度
        paint.pathEffect = effect
        canvas.drawArc(
            width / 2 - RADIUS,
            height / 2 - RADIUS,
            width / 2 + RADIUS,
            height / 2 + RADIUS,
            90 + ANGLE / 2.toFloat(),
            360 - ANGLE.toFloat(),
            false,
            paint
        )
        paint.pathEffect = null

        // 画指针
        canvas.drawLine(
            width / 2.toFloat(), height / 2.toFloat(),
            Math.cos(Math.toRadians(getAngleFromMark(10).toDouble())).toFloat() * LENGTH + width / 2,
            Math.sin(Math.toRadians(getAngleFromMark(10).toDouble()))
                .toFloat() * LENGTH + height / 2,
            paint
        )
    }

    private fun getAngleFromMark(mark: Int): Int {
        return (90 + ANGLE.toFloat() / 2 + (360 - ANGLE.toFloat()) / 20 * mark).toInt()
    }


}