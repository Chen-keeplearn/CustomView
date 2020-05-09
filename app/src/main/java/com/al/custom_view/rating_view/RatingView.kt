package com.al.custom_view.rating_view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.al.custom_view.R
import com.al.custom_view.extension.dp2px
import java.lang.RuntimeException
import kotlin.math.abs

/**
 * 1.刚进来初始化的样子,两种资源图片,选中or不选中 ,评分等级
 * (drawBitmap)
 * view的高度和padding
 * 绘制图片
 * 2.处理用户的交互(onTouch)
 */
class RatingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : View(context, attributeSet, defaultAttr) {

    private var mStarNormalBitmap: Bitmap
    private var mStarFocusBitmap: Bitmap
    private var mLevel: Int = 5
    private var mCurLevel = 0
    private val padding = context.dp2px(10f)

    init {

        val array = context.obtainStyledAttributes(attributeSet, R.styleable.RatingView)

        //拿到normal的星星资源id,可以设置默认,不设置默认,需要抛出异常,需要设置
        val starNormalId =
            array.getResourceId(R.styleable.RatingView_starNormal, R.drawable.star_uncheck)
        //拿到focus的星星资源id
        val starFocusId = array.getResourceId(R.styleable.RatingView_starFocus, 0)
        if (starFocusId == 0) {
            throw RuntimeException("请设置Focus下的资源图片,属性为starFocus")
        }
        //拿到等级
        mLevel = array.getInt(R.styleable.RatingView_level, mLevel)

        //转化成bitmap,canvas.drawBitmap()
        mStarNormalBitmap = BitmapFactory.decodeResource(context.resources, starNormalId)
        mStarFocusBitmap = BitmapFactory.decodeResource(context.resources, starFocusId)

        array.recycle()
    }

    /**
     * 测量 设置view宽高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //根据图片的宽高获取view的宽高,宽度有padding还需要加上padding
        //val padding = context.dp2px(10f) * 4
        //val width = mStarFocusBitmap.width * 5 + padding
        val width = mStarFocusBitmap.width * 5 + padding * 4
        val height = mStarFocusBitmap.height

        setMeasuredDimension(width, height)
    }

    /**
     * 绘制
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (index in 0..4) {
            //绘制默认未选中的星星图片
            //第一个星星从0开始,后面依次是加上相应的星星的宽度 index*width
            val x = (index * (mStarNormalBitmap.width + padding)).toFloat()
            //val x = (index * mStarNormalBitmap.width).toFloat()
            if (mCurLevel > index) {//1分or1等级的时候,所以index = 0 时,需要大于index
                canvas.drawBitmap(mStarFocusBitmap, x, 0f, null)
            } else {
                canvas.drawBitmap(mStarNormalBitmap, x, 0f, null)
            }
        }
    }

    /**
     * onTouch交互
     * 3个手势处理逻辑一样就可以了,
     * 获取x方向移动的距离,
     * 根据当前位置计算等级,
     * 然后刷新显示
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            //减少onDraw绘制
            //MotionEvent.ACTION_DOWN,
            //MotionEvent.ACTION_UP,
            MotionEvent.ACTION_MOVE -> {
                // event.x 即 event.getX()是指相对于view本身x方向移动的距离
                // event.rawX 即 event.getRawX()是指相对于屏幕x方向移动的距离
                val moveX = event.x
                val moveY = event.y
                //Log.i("yl--", "moveY==$moveY")
                //当移动的锤子方向大于控件的高度,就不考虑等级的计算
                if (abs(moveY) > height) {
                    return true
                }
                //计算等级(距离是不满一个,比如半个等级,需要算成一个)
                var currentLevel = ((moveX / (mStarNormalBitmap.width + padding)) + 1).toInt()
                if (currentLevel < 0) {
                    currentLevel = 0
                }
                if (currentLevel > mLevel) {
                    currentLevel = mLevel
                }
                //减少onDraw绘制
                if (currentLevel == mCurLevel) {
                    return true
                }
                mCurLevel = currentLevel
                //刷新
                invalidate()//会调用onDraw方法,但是需要尽可能的减少onDraw的绘制
            }
        }
        //如果不返回true,就表示不消费事件,即当down之后的move、up等事件都会消费
        return true
    }
}