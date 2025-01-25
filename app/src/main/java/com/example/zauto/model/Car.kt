package com.example.zauto.model

data class Cars(
    val cars: List<Car>
)

data class Car(
    val id: Int,
    val brand: String,
    val model: String,
    val year: Int,
    val type: String,
    val price: Int,
    val seat: Int,
    val transmission: String,
    val engineType: String,
    val horsePower: Int,
    val fuelType: String,
    val specifications: String,
    val features: List<String>,
    val images: List<String>
)