package com.al.custom_view.path_menu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivityPathMenuBinding
import com.al.custom_view.extension.onNoFastClick
import com.al.custom_view.extension.showToast
import kotlin.math.cos
import kotlin.math.sin

/**
 * 可以看成一个平移、缩放、透明度合成的动画
 * 5个菜单将90度圆弧值平均分成了4份
 * 自定义view三部曲有说明
 * https://blog.csdn.net/harvic880925/article/details/50763286
 * a是夹角
 * 水平平移的长度可以计算成半径R*sin(a)
 * 垂直平移长度可计算成R*cos(a)
 * 注意：缩放动画,缩小到0的时候,属性动画并没有改变实际的位置,比如设置菜单1的点击事件
 * 解决方式：
 * 可以将缩小到0,改成0.1;
 * 也可以在AnimatorListener中的onAnimationEnd方法中设置view的缩放大小动画结束时,设置成view不可见
 */
class PathMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPathMenuBinding

    private var isOpenMenu: Boolean = false

    private lateinit var openAnimator: AnimatorSet
    private lateinit var closeAnimator: AnimatorSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_path_menu)

        /*binding.ivMenu1.setOnClickListener {
            Log.i("yl", "快速点击")
            if (isOpenMenu) {
                isOpenMenu = false
                showToast("关闭菜单")
                startCloseMenuAnimation()
                startCloseRotateAnimation()
            } else {
                isOpenMenu = true
                showToast("打开菜单")
                startOpenMenuAnimation()
                startOpenRotateAnimation()
            }
        }*/

        /**
         * 经发现,快速3次或多次点击打开菜单按钮
         * 打开菜单的旋转动画实现了,展开了,但是打开的菜单并没有出现,动画没有出现
         * 原因：
         * 应该是动画时长设置的500的值,而快速点击按钮的间隔时间是小于500这个的
         * 从而发现,再次点击打开菜单在关闭菜单是在上一次的结束动画前执行了
         * 所以打印日志的时候会出现,再次点击菜单打开按钮,先于上次关闭菜单执行
         */
        binding.ivMenu1.onNoFastClick {
            if (isOpenMenu) {
                isOpenMenu = false
                showToast("关闭菜单")
                startCloseMenuAnimation()
                startCloseRotateAnimation()
            } else {
                isOpenMenu = true
                showToast("打开菜单")
                startOpenMenuAnimation()
                startOpenRotateAnimation()
            }
        }

        binding.ivMenu2.setOnClickListener {
            showToast("菜单2")
            closeMenu()
        }
        binding.ivMenu3.setOnClickListener {
            showToast("菜单3")
            closeMenu()
        }
        binding.ivMenu4.setOnClickListener {
            showToast("菜单4")
            closeMenu()
        }
        binding.ivMenu5.setOnClickListener {
            showToast("菜单5")
            closeMenu()
        }
        binding.ivMenu6.setOnClickListener {
            showToast("菜单6")
            closeMenu()
        }

    }

    /**
     * 点击菜单旋转动画
     */
    private fun startOpenRotateAnimation() {
        val openRotateAnim: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.ivMenu1, "rotation", 0f, 45f + 360f
        )
        openRotateAnim.duration = 500
        openRotateAnim.start()
    }

    private fun startCloseRotateAnimation() {
        val closeRotateAnim: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.ivMenu1, "rotation", 45f + 360f, 0f
        )
        closeRotateAnim.duration = 500
        closeRotateAnim.start()
    }

    /**
     * 打开时各个菜单动画
     */
    private fun startOpenMenuAnimation() {
        openAnimation(binding.ivMenu2, 0)
        openAnimation(binding.ivMenu3, 1)
        openAnimation(binding.ivMenu4, 2)
        openAnimation(binding.ivMenu5, 3)
        openAnimation(binding.ivMenu6, 4)
    }

    /**
     * 打开时菜单动画
     */
    private fun openAnimation(menu: ImageView, index: Int) {
        Log.i("yl--", "view显示==${menu.visibility}")
        if (menu.visibility == View.GONE) {
            menu.visibility = View.VISIBLE
        }
        //Math.toRadians()根据角度求出对应弧度值
        //Math.PI不仅表示π,也表示180度的弧度值
        val angle = index * ((Math.PI / 2) / 4)
        //cos、sin根据弧度求出对应角度的cos、sin值
        val translationX = 380 * sin(angle).toFloat()
        val translationY = 380 * cos(angle).toFloat()
        openAnimator = AnimatorSet()
        openAnimator.playTogether(
            ObjectAnimator.ofFloat(menu, "translationX", 0f, -translationX),
            ObjectAnimator.ofFloat(menu, "translationY", 0f, -translationY),
            ObjectAnimator.ofFloat(menu, "scaleX", 0f, 1f),
            ObjectAnimator.ofFloat(menu, "scaleY", 0f, 1f),
            ObjectAnimator.ofFloat(menu, "alpha", 0f, 1f)
        )
        openAnimator.duration = 500
        openAnimator.start()
        //Log.i("yl", "已经执行打开菜单动画11111")
    }

    /**
     * 关闭时各个菜单动画
     */
    private fun startCloseMenuAnimation() {
        closeMenuAnimation(binding.ivMenu2, 0)
        closeMenuAnimation(binding.ivMenu3, 1)
        closeMenuAnimation(binding.ivMenu4, 2)
        closeMenuAnimation(binding.ivMenu5, 3)
        closeMenuAnimation(binding.ivMenu6, 4)
    }

    /**
     * 关闭时菜单动画
     */
    private fun closeMenuAnimation(menu: ImageView, i: Int) {
        val angle = i * ((Math.PI / 2) / 4)
        val translationX = 380 * sin(angle).toFloat()
        val translationY = 380 * cos(angle).toFloat()
        closeAnimator = AnimatorSet()
        closeAnimator.playTogether(
            ObjectAnimator.ofFloat(menu, "translationX", -translationX, 0f),
            ObjectAnimator.ofFloat(menu, "translationY", -translationY, 0f),
            ObjectAnimator.ofFloat(menu, "scaleX", 1f, 0.1f),
            ObjectAnimator.ofFloat(menu, "scaleY", 1f, 0.1f),
            ObjectAnimator.ofFloat(menu, "alpha", 1f, 0f)
        )
        closeAnimator.duration = 500
        closeAnimator.start()
        closeAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                if (menu.visibility == View.VISIBLE) {
                    //Log.i("yl--", "22222222222222222222++${binding.ivMenu2.width}")
                    menu.visibility = View.GONE
                }
            }
        })
        //Log.i("yl", "已经执行关闭菜单动画------")
    }

    /**
     * 点击其它菜单时,关闭菜单
     */
    private fun closeMenu() {
        startCloseMenuAnimation()
        startCloseRotateAnimation()
        isOpenMenu = false
    }

}