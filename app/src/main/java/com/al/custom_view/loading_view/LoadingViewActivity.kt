package com.al.custom_view.loading_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.al.base.dialog.ALDialog
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivityLoadingViewBinding

class LoadingViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoadingViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading_view)

        binding.btnDialog.setOnClickListener {
            ALDialog.Builder(this, R.style.DialogTheme)
                .setContentView(R.layout.dialog_layout)
                .setHeight(100)
                .setWidth(100)
                .setCancelable(false)
                .create().show()
        }
    }
}