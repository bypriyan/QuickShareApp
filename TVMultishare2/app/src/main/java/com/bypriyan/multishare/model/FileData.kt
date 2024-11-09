package com.bypriyan.multishare.model

import android.graphics.Bitmap

data class FileData(
    val name: String,
    val path: String,
    val lastModified: Long,
    val fileType: String,  // IMAGE, VIDEO, AUDIO, or OTHER
    val thumbnail: Bitmap? // Thumbnail image if available
)

