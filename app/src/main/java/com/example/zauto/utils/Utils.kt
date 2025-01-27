package com.example.zauto.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

class Utils {
    fun openExternalLink(context: Context, url: String) {
        val uri = Uri.parse(url)
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, uri)
    }
}