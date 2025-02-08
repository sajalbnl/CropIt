package com.example.cropit

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.cropit.datastore.CropItStoreModule
import com.example.cropit.utils.publicsansSemiBold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun IntroPage(navController: NavController) {
    val context = LocalContext.current
    val dataStore = CropItStoreModule(context)

    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color("#000000".toColorInt())),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.camera),
            contentDescription = "img",
            modifier = Modifier.size(350.dp),

        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.padding(start = 30.dp, end = 30.dp),
            text = "Capture, Crop & Rotate with Ease",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontFamily = publicsansSemiBold,
            color =Color("#ffffff".toColorInt())
        )
        Spacer(modifier = Modifier.weight(1f))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color("#ed5ac3".toColorInt()),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 35.dp, end = 35.dp, bottom = 20.dp),
            shape = RoundedCornerShape(30.dp),
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    dataStore.setHasAccount(true)
                }
                navController.popBackStack()
                navController.navigate("home")
            }
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 5.dp,
                    bottom = 5.dp
                ),
                fontFamily = publicsansSemiBold,
                text = "Get Started",
                color = Color("#ffffff".toColorInt()),
                fontSize = 15.sp
            )

        }

    }
}