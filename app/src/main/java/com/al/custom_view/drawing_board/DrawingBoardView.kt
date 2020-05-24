package com.al.custom_view.drawing_board

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.al.custom_view.utils.Util

class DrawingBoardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : SurfaceView(context, attributeSet, defaultAttr), SurfaceHolder.Callback {

    //画笔
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    //绘制的路径
    private val mPath = Path()

    init {
        //添加Callback的回调
        holder.addCallback(this)
        //初始化画笔
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.RED
        mPaint.strokeWidth = Util.dp2px(5f)
        //添加TouchListener
        //setOnTouchListener(this)
    }

    /**
     * 绘制方法
     * SurfaceView改变或者开始的时候执行
     */
    private fun drawPath() {
        //SurfaceView绘制的时候锁定画布
        val canvas = holder.lockCanvas()
        //画布颜色
        canvas.drawColor(Color.WHITE)
        canvas.drawPath(mPath, mPaint)
        //绘制完之后,解锁画布
        holder.unlockCanvasAndPost(canvas)
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        TODO("Not yet implemented")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        drawPath()
    }


    /*override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath.moveTo(event.x, event.y)
                drawPath()
            }
            MotionEvent.ACTION_MOVE -> {
                mPath.lineTo(event.x,event.y)
                //Util.logI("x=$event.x,y=$event.y")
                drawPath()
            }
        }
        return true
    }*/

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath.moveTo(event.x, event.y)
                drawPath()
            }
            MotionEvent.ACTION_MOVE -> {
                mPath.lineTo(event.x,event.y)
                //Util.logI("x=$event.x,y=$event.y")
                drawPath()
            }
        }
        return true
    }

    fun clear(){
        //重置路径
        mPath.reset()
        //需要重新调用draw方法,清除已经绘制的
        drawPath()
    }
}