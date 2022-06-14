package com.example.coffeeapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.coffeeapp.model.Drink

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDrink(drink: Drink)
    
    @Update
    suspend fun updateDrink(drink:Drink)

    @Delete
    suspend fun deleteDrink(drink: Drink)

    @Query("DELETE FROM drinks")
    suspend fun deleteAllDrinks()

    @Query("SELECT * FROM drinks ORDER BY id ASC")
    fun readAllData(): LiveData<List<Drink>>
}