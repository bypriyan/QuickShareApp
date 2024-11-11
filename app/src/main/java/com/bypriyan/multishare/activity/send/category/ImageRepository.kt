package com.bypriyan.multishare.activity.send.category
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.bypriyan.multishare.model.ImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

// Define FileType Enum
enum class FileType {
    IMAGE, VIDEO, MUSIC, DOCUMENT, APK
}

// Update ImageRepository Interface
interface ImageRepository {
    fun getFiles(fileType: FileType): Flow<List<ImageModel>>
}

// Implementation of ImageRepository
class ImageRepositoryImpl(private val context: Context) : ImageRepository {

    override fun getFiles(fileType: FileType): Flow<List<ImageModel>> = flow {
        val files = mutableListOf<ImageModel>()
        val (uri, mimeTypes) = when (fileType) {
            FileType.IMAGE -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI to arrayOf("image/jpeg", "image/png", "image/jpg")
            FileType.VIDEO -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI to arrayOf("video/mp4", "video/3gp", "video/avi")
            FileType.MUSIC -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI to arrayOf("audio/mpeg", "audio/aac", "audio/wav")
            FileType.DOCUMENT -> MediaStore.Files.getContentUri("external") to arrayOf(
                "application/pdf",
                "application/msword",
                "application/vnd.ms-excel",
                "text/plain"
            )
            FileType.APK -> MediaStore.Files.getContentUri("external") to arrayOf("application/vnd.android.package-archive")
        }

        val projection = arrayOf(MediaStore.Files.FileColumns._ID)
        val selection = mimeTypes.joinToString(" OR ") { "${MediaStore.Files.FileColumns.MIME_TYPE} = ?" }
        val selectionArgs = mimeTypes

        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = Uri.withAppendedPath(uri, id.toString())
                files.add(ImageModel(files.size, contentUri.toString(), contentUri, fileType.name))
            }
        }

        emit(files)
    }
}

