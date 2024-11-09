package com.bypriyan.multishare.model

data class StorageData(
    val usedStorage: Long,
    val totalStorage: Long
) {
    val usagePercentage: Int
        get() = ((usedStorage.toDouble() / totalStorage) * 100).toInt()
}
