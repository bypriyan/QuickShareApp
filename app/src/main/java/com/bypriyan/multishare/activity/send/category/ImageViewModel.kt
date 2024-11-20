package com.bypriyan.multishare.activity.send.category

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bypriyan.multishare.model.ImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _files = MutableStateFlow<List<ImageModel>>(emptyList())
    val files = _files.asStateFlow()

    // Load files based on the file type
    fun loadFiles(fileType: FileType) {
        viewModelScope.launch {
            repository.getFiles(fileType).collect { fileList ->
                _files.value = fileList
            }
        }
    }
}
