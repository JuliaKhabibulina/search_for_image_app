package com.orangesoft.imagesearch

import androidx.lifecycle.ViewModelProvider
import com.orangesoft.imagesearch.api.ImageService
import com.orangesoft.imagesearch.data.ImageRepository
import com.orangesoft.imagesearch.ui.ViewModelFactory

object Injection {


    private fun provideImageRepository(): ImageRepository {
        return ImageRepository(ImageService.create())
    }


    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideImageRepository())
    }
}
