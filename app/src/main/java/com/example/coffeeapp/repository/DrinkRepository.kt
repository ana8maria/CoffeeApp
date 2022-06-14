package com.example.coffeeapp.repository

import androidx.lifecycle.LiveData
import com.example.coffeeapp.data.DrinkDao
import com.example.coffeeapp.model.Drink

class DrinkRepository(private val drinkDao: DrinkDao) {

    val readAllData: LiveData<List<Drink>> = drinkDao.readAllData()

    suspend fun addDrink(drink: Drink){
        drinkDao.addDrink(drink)
    }

    suspend fun updateDrink(drink: Drink){
        drinkDao.updateDrink(drink)
    }

    suspend fun deleteDrink(drink: Drink){
        drinkDao.deleteDrink(drink)
    }

    suspend fun deleteAllDrinks(){
        drinkDao.deleteAllDrinks()
    }
}