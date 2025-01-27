package com.example.zauto.ui.screen.favorite

import com.example.zauto.model.Car

data class FavoriteState(
    val cars: List<Car>,
    val isFavorite: Boolean
)
