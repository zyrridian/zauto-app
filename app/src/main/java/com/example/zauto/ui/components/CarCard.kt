package com.example.zauto.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.zauto.R
import com.example.zauto.ui.theme.ZautoTheme

@OptIn(ExperimentalLayoutApi::class)
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
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var favorite by remember { mutableStateOf(isFavorite) }
    Card(
        modifier = modifier.clickable { onClick() }
    ) {
        Column {
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.img_placeholder),
                    error = painterResource(R.drawable.img_error),
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .size(width = 240.dp, height = 140.dp)
                )
                IconButton(
                    onClick = {
                        favorite = !favorite
                        onFavoriteClick()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = if (favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (favorite) "Remove from favorites" else "Add to favorites",
                        tint = if (favorite) Color.Red else MaterialTheme.colorScheme.primary, // Icon color matches theme
                    )
                }
            }
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
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Chip(type)
                    Spacer(Modifier.width(4.dp))
                    Chip(fuelType)
//                    Spacer(Modifier.width(4.dp))
//                    Chip("$horsePower HP")
                }
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailContentPreview() {
    ZautoTheme {
        CarCard(
            brand = "Kia",
            model = "Sportage",
            year = 2023,
            type = "SUVfdfafdasfasfasfadfafasfsafsafasfas",
            fuelType = "Gasoline",
            image = "https://kiavietnam.com.vn/storage/kia-viet-nam/hinh-anh/suv-1.png",
            features = listOf(
                "Dual Panoramic Displays",
                "Smart Power Tailgate",
                "Heated Steering Wheel",
                "360-Degree Camera",
                "Wireless Phone Charger"
            ),
            horsePower = 203,
            price = 24000,
            onClick = { },
            isFavorite = true,
            onFavoriteClick = { }
        )
    }
}