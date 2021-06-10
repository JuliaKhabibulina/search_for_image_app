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
import kotlinx.coroutines.flow.*

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
                        showEmptyList(true, state.source.refresh)
                    }
                    else -> {
                        showEmptyList(false, state.source.refresh)
                    }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun showEmptyList(show: Boolean, loadState: LoadState) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
            binding.list.scrollToPosition(0)
        }

        binding.list.isVisible = loadState is LoadState.NotLoading
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
    }
}
