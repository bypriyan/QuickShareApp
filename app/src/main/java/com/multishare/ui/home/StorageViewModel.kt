package com.multishare.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : ViewModel() {

    private val _internalStorageInfo = MutableLiveData<StorageInfo>()
    val internalStorageInfo: LiveData<StorageInfo> = _internalStorageInfo

    private val _externalStorageInfo = MutableLiveData<StorageInfo>()
    val externalStorageInfo: LiveData<StorageInfo> = _externalStorageInfo

    fun loadStorageInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val internalStorage = storageRepository.getInternalStorageInfo()
            val externalStorage = storageRepository.getExternalStorageInfo()

            _internalStorageInfo.postValue(internalStorage)
            _externalStorageInfo.postValue(externalStorage)
        }
    }
}