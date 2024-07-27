package com.example.listatelefonica.database

import com.example.listatelefonica.model.ContactModel

interface iContactDAO {

    fun insert(contact: ContactModel):Boolean

    fun update(contact: ContactModel):Boolean

    fun remove(id: Int):Boolean

    fun findById(id: Int): ContactModel

    fun findAll():ArrayList<ContactModel>
}