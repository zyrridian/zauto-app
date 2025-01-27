package com.example.zauto.ui.screen.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zauto.R

@Composable
fun SpecItem(
    title: String,
    icon: Int,
    modifier: Modifier = Modifier
) {
    Row {
        Icon(painter = painterResource(icon), contentDescription = null)
        Spacer(Modifier.width(4.dp))
        Text(text = title, fontSize = 16.sp)
    }
}