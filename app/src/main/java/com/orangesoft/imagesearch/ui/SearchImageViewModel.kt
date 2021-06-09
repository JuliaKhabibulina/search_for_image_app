package com.orangesoft.imagesearch.ui

import com.orangesoft.imagesearch.data.ImageRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchImageViewModel(private val repository: ImageRepository) : ViewModel() {
    var currentQueryValue: String = ""
        private set

    private var searchJob: Job? = null
    val adapter = ImageAdapter()

    val runSearch: (String) -> Unit = { query ->
        searchImage(query)
    }

    private fun searchImage(queryString: String) {
        if (searchJob?.isActive == true && queryString == currentQueryValue) {
            searchJob?.cancel("The same query")
        } else {
            currentQueryValue = queryString
            searchJob = repository.getSearchResultStream(currentQueryValue)
                .cachedIn(viewModelScope)
                .onEach { adapter.submitData(it) }
                .launchIn(viewModelScope)
        }
    }
}