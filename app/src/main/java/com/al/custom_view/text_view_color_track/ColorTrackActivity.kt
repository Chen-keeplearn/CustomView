package com.al.custom_view.text_view_color_track

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_LEFT
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivityColorTrackBinding
import com.al.custom_view.extension.onClick
import kotlinx.android.synthetic.main.activity_color_track.*


class ColorTrackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityColorTrackBinding

    private lateinit var indicators: ArrayList<ColorTrackTextView>

    private val titles = listOf("推荐", "直播", "新闻", "体育", "军事")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_color_track)

        initIndicator()
        initViewPager()

        btnStartLeft.onClick {
            // 设置朝向
            binding.tvColorTrack.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
            // 用属性动画来控制，当然也可以用线程去控制
            // 用属性动画来控制，当然也可以用线程去控制
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(binding.tvColorTrack, "progress", 0f, 1f)
            animator.setDuration(2000)
                .start()
            // 添加动画的监听，不断的改变当前的进度
            // 添加动画的监听，不断的改变当前的进度
            animator.addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                //Log.e(FragmentActivity.TAG, "progress --> $progress")
                binding.tvColorTrack.setProgress(progress)
            }

            //binding.tvColorTrack.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
            //binding.tvColorTrack.setProgress(0.8f)
        }

        btnStartRight.onClick {
            binding.tvColorTrack.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
            binding.tvColorTrack.setProgress(0.5f)
        }
    }

    private fun initViewPager() {
        binding.vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position < indicators.size - 1) {

                    Log.i("yl", "positionOffset=$positionOffset")

                    val textView = indicators[position]
                    textView.setProgress(1-positionOffset)
                    textView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)

                    val colorTrackTextView = indicators[position + 1]
                    colorTrackTextView.setProgress(positionOffset)
                    colorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
                }
            }
        })


        binding.vp2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return indicators.size
            }

            override fun createFragment(position: Int): Fragment {
                val fragment = ColorTrackTextViewFragment()
                val bundle = Bundle()
                bundle.putString("title", titles[position])
                fragment.arguments = bundle
                return fragment
            }
        }

        //设置默认
        val trackTextView = indicators[0]
        trackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
        trackTextView.setProgress(1f)
    }

    private fun initIndicator() {
        indicators = arrayListOf()
        for (i in titles) {
            val textView = ColorTrackTextView(this)
            val params = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.weight = 1f
            textView.text = i
            textView.layoutParams = params
            //textView.normalColor = Color.BLACK
            //textView.selectColor = Color.RED
            indicators.add(textView)
            binding.textContent.addView(textView)
        }
    }
}