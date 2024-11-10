package com.bypriyan.multishare.model

import android.net.Uri

// ImageModel.kt
data class ImageModel(
    val id: Int,
    val filePath: String,
    val fileUri: Uri,
    val fileType: String // e.g., "image", "video", "audio", "document"
)
