package com.example.coffeeapp

import com.example.coffeeapp.model.Drink
import com.example.coffeeapp.model.MenuBar
import org.koin.dsl.module

val appModule = module {
    // Defines a singleton of Drinks
   single { Drinks() }

    // Creates a new instance every time
   factory { Food() }

    factory { OurMenu(get(), get()) }
}