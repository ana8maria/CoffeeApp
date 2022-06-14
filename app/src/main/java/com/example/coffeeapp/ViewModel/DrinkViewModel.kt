package com.example.coffeeapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.data.DrinkDatabase
import com.example.coffeeapp.repository.DrinkRepository
import com.example.coffeeapp.model.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DrinkViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Drink>>
    private val repository: DrinkRepository

    init{
        val drinkDao = DrinkDatabase.getDatabase(application).drinkDao()
        repository = DrinkRepository(drinkDao)
        readAllData = repository.readAllData
    }

    fun addDrink(drink: Drink){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDrink(drink)
        }
    }

    fun updateDrink(drink: Drink){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDrink(drink)
        }
    }

    fun deleteDrink(drink: Drink){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDrink(drink)
        }
    }

    fun deleteAllDrinks(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllDrinks()
        }
    }
}