package com.example.zauto.ui.screen.profile

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zauto.R
import com.example.zauto.ui.screen.detail.DetailContent
import com.example.zauto.ui.theme.ZautoTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.foto),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(100.dp))
                .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(100.dp))
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Rezky Aditia Fauzan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "rezkyaf246@gmail.com",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
        IconButton(
            onClick = {
                openExternalLink(context, "https://www.dicoding.com/users/zyrridian/academies")
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_outlined_open_in_new_24),
                contentDescription = "about_page"
            )
        }
    }
}

fun openExternalLink(context: Context, url: String) {
    val uri = Uri.parse(url)
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(context, uri)
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailContentPreview() {
    ZautoTheme {
        ProfileScreen()
    }
}