package com.example.zauto.di

import android.content.Context
import com.example.zauto.data.CarRepository

object Injection {
    fun provideRepository(context: Context): CarRepository {
        return CarRepository.getInstance(context)
    }
}