package com.bypriyan.multishare.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bypriyan.multishare.databinding.RowFilesBinding
import com.bypriyan.multishare.model.FileData
import coil3.load
import com.bypriyan.multishare.R
import com.bypriyan.multishare.model.ImageModel
import java.io.File

class FileAdapter(
    private val fileList: List<ImageModel>,
    private val onFileSelected: (ImageModel, Boolean) -> Unit // Callback for checkbox selection
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(private val binding: RowFilesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fileData: ImageModel) {
            // Log the file path to verify it's correct
            Log.d("FileAdapter", "Loading image from URI: ${fileData.fileUri}")

            // Load the image using Coil with error handling
            binding.image.load(fileData.fileUri) { // Use the URI from ImageModel
                listener(
                    onError = { _, _ ->
                        Log.e("FileAdapter", "Failed to load image: ${fileData.filePath}")
                    },
                    onSuccess = { _, _ ->
                        Log.d("FileAdapter", "Image loaded successfully: ${fileData.filePath}")
                    }
                )
            }

            // Checkbox listener for selecting/unselecting files
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onFileSelected(fileData, isChecked)
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
}
