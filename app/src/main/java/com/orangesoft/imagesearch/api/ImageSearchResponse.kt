package com.orangesoft.imagesearch.api

import com.google.gson.annotations.SerializedName
import com.orangesoft.imagesearch.model.Photos

data class ImageSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Photos> = emptyList(),
    val nextPage: Int? = null
)
