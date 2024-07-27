package com.example.listatelefonica.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.listatelefonica.model.ContactModel
import com.example.listatelefonica.model.UserModel
import java.sql.SQLException

class ContactDAO(context: Context) : iContactDAO{

    private val writeCommands = DBHelper(context).writableDatabase
    private val readCommands = DBHelper(context).readableDatabase

    override fun insert(contact: ContactModel): Boolean {

        val value = ContentValues()
        value.put("name", contact.name)
        value.put("address", contact.address)
        value.put("email", contact.email)
        value.put("phone", contact.phone)
        value.put("imgUrl", contact.imageId)

        try {
            writeCommands.insert("contact", null, value)
        }catch (e:SQLException){
            e.printStackTrace()
            Log.i("info_db", "Erro ao executar insert")
            return false
        }
        return true
    }

    override fun update(contact: ContactModel): Boolean {
        val value = ContentValues()
        value.put("name", contact.name)
        value.put("address", contact.address)
        value.put("email", contact.email)
        value.put("phone", contact.phone)
        value.put("imgUrl", contact.imageId)

        try {
            writeCommands.update("contact", value, "id = ?", arrayOf(contact.id.toString()))
        }catch (e:SQLException){
            e.printStackTrace()
            Log.i("info_db", "Erro ao executar Update")
            return false
        }
        return true
    }

    override fun remove(id: Int): Boolean {
        val idContact = arrayOf(id.toString())

        try {
            writeCommands.delete("contact", "id = ?", idContact)
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao executar Delete")
            return false
        }
        return true
    }

    override fun findById(id: Int): ContactModel {
        val sql = "SELECT * FROM contact WHERE id = ?"
        val cursor = readCommands.rawQuery(sql, arrayOf(id.toString()))

        return try{
            if(cursor.moveToFirst()) {
                val iId = cursor.getColumnIndex("id")
                val iName = cursor.getColumnIndex("name")
                val iAddress = cursor.getColumnIndex("address")
                val iEmail = cursor.getColumnIndex("email")
                val iPhone = cursor.getColumnIndex("phone")
                val iImgUrl = cursor.getColumnIndex("imgUrl")

                val idContact = cursor.getInt(iId)
                val nameContact = cursor.getString(iName)
                val addressContact = cursor.getString(iAddress)
                val emailContact = cursor.getString(iEmail)
                val phoneContact = cursor.getInt(iPhone)
                val imgContact = cursor.getString(iImgUrl)
                ContactModel(idContact,nameContact,addressContact,emailContact,phoneContact,imgContact)
            }else{
                throw SQLException("Contact not Find")
            }
        }finally {
            cursor.close()
        }
    }

    override fun findAll(): ArrayList<ContactModel> {
        val contactList = arrayListOf<ContactModel>()
        val sql = "SELECT * FROM contact;"
        val cursor = readCommands.rawQuery(sql, null)

        val iId = cursor.getColumnIndex("id")
        val iName = cursor.getColumnIndex("name")
        val iAddress = cursor.getColumnIndex("address")
        val iEmail = cursor.getColumnIndex("email")
        val iPhone = cursor.getColumnIndex("phone")
        val iImgUrl = cursor.getColumnIndex("imgUrl")

        while (cursor.moveToNext()) {
            val idContact = cursor.getInt(iId)
            val nameContact = cursor.getString(iName)
            val addressContact = cursor.getString(iAddress)
            val emailContact = cursor.getString(iEmail)
            val phoneContact = cursor.getInt(iPhone)
            val imgContact = cursor.getString(iImgUrl)
            contactList.add(ContactModel(idContact,nameContact,addressContact,emailContact,phoneContact,imgContact))
        }
        cursor.close()
        return contactList
    }

}