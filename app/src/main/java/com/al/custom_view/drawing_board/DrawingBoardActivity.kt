package com.al.custom_view.drawing_board

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.al.custom_view.R
import com.al.custom_view.databinding.ActivityDrawingBoardBinding
import com.al.custom_view.extension.onClick
import kotlinx.android.synthetic.main.activity_drawing_board.*

class DrawingBoardActivity:AppCompatActivity() {
    lateinit var binding:ActivityDrawingBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawing_board)

        btnDrawingBoardClear.onClick {
            drawingBoardView.clear()
        }
    }
}