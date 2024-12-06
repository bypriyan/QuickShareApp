package com.bypriyan.multishare.model

import android.graphics.Bitmap
import android.net.Uri
data class ImageModel(
    val id: Int,
    val filePath: String,
    val fileUri: Uri,
    val fileType: String,
    var isSelected: Boolean = false,
    var thumbnailBitmap: Bitmap? = null,  // For video and image thumbnails
    var albumArtBitmap: Bitmap? = null    // For music album art
)
