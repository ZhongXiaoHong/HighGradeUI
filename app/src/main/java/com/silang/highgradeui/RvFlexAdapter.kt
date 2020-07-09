package com.silang.highgradeui

import android.content.Context
import android.media.Image
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class RvFlexAdapter(val items: List<Int>,val context:Context) : RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var iv = LayoutInflater.from(context).inflate(R.layout.rcflex_item, parent, false)
        return MyViewHolder(iv)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")

        ViewCompat.setBackground( holder.itemView,context.getDrawable(items[position]))
    }


}

class MyViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

}