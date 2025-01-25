package com.example.zauto.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zauto.data.CarRepository
import com.example.zauto.model.Car
import com.example.zauto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CarRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Car>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Car>>
        get() = _uiState

    fun getCarById(carId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCarById(carId))
        }
    }

//    fun addToCart(car: Car, count: Int) {
//        viewModelScope.launch {
//            repository.updateOrderReward(reward.id, count)
//        }
//    }
}