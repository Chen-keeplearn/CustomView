package com.al.custom_view.small_red_book_start

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivitySmallRedBookStartApgeBinding
import com.al.custom_view.small_red_book_start.use_rv.StartPageAdapter
import com.al.custom_view.small_red_book_start.use_rv.StartPageLayoutManager
import kotlinx.android.synthetic.main.activity_small_red_book_start_apge.*

/**
 * 仿小红书启动页背景图自动滑动
 * 1.利用RecyclerView实现
 * https://www.jianshu.com/p/48a89e678d6c
 * 2.利用自定义FrameLayout实现
 * https://blog.csdn.net/lzw398756924/article/details/106101545
 */
class SmallRedBookStartActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySmallRedBookStartApgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_small_red_book_start_apge)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        rvSmallRedBookStart.adapter = StartPageAdapter()
        rvSmallRedBookStart.layoutManager = StartPageLayoutManager(this)
        rvSmallRedBookStart.smoothScrollToPosition(Int.MAX_VALUE/2)
    }
}