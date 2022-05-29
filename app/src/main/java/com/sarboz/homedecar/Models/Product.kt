package com.sarboz.homedecar.Models

import com.google.firebase.database.IgnoreExtraProperties


data class Product(
    var name:String?="",
    var pictureUrl:String?="",
    var detail:String?="",
    var modelUrl:String?="",
    var price:String?=""
)

