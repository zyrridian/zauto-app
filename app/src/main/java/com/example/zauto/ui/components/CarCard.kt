package com.example.zauto.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.zauto.R

@Composable
fun CarCard(
    image: String,
    brand: String,
    model: String,
    type: String,
    fuelType: String,
    horsePower: Int,
    year: Int,
    price: Int,
    features: List<String>,
    modifier: Modifier = Modifier
) {
    Card {
        Column {
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .padding(8.dp)
                    .size(width = 240.dp, height = 140.dp)
            )
            Column(
                modifier = Modifier.padding(
                    top = 8.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                    end = 12.dp
                )
            ) {
                Text(
                    text = "$brand $model ($year)",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(216.dp),
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "\$$price",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp),) {
                    Chip(type)
                    Chip(fuelType)
                    Chip("$horsePower HP")
                }
            }
        }
    }
}