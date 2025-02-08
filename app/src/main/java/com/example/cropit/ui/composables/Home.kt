package com.example.cropit.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.cropit.utils.publicsansBold

@Composable
fun Home(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize().background(Color("#000000".toColorInt())), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Your Anime Hub",
            fontFamily = publicsansBold,
            fontSize = 38.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp),
            color = Color("#ffffff".toColorInt())
        )


    }
}