package com.al.custom_view.loading58;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.al.custom_view.R;
import com.al.custom_view.databinding.ActivityLoading58ViewBinding;
import com.al.custom_view.extension.ContextExtKt;

public class Loading58ViewActivity extends AppCompatActivity {
    private ActivityLoading58ViewBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_loading58_view);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_loading58_view);
        ContextExtKt.showToast("");
        binding.btnExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding.loadingView58.exchange();
            }
        });
    }
}
