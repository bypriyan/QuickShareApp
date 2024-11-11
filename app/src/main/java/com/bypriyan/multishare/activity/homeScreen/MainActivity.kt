package com.bypriyan.multishare.activity.homeScreen

import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bypriyan.multishare.activity.recive.ReciveActivity
import com.bypriyan.multishare.activity.send.SendActivity
import com.bypriyan.multishare.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: StorageViewModel by viewModels()

    // Define an Activity Result Launcher to handle WiFi settings result
    private val wifiSettingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        if (wifiManager.isWifiEnabled) {
            proceedWithStorageAccess()
        }
    }

    // Define an Activity Result Launcher for permission request
    private val storagePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val readPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        val writePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: false

        if (readPermissionGranted && writePermissionGranted) {
            proceedWithStorageAccess()
        } else {
            Toast.makeText(this, "Storage permissions are required to access files.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.internalStorageData.observe(this) { data ->
            binding.circularProgressBarInternal.progress = data.usagePercentage
            binding.progressTextInternal.text = "${data.usagePercentage}%"
            binding.internalstorageText.text = formatStorage(data.usedStorage, data.totalStorage)
        }

        viewModel.externalStorageData.observe(this) { data ->
            data?.let {
                binding.circularProgressBarExternal.progress = it.usagePercentage
                binding.externalprogressText.text = "${it.usagePercentage}%"
                binding.externalstorageText?.text = formatStorage(it.usedStorage, it.totalStorage)
            }
        }

        viewModel.loadStorageData()

        binding.sendBtn.setOnClickListener {
            checkAndEnableWifi {
                checkAndRequestStoragePermission { startActivity(Intent(this, SendActivity::class.java)) }
            }
        }

        binding.reciveBtn.setOnClickListener {
            checkAndEnableWifi {
                checkAndRequestStoragePermission { startActivity(Intent(this, ReciveActivity::class.java)) }
            }
        }

        binding.menu.setOnClickListener{
            //open navigation drawer
        }
    }

    private fun formatStorage(used: Long, total: Long): String {
        val usedGB = used / (1024 * 1024 * 1024)
        val totalGB = total / (1024 * 1024 * 1024)
        return "$usedGB GB/$totalGB GB"
    }

    private fun checkAndEnableWifi(onWifiEnabled: () -> Unit) {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        if (wifiManager.isWifiEnabled) {
            onWifiEnabled()
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Enable WiFi")
                setMessage("This action requires WiFi to be enabled. Would you like to enable it?")
                setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    wifiSettingsLauncher.launch(intent)
                }
                setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                show()
            }
        }
    }

    private fun checkAndRequestStoragePermission(onPermissionsGranted: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                onPermissionsGranted()
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Storage Permission Needed")
                    setMessage("To access files, enable storage permissions in settings.")
                    setPositiveButton("Grant") { _, _ ->
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.data = Uri.parse("package:$packageName")
                        startActivity(intent)
                    }
                    setNegativeButton("Cancel", null)
                    show()
                }
            }
        } else {
            storagePermissionLauncher.launch(
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )
            onPermissionsGranted()
        }
    }

    private fun proceedWithStorageAccess() {
        Toast.makeText(this, "Storage access granted.", Toast.LENGTH_SHORT).show()
    }
}
