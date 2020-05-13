package com.al.custom_view.extension

import android.view.View
/**
 * 用以防止按钮快速重复点击
 */
abstract class NoFastClickListener : View.OnClickListener {
    //两次点击的间隔时间
    private val minFastClickTime = 600

    //最近一次点击时间
    private var lastClickTime = 0L

    //实现点击事件方法
    abstract fun onNoFastClick(v: View)

    override fun onClick(v: View) {
        //当前点击时间
        val curClickTime = System.currentTimeMillis()
        //必须大于间隔时间才能点击
        if (curClickTime - lastClickTime >= minFastClickTime) {
            lastClickTime = curClickTime
            onNoFastClick(v)
        }
    }
}

/**
 * 规避按钮快速重复点击
 */
fun View.onNoFastClick(block: () -> Unit) {
    this.setOnClickListener(object : NoFastClickListener() {
        override fun onNoFastClick(v: View) {
            block()
        }
    })
}

/**
 * 代替setOnClickListener的扩展
 */
fun View.onClick(block: () -> Unit) {
    this.setOnClickListener {
        block()
    }
}