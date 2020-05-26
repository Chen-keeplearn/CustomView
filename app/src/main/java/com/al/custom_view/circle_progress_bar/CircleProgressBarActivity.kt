package com.al.custom_view.circle_progress_bar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivityCircleProgressBarBinding
import kotlinx.android.synthetic.main.activity_circle_progress_bar.*


class CircleProgressBarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCircleProgressBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_circle_progress_bar)
        circleProgressBar.setProgress(68f)
    }
}