import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.bypriyan.multishare.R
import com.bypriyan.multishare.databinding.RowFilesBinding
import com.bypriyan.multishare.model.ImageModel
import kotlinx.coroutines.*

class FileAdapter(
    private var fileList: List<ImageModel>,
    private val onFileSelected: (ImageModel, Boolean) -> Unit
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.Main + Job()) // Scope for async tasks

    inner class FileViewHolder(private val binding: RowFilesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(fileData: ImageModel) {
            // Handle different file types and load thumbnails
            when (fileData.fileType) {
                "IMAGE" -> {
                    // Load image thumbnail using Coil
                    binding.image.load(fileData.fileUri) {
                        crossfade(true)
                        listener(onError = { _, _ -> binding.image.setImageResource(R.drawable.logo) })
                    }
                }
                "VIDEO" -> {
                    // Use cached Bitmap or generate asynchronously
                    if (fileData.thumbnailBitmap != null) {
                        binding.image.load(fileData.thumbnailBitmap) {
                            crossfade(true)
                        }
                    } else {
                        loadVideoThumbnailAsync(fileData)
                    }
                }
                "MUSIC" -> {
                    // Load music album art thumbnail
                    if (fileData.albumArtBitmap != null) {
                        binding.image.load(fileData.albumArtBitmap) {
                            crossfade(true)
                        }
                    } else {
                        loadMusicThumbnailAsync(fileData)
                    }
                }
                else -> {
                    binding.image.setImageResource(R.drawable.logo) // Fallback for other types
                }
            }

            // Checkbox handling
            binding.checkbox.setOnCheckedChangeListener(null) // Remove previous listeners
            binding.checkbox.isChecked = fileData.isSelected // Set current state
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (fileData.isSelected != isChecked) {
                    fileData.isSelected = isChecked
                    onFileSelected(fileData, isChecked) // Callback for selection state
                }
            }
        }

        private fun loadVideoThumbnailAsync(fileData: ImageModel) {
            binding.image.setImageResource(R.drawable.logo) // Set placeholder immediately
            scope.launch {
                val bitmap = withContext(Dispatchers.IO) {
                    generateVideoThumbnail(fileData.fileUri)
                }
                if (bitmap != null) {
                    fileData.thumbnailBitmap = bitmap // Cache generated thumbnail
                    binding.image.load(bitmap) {
                        crossfade(true)
                    }
                } else {
                    binding.image.setImageResource(R.drawable.logo) // Fallback
                }
            }
        }

        private fun generateVideoThumbnail(uri: Uri): Bitmap? {
            val retriever = MediaMetadataRetriever()
            return try {
                retriever.setDataSource(itemView.context, uri)
                retriever.getFrameAtTime(1000000) // Get the frame at 1 second
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                retriever.release()
            }
        }

        private fun loadMusicThumbnailAsync(fileData: ImageModel) {
            binding.image.setImageResource(R.drawable.logo) // Set placeholder immediately
            scope.launch {
                val bitmap = withContext(Dispatchers.IO) {
                    generateMusicThumbnail(fileData.fileUri)
                }
                if (bitmap != null) {
                    fileData.albumArtBitmap = bitmap // Cache the album art
                    binding.image.load(bitmap) {
                        crossfade(true)
                    }
                } else {
                    binding.image.setImageResource(R.drawable.logo) // Fallback
                }
            }
        }

        private fun generateMusicThumbnail(uri: Uri): Bitmap? {
            val retriever = MediaMetadataRetriever()
            return try {
                retriever.setDataSource(itemView.context, uri)
                val data = retriever.embeddedPicture // Get album art as byte array
                if (data != null) {
                    BitmapFactory.decodeByteArray(data, 0, data.size) // Convert byte array to Bitmap
                } else {
                    null // Return null if no album art is found
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                retriever.release()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = RowFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(fileList[position])
    }

    override fun getItemCount(): Int = fileList.size

    // Update the adapter's file list and refresh the RecyclerView
    fun updateFileList(newFileList: List<ImageModel>) {
        fileList = newFileList
        notifyDataSetChanged()
    }

    // Clean up coroutine resources when adapter is no longer in use
    fun clear() {
        scope.cancel()
    }
}
