package com.multishare

import android.os.Environment
import android.os.StatFs
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay


@Composable
fun SplashScreenContent() {
    var showMainScreen by remember { mutableStateOf(false) }

    // Launch effect to wait for the splash screen
    LaunchedEffect(key1 = true) {
        delay(3000L) // 3-second delay for the splash screen
        showMainScreen = true
    }

    if (showMainScreen) {
        MainContent(internalStorageInfo = getInternalStorageInfo())
    } else {
        SplashScreen()
    }
}

@Composable
fun SplashScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 1.5f
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MainContent(internalStorageInfo: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(internalStorageInfo, fontSize = 22.sp)
    }
}

fun getInternalStorageInfo(): String {
    val iPath = Environment.getDataDirectory()
    val iStat = StatFs(iPath.path)
    val iBlockSize = iStat.blockSizeLong
    val iAvailableBlocks = iStat.availableBlocksLong
    val iTotalBlocks = iStat.blockCountLong
    val iAvailableSpace = formatSize(iAvailableBlocks * iBlockSize)
    val iTotalSpace = formatSize(iTotalBlocks * iBlockSize)
    return "Internal Available: $iAvailableSpace\nInternal Total: $iTotalSpace"
}

private fun formatSize(size: Long): String {
    var sizeInBytes = size.toDouble()
    var suffix = "B" // Default suffix

    when {
        sizeInBytes >= 1024 * 1024 * 1024 -> { // GB
            sizeInBytes /= (1024 * 1024 * 1024)
            suffix = "GB"
        }
        sizeInBytes >= 1024 * 1024 -> { // MB
            sizeInBytes /= (1024 * 1024)
            suffix = "MB"
        }
        sizeInBytes >= 1024 -> { // KB
            sizeInBytes /= 1024
            suffix = "KB"
        }
    }

    // Format to one decimal place
    return String.format("%.1f %s", sizeInBytes, suffix)}
