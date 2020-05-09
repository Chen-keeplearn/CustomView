package com.al.custom_view

import android.app.Application
import android.content.Context

class CustomViewApplication : Application() {

    //伴生对象
    companion object {
        lateinit var mContext: Context

        //@JvmStatic
        fun getAppContext(): Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}