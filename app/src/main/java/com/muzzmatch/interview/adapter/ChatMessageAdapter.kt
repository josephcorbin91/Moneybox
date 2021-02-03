package com.muzzmatch.interview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muzzmatch.interview.R
import com.muzzmatch.interview.data.entities.UiModel
import com.muzzmatch.interview.databinding.ChatMessageViewItemBinding
import com.muzzmatch.interview.ui.viewholder.ChatMessageViewHolder

class ChatMessageAdapter : ListAdapter<UiModel, RecyclerView.ViewHolder>(UiModelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChatMessageViewHolder(
                ChatMessageViewItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.ChatMessageUIItem -> R.layout.chat_message_view_item
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is UiModel.ChatMessageUIItem -> (holder as ChatMessageViewHolder).bind(uiModel)
            }
        }
    }
}

private class UiModelDiffCallback : DiffUtil.ItemCallback<UiModel>() {

    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem is UiModel.ChatMessageUIItem && newItem is UiModel.ChatMessageUIItem && oldItem.chatMessage.id == newItem.chatMessage.id
                && newItem.hasTimeStamp == oldItem.hasTimeStamp && newItem.hasTail == oldItem.hasTail
    }

    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem == newItem
    }
}
