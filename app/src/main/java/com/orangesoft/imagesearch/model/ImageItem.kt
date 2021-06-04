package com.orangesoft.imagesearch.model

import com.google.gson.annotations.SerializedName

data class ImageItem(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("owner") val owner: String,
    @field:SerializedName("secret") val secret: String,
    @field:SerializedName("server") val server: String?,
    @field:SerializedName("title") val title: String

)

