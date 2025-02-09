package com.example.cropit.ui.composables


import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.cropit.R
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.cropit.utils.publicsansSemiBold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    var capturedImageUri = remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val bottomSheetState = remember { mutableStateOf(false) }
    if (bottomSheetState.value) {
        val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { bottomSheetState.value = false },
            sheetState = modalBottomSheetState,
            containerColor = White,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ){
            Column (modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){


            }


        }
    }
    // Image Picker Launcher (Gallery or Camera)
    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            capturedImageUri.value = it
        }
    }

    // Crop Image launcher
    val cropImageLauncher =
        rememberLauncherForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                capturedImageUri.value = result.uriContent!!
            }
        }

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
                .clickable {
                    // Allow user to retake or choose a new image
                    imagePickerLauncher.launch("image/*")
                } // Click to reselect image
        ) {
            if (capturedImageUri.value.path?.isNotEmpty() == true) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(capturedImageUri.value),
                    contentDescription = null
                )
            }else{
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = "img",
                    modifier = Modifier.fillMaxSize().alpha(0.4f),
                    contentScale = ContentScale.Crop
                )

            }

        }

        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp,end=25.dp,top=20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color("#333333".toColorInt()))
            .padding(10.dp).clickable(){
                cropImageLauncher.launch(
                    CropImageContractOptions(
                        capturedImageUri.value,
                        CropImageOptions()
                    )
                )

            },
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
                text = "Import & start Editing",
                fontSize = 14.sp,
                fontFamily = publicsansSemiBold,
                color =Color("#ffffff".toColorInt())
            )
        }
    }
}