package com.example.coffeeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.coffeeapp.Converters
import com.example.coffeeapp.model.Drink

@Database(entities = [Drink::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class DrinkDatabase: RoomDatabase() {

    abstract fun drinkDao(): DrinkDao

    companion object{
        @Volatile
        private var INSTANCE: DrinkDatabase? = null

        fun getDatabase(context: Context):DrinkDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrinkDatabase::class.java,
                    "drinks_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}