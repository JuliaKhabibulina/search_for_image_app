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

        initAdapter()

        viewModel.adapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .filter { it.refresh is LoadState.NotLoading }
            .launchIn(lifecycleScope)
    }

    private fun initAdapter() {

        viewModel.adapter.addLoadStateListener { loadState ->
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && viewModel.adapter.itemCount == 0
            showEmptyList(isListEmpty)

            binding.list.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
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
