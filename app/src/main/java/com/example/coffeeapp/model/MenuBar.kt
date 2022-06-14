package com.example.coffeeapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.StringBuilder

@Entity(tableName = "menu_bar")
class MenuBar {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id:Int = 0

    @ColumnInfo(name = "title")
    var title:String?=null

    @ColumnInfo(name = "description")
    var description:String?=null

    @ColumnInfo(name = "price")
    var price:String?=null

    @ColumnInfo(name = "image")
    var imageNo:Int = 0

    constructor(){
    }

    override fun toString(): String {
        return StringBuilder(title)
            .append("\n")
            .append(description)
            .append(price)
            .append(imageNo)
            .toString()
    }

//    fun getFood(){
//        println("your food is: " + title + " and costs " + price);
//
//    }
}