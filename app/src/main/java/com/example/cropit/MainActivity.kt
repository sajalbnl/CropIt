package com.example.cropit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.rememberNavController
import com.example.cropit.ui.theme.CropItTheme

class MainActivity : ComponentActivity() {
    private lateinit var currentDestination: MutableState<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            CropItTheme {
                window.navigationBarColor = colorScheme.background.toArgb()
                currentDestination = remember { mutableStateOf("") }

                Surface(modifier = Modifier.fillMaxSize(), color = Color("#ffffff".toColorInt())) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        currentDestination.value = destination.route ?: ""
                    }
                    val showTopBar = currentDestination.value in listOf("home","recent")
                    val showBottomBar=currentDestination.value in listOf("home","recent")

                    Scaffold(
                        topBar = {if (showTopBar)TopBar(navController=navController)},
                        bottomBar = { if(showBottomBar)BottomBar(navController = navController) },
                        content = { padding ->
                            NavHostContainer(
                                navController = navController, padding = padding
                            )
                        })
                }
            }
        }
    }
}
