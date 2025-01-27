package com.example.zauto.ui.screen.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zauto.data.CarRepository
import com.example.zauto.model.Car
import com.example.zauto.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CarListViewModel(
    private val repository: CarRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Car>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Car>>>
        get() = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    init {
        getAllCars()
    }

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

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.getAllCars()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { cars ->
                    val filteredCars = cars.filter {
                        it.brand.contains(newQuery, ignoreCase = true) ||
                                it.model.contains(newQuery, ignoreCase = true)
                    }
                    _uiState.value = UiState.Success(filteredCars)
                }
        }
    }

//    fun search(newQuery: String) {
//        _query.value = newQuery
//        _groupedHeroes.value = repository.searchCar(_query.value)
//            .sortedBy { it.brand }
//            .groupBy { it.name[0] }
//    }

}