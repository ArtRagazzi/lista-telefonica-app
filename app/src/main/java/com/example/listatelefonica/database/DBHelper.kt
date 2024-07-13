package com.example.listatelefonica.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

class DBHelper(context: Context) : SQLiteOpenHelper(context, "contact.db", null, 1) {
    override fun onCreate(database: SQLiteDatabase?) {

        val sql = arrayOf(
            "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)",
            "INSERT INTO users (username,password) VALUES ('admin', 'admin')"
        )

        try {
            sql.forEach { eachSql->
                database?.execSQL(eachSql)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao executar a query")
        }

    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}