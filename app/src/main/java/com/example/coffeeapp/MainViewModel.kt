package com.example.coffeeapp

import androidx.lifecycle.ViewModel
import com.example.coffeeapp.model.Drink
import com.example.coffeeapp.model.MenuBar

class OurMenu(val drink: Drinks, val food: Food) {

    fun getOrder(){
        drink.getDrink()
        food.getFood()
    }
}

class Drinks{
    fun getDrink(){
        println("Get your drink from here")
    }
}

class Food{
    fun getFood(){
        println("Get your food from here")
    }
}
