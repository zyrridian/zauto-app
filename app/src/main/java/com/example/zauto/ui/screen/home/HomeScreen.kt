package com.example.zauto.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zauto.R
import com.example.zauto.ui.components.Banner
import com.example.zauto.ui.components.Brand
import com.example.zauto.ui.components.CarCard
import com.example.zauto.ui.components.CustomIconButton
import com.example.zauto.ui.components.HomeSearchBar
import com.example.zauto.ui.components.LocationButton
import com.example.zauto.ui.components.SectionHeader

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
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
                CustomIconButton(icon = Icons.Default.Info, onClick = {})
            }
            Spacer(modifier = Modifier.height(16.dp))
            HomeSearchBar()
        }

        // Body
        Column(
            modifier = Modifier
        ) {
            // Brands
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(title = "Brands", onClick = {})
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Brand(text = "Audi", image = R.drawable.audi)
                Brand(text = "Mercedes", image = R.drawable.mercedes)
                Brand(text = "Honda", image = R.drawable.honda)
                Brand(text = "Nissan", image = R.drawable.nissan)
                Brand(text = "Toyota", image = R.drawable.toyota)
            }

            // Cars
            Spacer(modifier = Modifier.height(16.dp))
            SectionHeader(title = "New Arrival", onClick = {})
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(3) {
                    CarCard()
                }
            }

            // Banner
            Spacer(modifier = Modifier.height(20.dp))
            Banner()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}