package com.example.cropit.ui.composables


import android.net.Uri
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.toColorInt
import com.example.cropit.R
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.cropit.BuildConfig
import com.example.cropit.utils.publicsansBold
import com.example.cropit.utils.publicsansSemiBold
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current
    var capturedImageUri = remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    var imageUri = remember { mutableStateOf(Uri.EMPTY) }

    val bottomSheetState = remember { mutableStateOf(false) }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if(success){
            imageUri.value.let {
                capturedImageUri.value = it
            }
        }

    }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraLauncher.launch(imageUri.value)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    // Handle Camera Click with Permission Check
    fun captureImage() {
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            val newFile = context.createImageFile()
            val newImageUri = FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                newFile
            )
            imageUri.value = newImageUri
            Handler(Looper.getMainLooper()).postDelayed({
                cameraLauncher.launch(imageUri.value)
            }, 300)


        } else {

            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Gallery Launcher
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
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
                Text(
                    modifier = Modifier,
                    text = "Select Your Image Source",
                    fontSize = 16.sp,
                    fontFamily = publicsansBold,
                    color =Color("#ed5ac3".toColorInt())
                )
                Spacer(modifier=Modifier.height(30.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f).clickable(){
                            galleryLauncher.launch("image/*")
                            bottomSheetState.value = false
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.gallery),

                            contentDescription = "",
                            modifier = Modifier.padding(top=10.dp, bottom = 5.dp)
                                .width(35.dp)
                                .height(35.dp),
                        )
                        Text(
                            modifier = Modifier,
                            text = "Gallery",
                            fontSize = 14.sp,
                            fontFamily = publicsansSemiBold,
                            color =Color("#000000".toColorInt())
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f).clickable(){

                            captureImage()
                            bottomSheetState.value = false
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera_ic),
                            contentDescription = "",
                            modifier = Modifier.padding(top=10.dp, bottom = 5.dp)
                                .width(35.dp)
                                .height(35.dp),
                        )
                        Text(
                            modifier = Modifier,
                            text = "Camera",
                            fontSize = 14.sp,
                            fontFamily = publicsansSemiBold,
                            color =Color("#000000".toColorInt())
                        )
                    }

                }
                Spacer(modifier=Modifier.height(50.dp))

            }


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
                    bottomSheetState.value=true
                } // Click to reselect image
        ) {
            if (capturedImageUri.value != Uri.EMPTY) {
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
        if(capturedImageUri.value.path?.isNotEmpty() == true){
            Row (modifier = Modifier.fillMaxWidth()){
                Column (modifier = Modifier
                    .weight(1f)
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
                        painter = painterResource(id = R.drawable.edit),
                        tint = Color("#ffffff".toColorInt()),
                        contentDescription = "",
                        modifier = Modifier.padding(top=10.dp, bottom = 10.dp)
                            .width(35.dp)
                            .height(35.dp),
                    )
                    Text(
                        modifier = Modifier,
                        text = "Edit",
                        fontSize = 14.sp,
                        fontFamily = publicsansSemiBold,
                        color =Color("#ffffff".toColorInt())
                    )
                }
                Column (modifier = Modifier
                    .weight(1f)
                    .padding(start = 25.dp,end=25.dp,top=20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color("#333333".toColorInt()))
                    .padding(10.dp).clickable(){
                        bottomSheetState.value=true

                    },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Icon(
                        painter = painterResource(id = R.drawable.recent),
                        tint = Color("#ffffff".toColorInt()),
                        contentDescription = "",
                        modifier = Modifier.padding(top=10.dp, bottom = 10.dp)
                            .width(35.dp)
                            .height(35.dp),
                    )
                    Text(
                        modifier = Modifier,
                        text = "Retake",
                        fontSize = 14.sp,
                        fontFamily = publicsansSemiBold,
                        color =Color("#ffffff".toColorInt())
                    )
                }
            }
        }else{

            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp,end=25.dp,top=20.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color("#333333".toColorInt()))
                .padding(10.dp).clickable(){
                    bottomSheetState.value=true

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
}


fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)  // Use Pictures directory
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
}
