package com.example.zauto.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zauto.di.Injection
import com.example.zauto.model.Car
import com.example.zauto.ui.ViewModelFactory
import com.example.zauto.ui.common.UiState
import com.example.zauto.ui.components.CarCard

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current)
        )
    ),
    navigateToDetail: (Int) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedFavorite()
            }

            is UiState.Success -> {
                FavoriteContent(
                    state = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteClick = { car ->
                        viewModel.addToFavorite(car)
                    },
                )
            }

            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteContent(
    state: FavoriteState,
    onFavoriteClick: (car: Car) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Favorite",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            items(state.cars, key = { it.id }) { data ->
                CarCard(
                    brand = data.brand,
                    model = data.model,
                    year = data.year,
                    type = data.type,
                    fuelType = data.fuelType,
                    image = data.images[0],
                    features = data.features,
                    horsePower = data.horsePower,
                    price = data.price,
                    isFavorite = data.isFavorite,
                    onClick = { navigateToDetail(data.id) },//onFavoriteButtonClicked(item.id, false) },
                    onFavoriteClick = { onFavoriteClick(data) },
                )
            }
        }
    }
}