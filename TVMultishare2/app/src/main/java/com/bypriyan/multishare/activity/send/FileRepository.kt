package com.bypriyan.multishare.activity.send
import com.bypriyan.multishare.model.FileData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import com.bypriyan.multishare.model.FileType

@Singleton
class FileRepository @Inject constructor() {

    suspend fun getRecentFiles(directory: File): List<FileData> = withContext(Dispatchers.IO) {
        val files = directory.listFiles()
            ?.filter { it.isFile } // Only include files, not directories
            ?.sortedByDescending { it.lastModified() }

        Log.d("TAG", "Files found: ${files?.map { it.absolutePath }}")

        files?.map { file ->
            val fileType = getFileType(file)
            FileData(
                name = file.name,
                path = file.absolutePath,
                lastModified = file.lastModified(),
                fileType = fileType.toString(),
                thumbnail = generateThumbnail(file, fileType)
            )
        } ?: emptyList()
    }


    private fun getFileType(file: File): FileType {
        return when (file.extension.lowercase()) {
            "jpg", "jpeg", "png", "gif", "bmp" -> FileType.IMAGE
            "mp4", "mkv", "avi", "mov" -> FileType.VIDEO
            "mp3", "wav", "aac", "flac" -> FileType.MUSIC
            "pdf", "doc", "docx", "txt" -> FileType.DOCUMENT
            "apk" -> FileType.APK
            else -> FileType.OTHER
        }
    }

    private fun generateThumbnail(file: File, fileType: FileType): Bitmap? {
        return when (fileType) {
            FileType.IMAGE -> BitmapFactory.decodeFile(file.absolutePath)?.also {
                Log.d("TAG", "Generated image thumbnail for ${file.name}")
            }
            FileType.VIDEO -> ThumbnailUtils.createVideoThumbnail(
                file.absolutePath,
                MediaStore.Images.Thumbnails.MINI_KIND
            )?.also {
                Log.d("TAG", "Generated video thumbnail for ${file.name}")
            }
            else -> null
        }
    }

}
