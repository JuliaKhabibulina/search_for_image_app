package com.orangesoft.imagesearch.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.orangesoft.imagesearch.databinding.ImageItemBinding
import com.orangesoft.imagesearch.model.ImageItem

class ImageViewHolder private constructor(private var binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
    constructor(context: Context): this(ImageItemBinding.inflate(LayoutInflater.from(context)))

    fun bind(image: ImageItem) {
        binding.item = image
    }
}