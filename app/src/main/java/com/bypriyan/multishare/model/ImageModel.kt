package com.bypriyan.multishare.model

import android.graphics.Bitmap
import android.net.Uri

data class ImageModel(
    val id: Int,
    val filePath: String,       // Full file path on the device
    val fileUri: Uri,           // URI of the file
    val fileType: String,       // e.g., "image", "video", "audio", "document"
    var isSelected: Boolean = false, // Selection state
    val thumbnailUri: Uri? = null,   // Thumbnail URI (for images or cached videos)
    var thumbnailBitmap: Bitmap? = null // Bitmap for video thumbnails
)
