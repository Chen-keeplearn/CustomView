package com.al.custom_view

import android.app.Application
import android.content.Context

class CustomViewApplication : Application() {

    //伴生对象
    companion object {
        @JvmStatic
        @get:JvmName("mContext")
        //当在@JvmStatic的基础上再加上@get:JvmName("mContext")注解,
        //java就可以直接调用CustomViewApplication.mContext();
        lateinit var mContext: Context
            //@JvmStatic
            /*fun getAppContext(): Context {
                return mContext
            }*/
            //此处get函数可以省略,为了防止外部调用set改变值 将set设置成private
            //这样java调用的时候就是CustomViewApplication.Companion.getMContext();
            //如果加上@JvmStatic就可以直接get加属性名 CustomViewApplication.getMContext()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }
}