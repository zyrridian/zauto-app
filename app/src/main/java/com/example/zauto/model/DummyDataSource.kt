package com.example.zauto.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DummyDataSource {
    fun loadDummyCars(context: Context): List<Car> {
        val json = context.assets.open("dummy.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val type = object : TypeToken<Cars>() {}.type
        val cars: Cars = gson.fromJson(json, type)
        return cars.cars
    }
}