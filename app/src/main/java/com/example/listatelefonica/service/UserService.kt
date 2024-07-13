package com.example.listatelefonica.service

import android.content.Context
import com.example.listatelefonica.database.UserDAO
import com.example.listatelefonica.model.UserModel

class UserService(context: Context) {

    private val userDao = UserDAO(context)

    fun login(username:String, password:String):Boolean{
        val user = userDao.findUserByUsernameAndPassword(username,password)
        if(user != null){
            return true
        }
        return false
    }


}