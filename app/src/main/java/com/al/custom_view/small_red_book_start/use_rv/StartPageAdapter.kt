package com.al.custom_view.small_red_book_start.use_rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.al.custom_view.R


class StartPageAdapter : RecyclerView.Adapter<StartPageAdapter.StartPageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartPageHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_start_page,parent,false)
        return StartPageHolder(itemView)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: StartPageHolder, position: Int) {

    }


    class StartPageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv:ImageView = itemView.findViewById(R.id.itemStartPageIv)

    }
}