package com.bypriyan.multishare.activity.send.category

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bypriyan.multishare.databinding.ActivityFileCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.Manifest
import androidx.recyclerview.widget.LinearLayoutManager
import com.bypriyan.multishare.adapter.FileAdapter

@AndroidEntryPoint
class FileCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileCategoryBinding
    private val viewModel: ImageViewModel by viewModels()
    private lateinit var fileAdapter: FileAdapter
    private lateinit var fileType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fileAdapter = FileAdapter(emptyList()) { imageModel, isSelected -> }
        binding.imagesRV.adapter = fileAdapter
        fileType = intent.getStringExtra("TYPE").toString()
        Log.d("TAGs", "onCreate: $fileType")

        checkStoragePermission()

        lifecycleScope.launch {
            viewModel.files.collect { files ->
                Log.d("Files", "Files: $files")
                fileAdapter = FileAdapter(files) { imageModel, isSelected -> }
                binding.imagesRV.adapter = fileAdapter
            }
        }

    }

    private fun checkStoragePermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadFile()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            loadFile()
        } else {
            Log.d("img", "Permission denied.")
        }
    }

    fun loadFile(){
        when(fileType){
            "img"->viewModel.loadFiles(FileType.IMAGE)
            "vid"->viewModel.loadFiles(FileType.VIDEO)
            "mus"->viewModel.loadFiles(FileType.MUSIC)
            "doc"->viewModel.loadFiles(FileType.DOCUMENT)
            "apk"->viewModel.loadFiles(FileType.APK)
        }
    }
}