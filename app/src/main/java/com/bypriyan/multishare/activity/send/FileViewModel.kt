// FileViewModel.kt
package com.bypriyan.multishare.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bypriyan.multishare.model.FileData
import java.io.File

class FileViewModel(application: Application) : AndroidViewModel(application) {

    val recentFiles = MutableLiveData<List<FileData>>()

    fun loadRecentFiles(directory: File) {
        val files = directory.listFiles()
        val fileDataList = files?.map { file ->
            // Detect file type based on extension
            val fileType = when {
                file.name.endsWith(".jpg", true) || file.name.endsWith(".jpeg", true) || file.name.endsWith(".png", true) -> "IMAGE"
                file.name.endsWith(".mp4", true) || file.name.endsWith(".avi", true) -> "VIDEO"
                file.name.endsWith(".mp3", true) || file.name.endsWith(".wav", true) -> "AUDIO"
                else -> "OTHER"
            }

            // Generate thumbnail based on file type
            val thumbnail: Bitmap? = when (fileType) {
                "IMAGE" -> ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.path), 100, 100)
                "VIDEO" -> ThumbnailUtils.createVideoThumbnail(file.path, MediaStore.Images.Thumbnails.MINI_KIND)
                else -> null
            }

            FileData(
                name = file.name,
                path = file.path,
                lastModified = file.lastModified(),
                fileType = fileType,
                thumbnail = thumbnail
            )
        }

        // Post the list to live data
        recentFiles.postValue(fileDataList ?: emptyList())
    }
}
