package com.damar.submitinter2.adapter

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.damar.submitinter2.databinding.StoryLoadingBinding

class LoadingStateViewHolder(private val binding: StoryLoadingBinding, retry: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                binding.progressBar.isVisible = true
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
            }
            is LoadState.Error -> {
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = true
                binding.errorMsg.isVisible = true
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            else -> {
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
            }
        }
    }
}
