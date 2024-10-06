package com.multishare.ui.home

import android.os.Environment
import android.os.StatFs
import java.io.File
import javax.inject.Inject

class StorageRepository @Inject constructor() {

    fun getInternalStorageInfo(): StorageInfo {
        val internalStorageDir = Environment.getDataDirectory()
        return getStorageInfo(internalStorageDir)
    }

    fun getExternalStorageInfo(): StorageInfo {
        val externalStorageDir = Environment.getExternalStorageDirectory()
        return getStorageInfo(externalStorageDir)
    }

    private fun getStorageInfo(storageDir: File): StorageInfo {
        val statFs = StatFs(storageDir.path)

        val blockSize = statFs.blockSizeLong
        val totalBlocks = statFs.blockCountLong
        val availableBlocks = statFs.availableBlocksLong
        val usedBlocks = totalBlocks - availableBlocks

        val totalSpace = totalBlocks * blockSize
        val availableSpace = availableBlocks * blockSize
        val usedSpace = usedBlocks * blockSize

        return StorageInfo(totalSpace, usedSpace, availableSpace)
    }
}

data class StorageInfo(
    val totalSpace: Long,
    val usedSpace: Long,
    val availableSpace: Long
)