package com.bypriyan.multishare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bypriyan.multishare.databinding.RowFilesBinding
import com.bypriyan.multishare.model.FileData
import coil3.load
import com.bypriyan.multishare.R
import com.bypriyan.multishare.model.ImageModel

class FileAdapter(
    private val fileList: List<ImageModel>,
    private val onFileSelected: (ImageModel, Boolean) -> Unit // Callback for checkbox selection
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(private val binding: RowFilesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fileData: ImageModel) {
            // Load the thumbnail image using Coil
            binding.image.load(fileData.imagePath)

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
