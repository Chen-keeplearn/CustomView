package com.al.custom_view.step_view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivityQqStepViewBinding

class QQStepViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQqStepViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qq_step_view)

        binding.stepView.setMaxStep(5000)

        val objectAnimator = ObjectAnimator.ofFloat(0f, 3000f)
        objectAnimator.addUpdateListener {
            val curStep: Float = it.animatedValue as Float
            binding.stepView.setCurrentStep(curStep.toInt())
        }
        objectAnimator.duration = 1000
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }
}