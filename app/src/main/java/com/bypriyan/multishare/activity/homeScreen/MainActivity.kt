package com.bypriyan.multishare.activity.homeScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
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
            if (data != null) {
                binding.circularProgressBarExternal.progress = data.usagePercentage
                binding.externalprogressText.text = "${data.usagePercentage}%"
                binding.externalstorageText?.text = formatStorage(data.usedStorage, data.totalStorage)
            }
        }

        viewModel.loadStorageData()

        //send button
        binding.sendBtn.setOnClickListener {
            startActivity(Intent(this, SendActivity::class.java))
        }

        binding.reciveBtn.setOnClickListener{
            startActivity(Intent(this, ReciveActivity::class.java))
        }

    }

    private fun formatStorage(used: Long, total: Long): String {
        val usedGB = used / (1024 * 1024 * 1024)
        val totalGB = total / (1024 * 1024 * 1024)
        return "$usedGB GB/$totalGB GB"
    }
}