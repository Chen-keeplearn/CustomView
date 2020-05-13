package com.al.custom_view.text_view_color_track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.al.custom_view.R
import com.al.custom_view.databinding.FragmentColorTrackTextViewBinding

class ColorTrackTextViewFragment : Fragment() {
    private lateinit var binding: FragmentColorTrackTextViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_color_track_text_view,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val title = bundle!!.getString("title")
        binding.fragmentColorTrackTextViewText.text = title
    }
}