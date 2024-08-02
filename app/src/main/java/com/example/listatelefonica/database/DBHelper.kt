package com.example.listatelefonica.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.sql.SQLException

class DBHelper(context: Context) : SQLiteOpenHelper(context, "contact.db", null, 1) {
    override fun onCreate(database: SQLiteDatabase?) {

        val sql = arrayOf(
            "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",
           "INSERT INTO users (username,password) VALUES ('admin', 'admin')",
            "CREATE TABLE contact (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,address TEXT,email TEXT, phone TEXT,imgUrl TEXT)",
            "INSERT INTO contact (name,address,email,phone,imgUrl) VALUES ('Contato Teste', 'Endereco Teste', 'teste@gmail.com','123123','testeurl')"
        )


        try {
            sql.forEach { eachSql ->
                database?.execSQL(eachSql)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.i("info_db", "Erro ao executar a query")
        }

    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }
}