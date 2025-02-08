package com.example.cropit

import android.app.Activity
import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.example.cropit.datastore.CropItStoreModule
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dataStore = CropItStoreModule(context)
    val hasAccountState = dataStore.hasAccount.collectAsState(initial = null)

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        hasAccountState.value?.let { hasAccount ->
             if (hasAccount) navController.navigate("home") else navController.navigate("intro")
        }

    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    val context = LocalContext.current
    val window = (context as Activity).window
    // Set the status bar and navigation bar colors
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color("#000000".toColorInt()).toArgb()
        window.navigationBarColor = Color("#000000".toColorInt()).toArgb()
    }

    // Ensure status bar text remains white
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = false
    }
    Box(
        modifier = Modifier
            .background(Color("#000000".toColorInt()))
            .fillMaxSize().systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.app_logo),
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),
            contentDescription = "Logo Icon",
        )
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}