package com.multishare.file

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getResponsiveDimension(): Dp {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    return when {
        screenWidth < 360.dp -> 80.dp // Small devices
        screenWidth < 600.dp -> 100.dp // Medium devices
        else -> 120.dp // Large devices
    }
}