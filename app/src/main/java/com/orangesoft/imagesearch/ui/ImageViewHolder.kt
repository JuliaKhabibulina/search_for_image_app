package com.orangesoft.imagesearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.orangesoft.imagesearch.R
import com.orangesoft.imagesearch.model.ImageItem

class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.tv_title)

    private var image: ImageItem? = null

    fun bind(image: ImageItem?) {
        if (image == null) {
            val resources = itemView.resources
            title.text = resources.getString(R.string.loading)
        } else {
            showImageData(image)
        }
    }

    private fun showImageData(image: ImageItem) {
        this.image = image
        title.text = image.title

    }

    companion object {
        fun create(parent: ViewGroup): ImageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.image_item, parent, false)
            return ImageViewHolder(view)
        }
    }
}