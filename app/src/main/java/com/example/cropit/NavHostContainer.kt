package com.example.cropit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cropit.ui.composables.Home

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(route = "splash") {
                AnimatedSplashScreen(navController = navController)
            }

            composable("home") {
                Home(navController)
            }
        }
    )
}
