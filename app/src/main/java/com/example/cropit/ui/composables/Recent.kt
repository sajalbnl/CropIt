package com.example.cropit.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.cropit.utils.publicsansSemiBold


@Composable
fun Recent(navController: NavController){

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color("#000000".toColorInt())),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            modifier = Modifier,
            text = "No Recent Project",
            fontSize = 30.sp,
            fontFamily = publicsansSemiBold,
            color =Color("#ffffff".toColorInt())
        )
    }

}