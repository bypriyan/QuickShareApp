package com.multishare.ui.home

import android.os.Environment
import android.os.StatFs
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.multishare.R
import com.multishare.composable.circularProgressIndicator
import com.multishare.composable.topAppBar

@Composable
fun homeScreen() {
    // Get storage stats for internal storage
    val internalStatFs = StatFs(Environment.getDataDirectory().absolutePath)

    // State to hold storage information
    var totalInternalStorage by remember { mutableStateOf(0L) }
    var availableInternalStorage by remember { mutableStateOf(0L) }
    var usedInternalStorage by remember { mutableStateOf(0L) }

    // State for external storage (memory card)
    var totalExternalStorage by remember { mutableStateOf(0L) }
    var availableExternalStorage by remember { mutableStateOf(0L) }
    var usedExternalStorage by remember { mutableStateOf(0L) }
    var isMemoryCardAvailable by remember { mutableStateOf(false) }

    // Calculate storage values in LaunchedEffect
    LaunchedEffect(Unit) {
        // Internal Storage
        totalInternalStorage = internalStatFs.blockCountLong * internalStatFs.blockSizeLong
        availableInternalStorage = internalStatFs.availableBlocksLong * internalStatFs.blockSizeLong
        usedInternalStorage = totalInternalStorage - availableInternalStorage

        // External Storage (Memory Card)
        val externalStorageDir = Environment.getExternalStorageState()
        if (externalStorageDir == Environment.MEDIA_MOUNTED) {
            val memoryCardPath = Environment.getExternalStorageDirectory().absolutePath // This usually points to the memory card
            val externalStatFs = StatFs(memoryCardPath)

            totalExternalStorage = externalStatFs.blockCountLong * externalStatFs.blockSizeLong
            availableExternalStorage = externalStatFs.availableBlocksLong * externalStatFs.blockSizeLong
            usedExternalStorage = totalExternalStorage - availableExternalStorage
            isMemoryCardAvailable = true
        } else {
            // Handle case where external storage is not available
            isMemoryCardAvailable = false
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        topBar = {
            topAppBar("TV Multishare")
        }
    ) { padding ->

        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .weight(0.9f)
                    .verticalScroll(scrollState)
            ) {

                // Card 1: Internal Storage
                val internalStoragePercent = if (totalInternalStorage > 0) {
                    (usedInternalStorage.toFloat() / totalInternalStorage) * 100
                } else 0f

                androidx.compose.material3.Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.appLite)
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                    ) {
                        circularProgressIndicator(
                            percent = internalStoragePercent / 100,
                            num = 100,
                            fontSize = 20.sp,
                            radius = 40.dp
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        storageContent("Available Internal Storage", "${formatStorage(usedInternalStorage)} of ${formatStorage(totalInternalStorage)} used")
                    }
                }

                // Card 2: External Storage (Memory Card)
                if (isMemoryCardAvailable) {
                    val externalStoragePercent = if (totalExternalStorage > 0) {
                        (usedExternalStorage.toFloat() / totalExternalStorage) * 100
                    } else 0f

                    androidx.compose.material3.Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.appLite)
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp)
                        ) {
                            circularProgressIndicator(
                                percent = externalStoragePercent / 100,
                                num = 100,
                                fontSize = 20.sp,
                                radius = 40.dp
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            storageContent("Available External Storage", "${formatStorage(usedExternalStorage)} of ${formatStorage(totalExternalStorage)} used")
                        }
                    }
                } else {
                    // Handle case when memory card is not available
                    Text(
                        text = "Memory card not available",
                        modifier = Modifier.padding(16.dp),
                        color = colorResource(R.color.dark_greay),
                        fontFamily = FontFamily(Font(R.font.medium)),
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Send/Receive Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                ) {
                    fileButton(
                        painterResource(R.drawable.send),
                        "Send",
                        colorResource(R.color.appColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .wrapContentHeight()
                            .padding(5.dp)
                    )

                    fileButton(
                        painterResource(R.drawable.recive_file),
                        "Receive",
                        colorResource(R.color.progressLite),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .wrapContentHeight()
                            .padding(5.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.appColor))
                    .weight(0.1f),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material.Text(
                    text = "Ad",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = colorResource(R.color.white),
                    fontFamily = FontFamily(Font(R.font.bold))
                )
            }
        }
    }
}

@Composable
fun storageContent(title: String, storage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        androidx.compose.material.Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = colorResource(R.color.dark_greay),
            fontFamily = FontFamily(Font(R.font.medium))
        )

        Spacer(Modifier.height(10.dp))

        androidx.compose.material.Text(
            text = storage,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = colorResource(R.color.black),
            fontFamily = FontFamily(Font(R.font.black))
        )
        Spacer(Modifier.height(10.dp))

        androidx.compose.material.Text(
            text = "View Details",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = colorResource(R.color.appColor),
            fontFamily = FontFamily(Font(R.font.bold))
        )
    }
}

@Composable
fun fileButton(icon: Painter, buttonName: String, color: Color, modifier: Modifier) {
    androidx.compose.material3.Card(
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        modifier = modifier
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = icon,
                modifier = Modifier
                    .padding(10.dp)
                    .size(50.dp),
                contentDescription = "send img",
                colorFilter = ColorFilter.tint(color = Color.White)
            )

            Button(onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.white), // Change this to your desired background color
                    contentColor = colorResource(R.color.black) // Change this to your desired text color
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 0.dp, 15.dp, 10.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(buttonName,
                    color = colorResource(R.color.black),
                    fontFamily = FontFamily(Font(R.font.bold))
                )
            }
        }
    }
}

// Helper function to format storage size from bytes to GB
fun formatStorage(bytes: Long): String {
    return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0))
}
