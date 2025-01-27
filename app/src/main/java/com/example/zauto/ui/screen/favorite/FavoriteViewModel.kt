package com.example.zauto.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zauto.data.CarRepository
import com.example.zauto.model.Car
import com.example.zauto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: CarRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteState>>
        get() = _uiState

    fun getAddedFavorite() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedFavorite()
                .collect { car ->
                    val isFavorite = true
                    _uiState.value = UiState.Success(FavoriteState(car, isFavorite))
                }
        }
    }

    fun updateOrderReward(carId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateFavoriteCar(carId, isFavorite)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedFavorite()
                    }
                }
        }
    }
}