package com.bypriyan.multishare.activity.send

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import android.Manifest
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bypriyan.multishare.R
import com.bypriyan.multishare.activity.homeScreen.StorageViewModel
import com.bypriyan.multishare.activity.send.category.FileCategoryActivity
import com.bypriyan.multishare.databinding.ActivityMainBinding
import com.bypriyan.multishare.databinding.ActivitySendBinding
import com.bypriyan.multishare.viewmodel.FileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import kotlin.getValue

@AndroidEntryPoint
class SendActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendBinding
    private val viewModel: StorageViewModel by viewModels()
    private val fileViewModel: FileViewModel by viewModels()

    // Permission request for accessing storage
    private val storagePermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            loadFiles()
        } else {
            Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Observe file data and update adapter
        fileViewModel.recentFiles.observe(this) { files ->
            Log.d("TAG", "onCreate check: $files")
        }

        // Request permissions and load data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                loadFiles()
            } else {
                requestManageExternalStoragePermission()
            }
        } else {
            storagePermissionRequest.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        // Observe storage data to update UI
        viewModel.internalStorageData.observe(this) { data ->
            binding.circularProgressBarInternal?.progress = data.usagePercentage
            binding.progressTextInternal?.text = "${data.usagePercentage}%"
            binding.internalstorageText?.text = formatStorage(data.usedStorage, data.totalStorage)
        }


        //images
        binding.imagesBtn?.setOnClickListener{
            catActivity("img")
        }
        binding.videos?.setOnClickListener{
            catActivity("vid")
        }
        binding.music?.setOnClickListener{
            catActivity("mus")
        }
        binding.docs?.setOnClickListener{
            catActivity("doc")
        }
        binding.downloads?.setOnClickListener{
            catActivity("dow")
        }
        binding.apks?.setOnClickListener{
            catActivity("apk")
        }
    }

    fun catActivity(type:String){
        var intent = Intent(this, FileCategoryActivity::class.java)
        intent.putExtra("TYPE",type)
        startActivity(intent)
    }

    private fun loadFiles() {
        viewModel.loadStorageData()
        val directory = File("/storage/emulated/0/")
        if (directory.exists()) {
            fileViewModel.loadRecentFiles(directory)
        } else {
            Toast.makeText(this, "Directory not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun formatStorage(used: Long, total: Long): String {
        val usedGB = used / (1024 * 1024 * 1024)
        val totalGB = total / (1024 * 1024 * 1024)
        return "$usedGB GB / $totalGB GB"
    }

    private fun requestManageExternalStoragePermission() {
        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
        startActivityForResult(intent, REQUEST_CODE_MANAGE_STORAGE)
    }

    companion object {
        private const val REQUEST_CODE_MANAGE_STORAGE = 1001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MANAGE_STORAGE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                loadFiles()
            } else {
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
