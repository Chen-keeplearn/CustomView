package com.al.custom_view.utils

import android.widget.Toast
import com.al.custom_view.CustomViewApplication

object ToastUtil {
    fun showMsg(msg: String) {
        //Toast.makeText(CustomViewApplication.getAppContext(), msg, Toast.LENGTH_SHORT).show()
        Toast.makeText(CustomViewApplication.mContext, msg, Toast.LENGTH_SHORT).show()
    }
}