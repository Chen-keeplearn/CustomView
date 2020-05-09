package com.al.custom_view.rating_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivityRatingViewBinding

class RatingViewActivity :AppCompatActivity(){
    lateinit var binding:ActivityRatingViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rating_view)
    }
}