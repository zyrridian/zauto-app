package com.example.zauto.model

import com.example.zauto.R

data class BrandInfo(val text: String, val image: Int, val url: String)

val brands = listOf(
    BrandInfo("Audi", R.drawable.audi, "https://www.audi.com/"),
    BrandInfo("Mercedes", R.drawable.mercedes, "https://www.mercedes-benz.com/"),
    BrandInfo("Honda", R.drawable.honda, "https://www.honda.com/"),
    BrandInfo("Nissan", R.drawable.nissan, "https://www.nissan-global.com/"),
    BrandInfo("Toyota", R.drawable.toyota, "https://www.toyota.com/")
)
