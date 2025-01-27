package com.example.zauto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zauto.data.CarRepository
import com.example.zauto.ui.screen.detail.DetailViewModel
import com.example.zauto.ui.screen.favorite.FavoriteViewModel
import com.example.zauto.ui.screen.home.HomeViewModel
import com.example.zauto.ui.screen.list.CarListViewModel

class ViewModelFactory(private val repository: CarRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CarListViewModel::class.java)) {
            return CarListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}