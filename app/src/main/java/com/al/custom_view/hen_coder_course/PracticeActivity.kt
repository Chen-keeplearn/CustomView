package com.al.custom_view.hen_coder_course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivityPracticeBinding

class PracticeActivity:AppCompatActivity() {
    private lateinit var binding:ActivityPracticeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_practice)
    }
}