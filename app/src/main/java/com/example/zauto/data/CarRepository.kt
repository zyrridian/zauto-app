package com.example.zauto.data

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.zauto.model.Car
import com.example.zauto.model.DummyDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class CarRepository(context: Context) {

    private val cars = mutableListOf<Car>()

    init {
        if (cars.isEmpty()) {
            DummyDataSource.loadDummyCars(context).forEach {
                cars.add(it)
            }
        }
    }

    fun getAllCars(): Flow<List<Car>> {
        return flowOf(cars)
    }

    fun getCarById(carId: Int): Car {
        return cars.first {
            it.id == carId
        }
    }

    fun searchCar(query: String): List<Car> {
        return cars.filter {
            it.brand.contains(query, ignoreCase = true)
        }
    }

    fun getAddedFavorite(): Flow<List<Car>> {
        return getAllCars()
            .map { cars ->
                cars.filter { car ->
                    car.isFavorite
                }
            }
    }

    fun updateFavoriteCar(carId: Int, isFavorite: Boolean): Flow<Boolean> {
        val index = cars.indexOfFirst { it.id == carId }
        val result = if (index >= 0) {
            val car = cars[index]
            cars[index] =
                car.copy(id = car.id, isFavorite = !isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: CarRepository? = null

        fun getInstance(context: Context): CarRepository =
            instance ?: synchronized(this) {
                CarRepository(context).apply {
                    instance = this
                }
            }
    }

}