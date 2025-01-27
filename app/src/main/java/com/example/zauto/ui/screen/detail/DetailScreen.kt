package com.example.zauto.ui.screen.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.zauto.R
import com.example.zauto.di.Injection
import com.example.zauto.ui.ViewModelFactory
import com.example.zauto.ui.common.UiState
import com.example.zauto.ui.screen.detail.components.SpecItem
import com.example.zauto.ui.screen.profile.openExternalLink
import com.example.zauto.ui.theme.ZautoTheme

@Composable
fun DetailScreen(
    carId: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current)
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCarById(carId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.id,
                    data.brand,
                    data.model,
                    data.year,
                    data.type,
                    data.fuelType,
                    data.seat,
                    data.transmission,
                    data.specifications,
                    data.price,
                    data.features,
                    data.images,
                    data.isFavorite,
                    onBackClick = navigateBack,
                    onAddToFavorite = { viewModel.addToFavorite(data) }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DetailContent(
    id: Int,
    brand: String,
    model: String,
    year: Int,
    type: String,
    fuelType: String,
    seat: Int,
    transmission: String,
    specifications: String,
    price: Int,
    features: List<String>,
    images: List<String>,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onAddToFavorite: (isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current
    var selectedImageIndex by remember { mutableStateOf(0) }
    var favorite by remember { mutableStateOf(isFavorite) }

    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        favorite = !favorite
                        onAddToFavorite(favorite)
                    }) {
                        Icon(
                            imageVector = if (favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (favorite) "Remove from favorites" else "Add to favorites",
                            tint = if (favorite) Color.Red else MaterialTheme.colorScheme.primary, // Icon color matches theme
                        )
                    }
                },
            )

            AsyncImage(
                model = images[selectedImageIndex],
                contentDescription = "Car Image",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(images) { index, image ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(0.5.dp, Color.LightGray, RoundedCornerShape(12.dp))
                            .clickable {
                                selectedImageIndex = index
                            }
                    ) {
                        AsyncImage(
                            model = image,
                            contentDescription = "Car Image",
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.img_placeholder),
                            error = painterResource(id = R.drawable.img_error),
                            modifier = Modifier.fillMaxSize()
                        )
                        if (index == selectedImageIndex) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "$brand $model ($year)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SpecItem(title = type, icon = R.drawable.ic_outline_car_24)
                    SpecItem(title = fuelType, icon = R.drawable.ic_outline_fuel_24)
                    SpecItem(title = "$seat Seats", icon = R.drawable.ic_outline_seat_24)
                    SpecItem(title = transmission, icon = R.drawable.ic_outline_transmission_24)
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Specifications",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                Text(
                    text = specifications,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Features",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    features.forEach { feature ->
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 0.5.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(text = feature, fontSize = 14.sp, fontWeight = FontWeight.Light)
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                val searchQuery = "$brand $model $year buy"
                val searchUrl = "https://www.google.com/search?q=$searchQuery"
                openExternalLink(context, searchUrl)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Buy this car NOW! (\$$price)"
            )
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailContentPreview() {
    ZautoTheme {
        DetailContent(
            id = 1,
            brand = "Kia",
            model = "Sportage",
            year = 2023,
            type = "SUV",
            fuelType = "Gasoline",
            seat = 5,
            transmission = "8-Speed Automatic",
            specifications = """
                • Brand: Kia
                • Model: Sportage
                • Year: 2023
                • Body Type: SUV
                • Engine: 2.5L Inline-4
                • Transmission: 8-speed automatic
                • Drive Type: Front-Wheel Drive (FWD) or All-Wheel Drive (AWD)
                • Fuel Type: Petrol
                • Horsepower: 187 hp @ 6,100 rpm
                • Torque: 178 lb-ft @ 4,000 rpm
                • Seating Capacity: 5
            """.trimIndent(),
            price = 12000,
            features = listOf(
                "Dual Panoramic Displays",
                "Smart Power Tailgate",
                "Heated Steering Wheel",
                "360-Degree Camera",
                "Wireless Phone Charger"
            ),
            images = listOf(
                "https://kiavietnam.com.vn/storage/kia-viet-nam/hinh-anh/suv-1.png",
                "https://www.blackxperience.com/assets/content/blackauto/autonews/kia-sportage-2022-5.jpg",
                "https://hips.hearstapps.com/hmg-prod/images/2026-kia-sportage-pr-114-673f7ae56c057.jpg?crop=0.796xw:0.673xh;0.103xw,0.142xh&resize=2048:*",
                "https://www.kia.com/content/dam/kwcms/au/en/images/showroom/sportage/features/kia-sportage-features-design-rear.jpg"
            ),
            isFavorite = true,
            onBackClick = {},
            onAddToFavorite = {}
        )
    }
}