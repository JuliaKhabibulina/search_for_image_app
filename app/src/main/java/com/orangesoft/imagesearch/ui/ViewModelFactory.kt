package com.orangesoft.imagesearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orangesoft.imagesearch.data.ImageRepository

class ViewModelFactory(private val repository: ImageRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchImageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchImageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}