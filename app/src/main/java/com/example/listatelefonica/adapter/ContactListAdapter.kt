package com.example.listatelefonica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listatelefonica.R
import com.example.listatelefonica.adapter.listener.ContactOnClickListener
import com.example.listatelefonica.adapter.viewHolder.ContactViewHolder
import com.example.listatelefonica.model.ContactModel

class ContactListAdapter(
    private val contactList: List<ContactModel>,
    private val contactOnClickListener: ContactOnClickListener
): RecyclerView.Adapter<ContactViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.textName.text = contact.name
        holder.textPhone.text = contact.phone
        val imageResource = when (contact.imageId) {
            "1" -> R.drawable.male
            "2" -> R.drawable.female
            else -> R.drawable.male
        }
        holder.imagem.setImageResource(imageResource)
        holder.itemView.setOnClickListener {
            contactOnClickListener.clickListener(contact)
        }

    }
}