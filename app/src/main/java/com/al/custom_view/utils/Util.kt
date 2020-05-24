package com.al.custom_view.utils

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue

object Util {
    fun dp2px(dpValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            Resources.getSystem().displayMetrics
        )
    }

    fun logI(str: String) {
        Log.i("yl--", str)
    }
}