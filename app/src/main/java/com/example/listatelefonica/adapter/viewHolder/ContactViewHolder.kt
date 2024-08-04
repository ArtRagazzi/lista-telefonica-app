package com.example.listatelefonica.adapter.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listatelefonica.R


class ContactViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val imagem:ImageView = view.findViewById(R.id.ivItemContact)
    var textName: TextView = view.findViewById(R.id.tvItemName)
    val textPhone:TextView = view.findViewById(R.id.tvItemPhone)
}
