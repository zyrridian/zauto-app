package com.example.zauto.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zauto.data.CarRepository
import com.example.zauto.model.Car
import com.example.zauto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CarRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Car>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Car>>>
        get() = _uiState

    fun getAllCars() {
        viewModelScope.launch {
            repository.getAllCars()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { cars ->
                    _uiState.value = UiState.Success(cars)
                }
        }
    }

    fun getLimitedCars(limit: Int = 5) {
        viewModelScope.launch {
            repository.getLimitedCars()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { cars ->
                    _uiState.value = UiState.Success(cars)
                }
        }
    }

    fun addToFavorite(car: Car) {
        viewModelScope.launch {
            repository.updateFavoriteCar(
                carId = car.id,
                isFavorite = car.isFavorite
            )
        }
    }
}