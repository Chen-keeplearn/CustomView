package com.al.custom_view.small_red_book_start.use_rv

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView


class StartPageLayoutManager(var context: Context) : LinearLayoutManager(context) {

    private var MILLISECONDS_PER_INCH = 25f //修改可以改变数据,越大速度越慢

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State?,
        position: Int
    ) {

        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@StartPageLayoutManager.computeScrollVectorForPosition(targetPosition)
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.density
            }
        }

        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    //可以用来设置速度
    fun setSpeedSlow(x: Float) {
        //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
        //0.3f是自己估摸的一个值，可以根据不同需求自己修改
        MILLISECONDS_PER_INCH = context.resources.displayMetrics.density * 0.3f + x
    }

}