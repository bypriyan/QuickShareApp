package com.bypriyan.multishare.activity.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bypriyan.multishare.model.StorageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val repository: StorageRepository
) : ViewModel() {

    private val _internalStorageData = MutableLiveData<StorageData>()
    val internalStorageData: LiveData<StorageData> = _internalStorageData

    private val _externalStorageData = MutableLiveData<StorageData?>()
    val externalStorageData: LiveData<StorageData?> = _externalStorageData

    fun loadStorageData() {
        viewModelScope.launch {
            val internalData = repository.getInternalStorageData()
            _internalStorageData.value = internalData

            val externalData = repository.getExternalStorageData()
            _externalStorageData.value = externalData
        }
    }
}
