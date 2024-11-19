import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.bypriyan.multishare.R
import com.bypriyan.multishare.databinding.RowFilesBinding
import com.bypriyan.multishare.model.ImageModel

class FileAdapter(
    private var fileList: List<ImageModel>,
    private val onFileSelected: (ImageModel, Boolean) -> Unit
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(private val binding: RowFilesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fileData: ImageModel) {
            // Handle different file types and load thumbnails accordingly
            when (fileData.fileType) {
                "IMAGE" -> {
                    binding.image.load(fileData.fileUri) {
                        listener(
                            onError = { _, _ ->
                                binding.image.setImageResource(R.drawable.logo)
                            }
                        )
                    }
                }
                "VIDEO" -> {
                    // Load the video thumbnail if available
                    val thumbnailUri = fileData.thumbnailUri
                    if (thumbnailUri != null) {
                        binding.image.load(thumbnailUri) {
                            listener(
                                onError = { _, _ ->
                                    binding.image.setImageResource(R.drawable.logo)
                                }
                            )
                        }
                    } else {
                        // Fallback if no thumbnail
                        binding.image.setImageResource(R.drawable.logo)
                    }
                }
            }

            // Ensure checkbox reflects the correct state
            binding.checkbox.setOnCheckedChangeListener(null)  // Remove the old listener
            binding.checkbox.isChecked = fileData.isSelected  // Set checkbox state
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                fileData.isSelected = isChecked
                onFileSelected(fileData, isChecked)  // Callback for state changes
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

    fun updateFiles(newFileList: List<ImageModel>) {
        fileList = newFileList
        notifyDataSetChanged()
    }
}
