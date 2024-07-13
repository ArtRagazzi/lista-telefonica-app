package com.example.listatelefonica.database

import com.example.listatelefonica.model.UserModel

interface IUserDAO {


    fun insert(userModel: UserModel):Boolean

    fun update(userModel: UserModel):Boolean

    fun remove(id: Int):Boolean

    fun findById(id: Int):UserModel

    fun findAll():List<UserModel>
}