package com.orangesoft.imagesearch.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.orangesoft.imagesearch.model.ImageItem

class ImageAdapter : PagingDataAdapter<ImageItem, ImageViewHolder>(IMAGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(parent.context)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.apply { holder.bind(this) }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean =
                oldItem == newItem
        }
    }
}