package com.muzzmatch.interview.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.muzzmatch.interview.adapter.ChatMessageAdapter
import com.muzzmatch.interview.data.entities.ChatMessage
import com.muzzmatch.interview.data.entities.UiModel
import com.muzzmatch.interview.databinding.ChatMessageFragmentBinding
import com.muzzmatch.interview.ui.viewmodels.ChatMessageViewModel
import com.muzzmatch.interview.utils.isTimeDifferenceGreaterThanOneHour
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime

/**
 * Fragment enabling a user to send messages with a given user.
 */
@AndroidEntryPoint
class ChatMessageFragment : Fragment() {

    private val viewModel: ChatMessageViewModel by viewModels()

    lateinit var adapter: ChatMessageAdapter
    private lateinit var binding: ChatMessageFragmentBinding
    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = ChatMessageFragmentBinding.inflate(inflater, container, false)
        binding.chatMessageViewModel = viewModel
        initAdapter()
        return binding.root
    }

    @ExperimentalTime
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun initAdapter() {
        adapter = ChatMessageAdapter()
        binding.chatRv.adapter = adapter
    }


    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(requireContext())
        binding.chatRv.layoutManager = layoutManager
        binding.chatRv.adapter = adapter
    }


    @ExperimentalTime
    private fun setupObservers() {
        viewModel.chatMessage.observe(viewLifecycleOwner, {
            it.convertToUIModelList().let(adapter::submitList)
            Handler(Looper.getMainLooper()).postDelayed({ binding.chatRv.smoothScrollToPosition(adapter.itemCount - 1); }, 200)
        })
        viewModel.chatMessageSubmitted.observe(viewLifecycleOwner, {
            Handler(Looper.getMainLooper()).postDelayed(
                    { binding.chatMessageTextLayout.chatEditText.text.clear() },
                    1000
            )
        })
    }
}


/**
 * If a previous message was sent more than an hour ago, or there is no previous messages,
 *
 * @param previousChatMessageUIItem chat message that precedes the current message chronologically
 */
@ExperimentalTime
fun UiModel.ChatMessageUIItem.updateTimestamp(previousChatMessageUIItem: UiModel.ChatMessageUIItem?): UiModel.ChatMessageUIItem {
    if (previousChatMessageUIItem == null) {
        this.hasTimeStamp = true
    } else {
        this.hasTimeStamp = isTimeDifferenceGreaterThanOneHour(
                this.chatMessage.date,
                previousChatMessageUIItem.chatMessage.date
        )
    }
    return this
}

/**
 * Determines the tail status of the chat message UI item based on the following three characteristics
 * If it is the most recent message in the conversation
 * The message after it is sent by the other user
 * The message after it was sent more than 20 seconds afterwards
 *
 * @param subsequentChatMessageUIItem chat message that succeeds the current message chronologically
 */
fun UiModel.ChatMessageUIItem.updateTailState(subsequentChatMessageUIItem: UiModel.ChatMessageUIItem?): UiModel.ChatMessageUIItem {
    if (subsequentChatMessageUIItem == null) {
        this.hasTail = true
    } else {
        val subsequentMessageHasDifferentUserName = this.chatMessage.userName != subsequentChatMessageUIItem.chatMessage.userName
        val subsequentMessageHasIs20SecondsOlder = subsequentChatMessageUIItem.chatMessage.date.toInstant().epochSecond - this.chatMessage.date.toInstant().epochSecond > 20
        this.hasTail = subsequentMessageHasDifferentUserName || subsequentMessageHasIs20SecondsOlder
    }
    return this
}

/** Converts list of most recent chat messages to a UI Model list including time stamp separators */
@ExperimentalTime
fun List<ChatMessage>.convertToUIModelList(): List<UiModel> {
    val mutableUIModelList = mutableListOf<UiModel>()
    this.forEachIndexed { index, _ ->
        val currentItem = UiModel.ChatMessageUIItem(this[index])
        val previousItem = if (index == 0) null else UiModel.ChatMessageUIItem(this[index - 1])
        val subsequentItem = if (index >= this.size - 1) null else UiModel.ChatMessageUIItem(this[index + 1])
        currentItem.updateTailState(subsequentItem)
        currentItem.updateTimestamp(previousItem)
        mutableUIModelList.add(currentItem)
    }
    return mutableUIModelList.toList()
}

