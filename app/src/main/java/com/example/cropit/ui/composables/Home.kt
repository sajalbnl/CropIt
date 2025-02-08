package com.example.cropit.ui.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.cropit.R
import androidx.navigation.NavController
import com.example.cropit.utils.publicsansSemiBold

@Composable
fun Home(navController: NavController) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color("#000000".toColorInt())),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(
            modifier = Modifier
                .size(350.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color.White, RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = "img",
                modifier = Modifier.fillMaxSize().alpha(0.4f),
                contentScale = ContentScale.Crop
            )
        }

        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp,end=25.dp,top=20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color("#333333".toColorInt()))
            .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                tint = Color("#ffffff".toColorInt()),
                contentDescription = "",
               modifier = Modifier.padding(top=10.dp, bottom = 10.dp)
                    .width(35.dp)
                    .height(35.dp),
            )
            Text(
                modifier = Modifier,
                text = "Lets start Editing",
                fontSize = 14.sp,
                fontFamily = publicsansSemiBold,
                color =Color("#ffffff".toColorInt())
            )


        }
    }
}