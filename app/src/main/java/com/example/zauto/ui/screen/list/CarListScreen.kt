package com.example.zauto.ui.screen.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zauto.di.Injection
import com.example.zauto.model.Car
import com.example.zauto.ui.ViewModelFactory
import com.example.zauto.ui.common.UiState
import com.example.zauto.ui.components.CarCard
import com.example.zauto.ui.screen.detail.DetailContent
import com.example.zauto.ui.screen.home.HomeContent
import com.example.zauto.ui.screen.home.HomeViewModel
import com.example.zauto.ui.theme.ZautoTheme

@Composable
fun CarListScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current)
        )
    ),
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCars()
            }

            is UiState.Success -> {
                CarListContent(
                    cars = uiState.data,
                    onBackClick = {},
                    modifier = modifier
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListContent(
    cars: List<Car>,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") } // State for search input

    val filteredCars = cars.filter {
        it.brand.contains(searchQuery, ignoreCase = true) ||
                it.model.contains(searchQuery, ignoreCase = true) ||
                it.year.toString().contains(searchQuery, ignoreCase = true)
    } // Filter cars based on query

    Column {
        TopAppBar(
            title = {
                Text("Car List")
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
        Spacer(Modifier.height(16.dp))
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onClearClick = { searchQuery = "" },
            modifier = Modifier.padding(16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            items(filteredCars) { data ->
                CarCard(
                    image = data.images[0],
                    brand = data.brand,
                    model = data.model,
                    type = data.type,
                    fuelType = data.fuelType,
                    horsePower = data.horsePower,
                    year = data.year,
                    price = data.price,
                    features = data.features,
                    onClick = { } //navigateToDetail(data.id)
                )
            }

        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search here...") },
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(Icons.Default.Close, contentDescription = "Clear Search")
                }
            }
        },
        shape = RoundedCornerShape(12.dp), // Softer, more modern rounded edges
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CarListContentPreview() {
    ZautoTheme {
        CarListContent(
            listOf(
                Car(
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
                    id = 1,
                    price = 11111,
                    engineType = "GG",
                    horsePower = 69
                ),
                Car(
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
                    id = 1,
                    price = 11111,
                    engineType = "GG",
                    horsePower = 69
                ),
                Car(
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
                    id = 1,
                    price = 11111,
                    engineType = "GG",
                    horsePower = 69
                ),
                Car(
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
                    id = 1,
                    price = 11111,
                    engineType = "GG",
                    horsePower = 69
                ),
                Car(
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
                    id = 1,
                    price = 11111,
                    engineType = "GG",
                    horsePower = 69
                )
            ),
            onBackClick = {},
        )
    }
}