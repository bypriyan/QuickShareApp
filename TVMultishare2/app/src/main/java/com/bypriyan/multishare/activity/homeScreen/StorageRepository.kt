package com.bypriyan.multishare.activity.homeScreen

import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.bypriyan.multishare.model.StorageData
import javax.inject.Inject

interface StorageRepository {
    suspend fun getInternalStorageData(): StorageData
    suspend fun getExternalStorageData(): StorageData?
}

class StorageRepositoryImpl @Inject constructor(
    private val context: Context
) : StorageRepository {

    override suspend fun getInternalStorageData(): StorageData {
        // Logic to calculate internal storage data
        val stat = StatFs(Environment.getDataDirectory().path)
        val total = stat.blockSizeLong * stat.blockCountLong
        val available = stat.blockSizeLong * stat.availableBlocksLong
        val used = total - available
        return StorageData(usedStorage = used, totalStorage = total)
    }

    override suspend fun getExternalStorageData(): StorageData? {
        val externalFilesDirs = context.getExternalFilesDirs(null)
        val externalStat = externalFilesDirs.getOrNull(1)?.let { StatFs(it.path) }
        return if (externalStat != null) {
            val total = externalStat.blockSizeLong * externalStat.blockCountLong
            val available = externalStat.blockSizeLong * externalStat.availableBlocksLong
            val used = total - available
            StorageData(usedStorage = used, totalStorage = total)
        } else null
    }
}
