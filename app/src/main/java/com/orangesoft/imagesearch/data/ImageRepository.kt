package com.orangesoft.imagesearch.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.orangesoft.imagesearch.api.ImageService
import com.orangesoft.imagesearch.model.ImageItem
import kotlinx.coroutines.flow.Flow


class ImageRepository(private val service: ImageService) {
    fun getSearchResultStream(query: String): Flow<PagingData<ImageItem>> {
        Log.d("ImageRepository", "New query: $query")
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { ImagePagingSource(service, query) }
        ).flow
    }
    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }

}