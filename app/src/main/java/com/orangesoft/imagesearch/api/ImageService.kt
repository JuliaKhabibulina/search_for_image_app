package com.orangesoft.imagesearch.api

import com.orangesoft.imagesearch.model.Photos
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("rest/?method=flickr.photos.search&api_key=949ec7f84f7fdec0da0b220473e4fbbf&format=json")
    suspend fun searchImage(
        @Query("tags") tag: String
    ): Photos

    companion object {
        private const val BASE_URL = "https://www.flickr.com/services/"

        fun create(): ImageService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ImageService::class.java)
        }
    }
}