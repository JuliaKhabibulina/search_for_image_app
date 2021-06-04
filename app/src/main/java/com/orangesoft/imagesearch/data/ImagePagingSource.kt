package com.orangesoft.imagesearch.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.orangesoft.imagesearch.api.ImageService
import retrofit2.HttpException
import java.io.IOException
import com.orangesoft.imagesearch.data.ImageRepository.Companion.NETWORK_PAGE_SIZE
import com.orangesoft.imagesearch.model.Photo

private const val STARTING_PAGE_INDEX = 1

class ImagePagingSource(
    private val service: ImageService,
    private val query : String
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo>{
        val position = params.key ?: STARTING_PAGE_INDEX

        val apiQuery = query
        return try {
            val response = service.searchImage(apiQuery )
            val image = response.photos
            val nextKey = if (image.photo.isEmpty()){
                null
            }
            else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = image.photo,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position -1  ,
                nextKey = nextKey            )
        } catch (exception :IOException){
            return LoadResult.Error(exception)
        }catch (exception :HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let {anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}

