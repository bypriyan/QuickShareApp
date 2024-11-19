package com.bypriyan.multishare.activity.send.category

import FileAdapter
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
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager

@AndroidEntryPoint
class FileCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileCategoryBinding
    private val viewModel: ImageViewModel by viewModels()
    private lateinit var fileAdapter: FileAdapter
    private lateinit var fileType: String

    private lateinit var manageStoragePermissionLauncher: ActivityResultLauncher<Intent>

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

        // Initialize ActivityResultLauncher for managing external storage permission
        manageStoragePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                loadFile()
            } else {
                Log.d("img", "Manage External Storage permission denied.")
            }
        }
    }

    private fun checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                loadFile()
            } else {
                requestManageExternalStoragePermission()
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                loadFile()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun requestManageExternalStoragePermission() {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:$packageName"))
        manageStoragePermissionLauncher.launch(intent)
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

    fun loadFile() {
        when (fileType) {
            "img" -> viewModel.loadFiles(FileType.IMAGE)
            "vid" -> viewModel.loadFiles(FileType.VIDEO)
            "mus" -> viewModel.loadFiles(FileType.MUSIC)
            "doc" -> viewModel.loadFiles(FileType.DOCUMENT)
            "apk" -> viewModel.loadFiles(FileType.APK)
        }
    }
}

