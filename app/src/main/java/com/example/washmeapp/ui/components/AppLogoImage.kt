package com.example.washmeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.core.graphics.toColorInt
import com.example.washmeapp.R

@Composable
fun AppLogoImage(width: Dp) {
        Image(
            painter = painterResource(id = R.drawable.washme_logo),
            contentDescription = "WashME Logo",
            colorFilter = ColorFilter.tint(Color("#1F58CA".toColorInt())),
            modifier = Modifier.width(width)
        )
}