package com.al.custom_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.al.custom_view.databinding.ActivityMainBinding
import com.al.custom_view.extension.onClick
import com.al.custom_view.extension.startActivity
import com.al.custom_view.loading58.Loading58ViewActivity
import com.al.custom_view.loading_view.LoadingViewActivity
import com.al.custom_view.path_menu.PathMenuActivity
import com.al.custom_view.rating_view.RatingViewActivity
import com.al.custom_view.step_view.QQStepViewActivity
import com.al.custom_view.utils.ToastUtil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnMenu.setOnClickListener {
            startActivity(PathMenuActivity::class.java)
        }
        binding.btnLoadingView.setOnClickListener {
            startActivity(LoadingViewActivity::class.java)
        }
        binding.btnRatingView.setOnClickListener {
            startActivity(RatingViewActivity::class.java)
        }
        binding.btnQQStepView.setOnClickListener {
            startActivity(QQStepViewActivity::class.java)
        }
        binding.btnLoading58.onClick {
            ToastUtil.showMsg("")
            startActivity(Loading58ViewActivity::class.java)
        }
    }
}
