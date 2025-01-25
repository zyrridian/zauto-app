package com.example.zauto.data

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.zauto.model.Car
import com.example.zauto.model.DummyDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class CarRepository(private val context: Context) {

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

//    fun getOrderRewardById(rewardId: Long): OrderReward {
//        return orderRewards.first {
//            it.reward.id == rewardId
//        }
//    }
//
//    fun updateOrderReward(rewardId: Long, newCountValue: Int): Flow<Boolean> {
//        val index = orderRewards.indexOfFirst { it.reward.id == rewardId }
//        val result = if (index >= 0) {
//            val orderReward = orderRewards[index]
//            orderRewards[index] =
//                orderReward.copy(reward = orderReward.reward, count = newCountValue)
//            true
//        } else {
//            false
//        }
//        return flowOf(result)
//    }
//
//    fun getAddedOrderRewards(): Flow<List<OrderReward>> {
//        return getAllRewards()
//            .map { orderRewards ->
//                orderRewards.filter { orderReward ->
//                    orderReward.count != 0
//                }
//            }
//    }

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