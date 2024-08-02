package com.example.listatelefonica.model

data class ContactModel(
    val id: Int = 0,
    var name:String = "",
    var address:String = "",
    var email:String ="",
    var phone:String = "",
    var imageId:String = ""
)
