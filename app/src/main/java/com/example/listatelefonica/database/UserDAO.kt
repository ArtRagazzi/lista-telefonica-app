package com.example.listatelefonica.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.listatelefonica.model.UserModel
import java.sql.SQLException

class UserDAO(context: Context) : IUserDAO {


    private val writeCommands = DBHelper(context).writableDatabase
    private val readCommands = DBHelper(context).readableDatabase


    override fun insert(userModel: UserModel): Boolean {

        //Verifica se o usuario ja existe
        if(findUserByUsername(userModel.username)!=null){
            Log.i("info_db", "Erro ao executar insert, Usuario ja existente")
            return false
        }

        //Cria um Objeto chave valor
        val value = ContentValues()
        value.put("username", userModel.username)
        value.put("password", userModel.password)

        try {
            writeCommands.insert("users", null, value)
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao executar insert")
            return false
        }
        return true
    }

    override fun update(userModel: UserModel): Boolean {
        val value = ContentValues()
        value.put("username", userModel.username)
        value.put("password", userModel.password)

        val oldUserId = arrayOf(userModel.id.toString())

        try {
            //Substitui ? pelo parametro oldUSerId
            writeCommands.update("users", value, "id = ?", oldUserId)
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao executar Update")
            return false
        }
        return true
    }

    override fun remove(id: Int): Boolean {
        val idUser = arrayOf(id.toString())

        try {
            writeCommands.delete("users", "id = ?", idUser)
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao executar Delete")
            return false
        }
        return true
    }

    override fun findById(id: Int): UserModel {
        val sql = "SELECT * FROM users WHERE id = ?"
        val cursor = readCommands.rawQuery(sql, arrayOf(id.toString()))

        return try{
            if(cursor.moveToFirst()) {
                val iId = cursor.getColumnIndex("id")
                val iUsername = cursor.getColumnIndex("username")
                val iPassword = cursor.getColumnIndex("password")

                val idUser = cursor.getInt(iId)
                val username = cursor.getString(iUsername)
                val password = cursor.getString(iPassword)

                UserModel(idUser,username,password)
            }else{
                throw SQLException("Usuario não encontrado")
            }
        }finally {
            cursor.close()
        }
    }

    override fun findAll(): List<UserModel> {
        val userList = mutableListOf<UserModel>()
        val sql = "SELECT * FROM users;"
        val cursor = readCommands.rawQuery(sql, null)

        val iId = cursor.getColumnIndex("id")
        val iUsername = cursor.getColumnIndex("username")
        val iPassword = cursor.getColumnIndex("password")

        while (cursor.moveToNext()) {
            val idUser = cursor.getInt(iId)
            val username = cursor.getString(iUsername)
            val password = cursor.getString(iPassword)
            userList.add(UserModel(idUser,username,password))
        }
        cursor.close()
        return userList
    }

    //Metodos Para Login

    fun findUserByUsernameAndPassword(username:String, password:String): UserModel? {
        val sql = "SELECT * FROM users WHERE username = ? AND password = ?"
        val cursor = readCommands.rawQuery(sql, arrayOf(username,password))

        return try{
            if(cursor.moveToFirst()) {
                val iId = cursor.getColumnIndex("id")
                val iUsername = cursor.getColumnIndex("username")
                val iPassword = cursor.getColumnIndex("password")

                val idUser = cursor.getInt(iId)
                val username = cursor.getString(iUsername)
                val password = cursor.getString(iPassword)

                UserModel(idUser,username,password)
            }else{
                return null
            }
        }finally {
            cursor.close()
        }
    }

    fun findUserByUsername(username:String): UserModel? {
        val sql = "SELECT * FROM users WHERE username = ?"
        val cursor = readCommands.rawQuery(sql, arrayOf(username))

        return try{
            if(cursor.moveToFirst()) {
                val iId = cursor.getColumnIndex("id")
                val iUsername = cursor.getColumnIndex("username")
                val iPassword = cursor.getColumnIndex("password")

                val idUser = cursor.getInt(iId)
                val username = cursor.getString(iUsername)
                val password = cursor.getString(iPassword)

                UserModel(idUser,username,password)
            }else{
                return null
            }
        }finally {
            cursor.close()
        }
    }

}