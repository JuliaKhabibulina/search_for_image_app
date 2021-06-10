package com.orangesoft.imagesearch.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.orangesoft.imagesearch.Injection
import com.orangesoft.imagesearch.R
import com.orangesoft.imagesearch.databinding.ActivitySearchImageBinding
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchImageBinding
    private lateinit var viewModel: SearchImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_image)

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(SearchImageViewModel::class.java)

        binding.imageQuery.requestFocus()
        binding.viewmodel = viewModel

        viewModel.adapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .onEach { state ->
                when (state.refresh) {
                    is LoadState.Error -> {
                        showEmptyList(true)
                        binding.list.isVisible = state.source.refresh is LoadState.NotLoading
                        binding.progressBar.isVisible = state.source.refresh is LoadState.Loading
                        binding.retryButton.isVisible = state.source.refresh is LoadState.Error
                    }

                    else -> {
                        showEmptyList(false)
                        binding.list.isVisible = state.source.refresh is LoadState.NotLoading
                        binding.progressBar.isVisible = state.source.refresh is LoadState.Loading
                        binding.retryButton.isVisible = state.source.refresh is LoadState.Error
                    }
                }
            }
           .launchIn(lifecycleScope)
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }
    }
}
