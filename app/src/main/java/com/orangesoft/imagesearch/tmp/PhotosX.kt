package com.orangesoft.imagesearch.tmp

data class PhotosX(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)