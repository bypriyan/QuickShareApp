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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init
        fileAdapter = FileAdapter(emptyList()) { imageModel, isSelected -> }
        checkStoragePermission()

        lifecycleScope.launch {
            viewModel.images.collect { images ->
                Log.d("img", "Images: $images")
                fileAdapter = FileAdapter(images) { imageModel, isSelected -> }
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
                viewModel.loadImages()
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
            viewModel.loadImages()
        } else {
            Log.d("img", "Permission denied.")
        }
    }
}