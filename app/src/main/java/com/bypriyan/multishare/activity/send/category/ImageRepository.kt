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

        // Define projection to retrieve the ID and data (file path) columns
        val projection = arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DATA)

        // Build selection based on MIME types provided for the file type
        val selection = mimeTypes.joinToString(" OR ") { "${MediaStore.Files.FileColumns.MIME_TYPE} = ?" }
        val selectionArgs = mimeTypes

        // Query the MediaStore with specified URI and selection criteria
        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            while (cursor.moveToNext()) {
                val path = cursor.getString(dataColumn)
                val file = File(path)
                if (file.exists()) {
                    // Add each valid file to the list, setting file type in ImageModel
                    files.add(ImageModel(files.size, path, Uri.fromFile(file), fileType.name))
                }
            }
        }

        emit(files) // Emit the list of files
    }
}


