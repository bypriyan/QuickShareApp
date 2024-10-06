package com.multishare.file

<<<<<<< HEAD:app/src/main/java/com/multishare/file/Splash_Screen_Compose.kt
import StorageUsageScreen

import androidx.compose.foundation.layout.Arrangement
=======
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
>>>>>>> 22f131bda8fd66212fd1f8e610b1ef3e1fc1bbdb:app/src/main/java/com/multishare/MainActivity.kt
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
<<<<<<< HEAD:app/src/main/java/com/multishare/file/Splash_Screen_Compose.kt
=======
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
>>>>>>> 22f131bda8fd66212fd1f8e610b1ef3e1fc1bbdb:app/src/main/java/com/multishare/MainActivity.kt
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
<<<<<<< HEAD:app/src/main/java/com/multishare/file/Splash_Screen_Compose.kt
import com.multishare.R
import kotlinx.coroutines.delay

=======
import com.multishare.ui.home.homeScreen
import com.multishare.ui.theme.TVMultishareTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            SplashScreenContent()
        }
    }
}
>>>>>>> 22f131bda8fd66212fd1f8e610b1ef3e1fc1bbdb:app/src/main/java/com/multishare/MainActivity.kt

@Composable
fun SplashScreenContent() {
    var showMainScreen by remember { mutableStateOf(false) }
<<<<<<< HEAD:app/src/main/java/com/multishare/file/Splash_Screen_Compose.kt

    // Launch effect to wait for the splash screen
    LaunchedEffect(key1 = true) {
        delay(3000L) // 3-second delay for the splash screen
=======
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        delay(3000L)
>>>>>>> 22f131bda8fd66212fd1f8e610b1ef3e1fc1bbdb:app/src/main/java/com/multishare/MainActivity.kt
        showMainScreen = true
    }

    if (showMainScreen) {
<<<<<<< HEAD:app/src/main/java/com/multishare/file/Splash_Screen_Compose.kt
        MainContent()
        StorageUsageScreen()
=======
        homeScreen()
>>>>>>> 22f131bda8fd66212fd1f8e610b1ef3e1fc1bbdb:app/src/main/java/com/multishare/MainActivity.kt
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
<<<<<<< HEAD:app/src/main/java/com/multishare/file/Splash_Screen_Compose.kt
        modifier = Modifier.fillMaxSize(),
=======
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
>>>>>>> 22f131bda8fd66212fd1f8e610b1ef3e1fc1bbdb:app/src/main/java/com/multishare/MainActivity.kt
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize()
        )
    }
}

<<<<<<< HEAD:app/src/main/java/com/multishare/file/Splash_Screen_Compose.kt
@Composable
fun MainContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}

=======
>>>>>>> 22f131bda8fd66212fd1f8e610b1ef3e1fc1bbdb:app/src/main/java/com/multishare/MainActivity.kt

