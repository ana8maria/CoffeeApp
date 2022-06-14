package com.example.coffeeapp.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName= "drinks")
data class Drink (

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val price: String//,
  //  val photo: Bitmap
): Parcelable//{

//    init{}
//    fun getDrink(){
//        println("your drink is: " + title + " and costs " + price);
//
//    }
//}