package com.bypriyan.multishare.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bypriyan.multishare.databinding.RowFilesBinding
import com.bypriyan.multishare.model.FileData

class FileAdapter(
    private val fileList: List<FileData>,
    private val onFileSelected: (FileData, Boolean) -> Unit // Callback for checkbox selection
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(private val binding: RowFilesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fileData: FileData) {
            // Load the thumbnail image
            binding.image.setImageBitmap(fileData.thumbnail)

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
