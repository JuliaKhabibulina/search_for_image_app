package com.orangesoft.imagesearch.model

data class Images(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<ImageItem>,
    val total: Int
)