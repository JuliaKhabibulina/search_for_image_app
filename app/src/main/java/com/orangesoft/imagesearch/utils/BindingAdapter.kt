package com.orangesoft.imagesearch.utils

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.orangesoft.imagesearch.model.ImageItem

@BindingAdapter(value = ["action"])
fun EditText.runSearch(action: (String) -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_GO) {
            action.invoke(this.text.trim().toString())
            true
        } else {
            false
        }
    }
}

@BindingAdapter(value = ["submitAction"])
fun EditText.submitAction(action: (String) -> Unit) {
    this.setOnKeyListener { _, keyCode, event ->
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            action.invoke(this.text.trim().toString())
            true
        } else {
            false
        }
    }
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(item: ImageItem) {
    Glide.with(context)
        .load(item.url_s)
        .into(this)
}
