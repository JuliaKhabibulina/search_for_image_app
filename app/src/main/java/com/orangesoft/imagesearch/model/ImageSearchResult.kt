package com.orangesoft.imagesearch.model

import java.lang.Exception

sealed class ImageSearchResult {
    data class Success(val data: List<ImageItem>) : ImageSearchResult()
    data class Error(val error: Exception) : ImageSearchResult()
}
