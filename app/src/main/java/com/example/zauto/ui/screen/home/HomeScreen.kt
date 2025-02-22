package com.example.zauto.ui.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zauto.R
import com.example.zauto.ZautoApp
import com.example.zauto.di.Injection
import com.example.zauto.model.Car
import com.example.zauto.model.DummyDataSource.loadDummyCars
import com.example.zauto.model.brands
import com.example.zauto.ui.ViewModelFactory
import com.example.zauto.ui.common.UiState
import com.example.zauto.ui.components.Banner
import com.example.zauto.ui.components.Brand
import com.example.zauto.ui.components.CarCard
import com.example.zauto.ui.components.CustomIconButton
import com.example.zauto.ui.components.HomeSearchBar
import com.example.zauto.ui.components.LocationButton
import com.example.zauto.ui.components.SectionHeader
import com.example.zauto.ui.screen.profile.openExternalLink
import com.example.zauto.ui.theme.ZautoTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current)
        )
    ),
    navigateToCarList: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getLimitedCars()
            }

            is UiState.Success -> {
                val data = uiState.data
                HomeContent(
                    cars = data,
                    navigateToCarList = navigateToCarList,
                    navigateToDetail = navigateToDetail,
                    onFavoriteClick = { car ->
                        viewModel.addToFavorite(car)
                    },
                    modifier = modifier
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    cars: List<Car>,
    navigateToCarList: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    onFavoriteClick: (car: Car) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        // Header
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 16.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Location",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LocationButton()
                }
                CustomIconButton(
                    icon = Icons.Default.Info,
                    onClick = {
                        openExternalLink(
                            context,
                            "https://github.com/zyrridian/zauto-app"
                        )
                    })
            }
            Spacer(modifier = Modifier.height(16.dp))
            HomeSearchBar(modifier = Modifier.clickable { navigateToCarList() })
        }

        // Body
        Column(
            modifier = Modifier
        ) {
            // Brands
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(title = "Brands", onClick = {}, displayShowAll = false)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                brands.forEach { brand ->
                    Brand(
                        text = brand.text,
                        image = brand.image,
                        modifier = Modifier
                            .clickable {
                                openExternalLink(context, brand.url)
                            }
                    )
                }
            }

            // Cars
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(title = "New Arrival", onClick = { navigateToCarList() })
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(cars) { data ->
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
                        isFavorite = data.isFavorite,
                        onClick = { navigateToDetail(data.id) },
                        onFavoriteClick = { onFavoriteClick(data) }
                    )
                }
            }

            // Banner
            Spacer(modifier = Modifier.height(20.dp))
            Banner()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    ZautoTheme {
        HomeContent(
            cars = loadDummyCars(LocalContext.current),
            navigateToCarList = {},
            navigateToDetail = {},
            onFavoriteClick = {},
        )
    }
}
