package com.muzzmatch.interview.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.muzzmatch.interview.data.entities.UiModel
import com.muzzmatch.interview.databinding.ChatMessageViewItemBinding

/**
 * View Holder for a Chat Message RecyclerView list item.
 */
class ChatMessageViewHolder(
        private val binding: ChatMessageViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(message: UiModel.ChatMessageUIItem) {
        binding.apply {
            chatMessageUIModel = message
            executePendingBindings()
        }
    }
}