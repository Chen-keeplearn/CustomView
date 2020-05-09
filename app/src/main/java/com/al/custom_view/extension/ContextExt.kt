package com.al.custom_view.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.al.custom_view.CustomViewApplication


fun showToast(msg:String){
    Toast.makeText(CustomViewApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.startActivity(clazz: Class<out Activity>, params: Map<String, Any> = emptyMap()) {
    val intent = Intent(this, clazz)
    params.forEach {
        when (it.value) {
            is Int -> intent.putExtra(it.key, it.value as Int)
            is Boolean -> intent.putExtra(it.key, it.value as Boolean)
            is String -> intent.putExtra(it.key, it.value as String)
            is Float -> intent.putExtra(it.key, it.value as Float)
            is Double -> intent.putExtra(it.key, it.value as Double)
        }
    }
    this.startActivity(intent)
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 将dp转换成px
 *
 * @param dpValue dpValue
 * @return px
 */
fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 将px值转换为sp值
 * @param pxValue
 * @return
 */
fun Context.px2sp(pxValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 将sp值转换为px值
 * @param spValue
 * @return
 */
fun Context.sp2px(spValue: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

