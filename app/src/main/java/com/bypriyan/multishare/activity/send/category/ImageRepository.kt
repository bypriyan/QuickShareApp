package com.bypriyan.multishare.activity.send.category
import android.content.Context
import android.provider.MediaStore
import com.bypriyan.multishare.model.ImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

interface ImageRepository {
    fun getImages(): Flow<List<ImageModel>>
}
class ImageRepositoryImpl(private val context: Context) : ImageRepository {

    override fun getImages(): Flow<List<ImageModel>> = flow {
        val images = mutableListOf<ImageModel>()

        // Define projection for the columns we want to retrieve
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA
        )

        // Query MediaStore for images from both internal and external storage
        val internalCursor = context.contentResolver.query(
            MediaStore.Images.Media.INTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        val externalCursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        // Process the internal storage cursor
        internalCursor?.use { cursor ->
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (cursor.moveToNext()) {
                val path = cursor.getString(dataColumn)
                val file = File(path)
                if (file.exists()) {
                    images.add(ImageModel(images.size, path))
                }
            }
        }

        // Process the external storage cursor
        externalCursor?.use { cursor ->
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (cursor.moveToNext()) {
                val path = cursor.getString(dataColumn)
                val file = File(path)
                if (file.exists()) {
                    images.add(ImageModel(images.size, path))
                }
            }
        }

        emit(images)
    }
}

