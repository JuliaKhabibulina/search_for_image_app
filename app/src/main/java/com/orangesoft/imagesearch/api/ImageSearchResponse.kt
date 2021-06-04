package com.orangesoft.imagesearch.api

import com.google.gson.annotations.SerializedName
import com.orangesoft.imagesearch.model.ImageItem

data class ImageSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<ImageItem> = emptyList(),
    val nextPage: Int? = null
)
