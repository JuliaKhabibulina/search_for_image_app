package com.orangesoft.imagesearch.ui

import com.orangesoft.imagesearch.data.ImageRepository
import com.orangesoft.imagesearch.model.ImageItem
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class SearchImageViewModel(private val repository: ImageRepository) : ViewModel() {
    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<ImageItem>>? = null

    fun searchImage(queryString: String): Flow<PagingData<ImageItem>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<ImageItem>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}