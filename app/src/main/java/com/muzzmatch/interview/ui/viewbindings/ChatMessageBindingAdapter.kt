package com.muzzmatch.interview.ui.viewbindings

import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.muzzmatch.interview.R
import com.muzzmatch.interview.data.entities.ChatMessage
import com.muzzmatch.interview.data.entities.MessageReadStatus
import com.muzzmatch.interview.data.entities.UiModel
import com.muzzmatch.interview.data.entities.User
import com.muzzmatch.interview.utils.separatorTimeStamp

@BindingAdapter("toolbarProfileImage")
fun bindToolbarProfileImage(toolbarProfileImage: ImageView, user: User) {
    val context = toolbarProfileImage.context
    Glide.with(context)
            .load(user.url)
            .circleCrop()
            .into(toolbarProfileImage)
}

@BindingAdapter("chatTextInput")
fun bindChatTextInput(chatMessageTextInput: EditText, chatEnterButton: ImageButton) {
    chatMessageTextInput.addTextChangedListener {
        val isTextInputEmpty = it.toString().isEmpty()
        chatEnterButton.alpha = if (isTextInputEmpty) 0.5f else 1.0f
        if (isTextInputEmpty) {
            chatEnterButton.isEnabled = false
            chatMessageTextInput.setBackgroundResource(R.drawable.grey_rounded_rectangle_background)
        } else {
            chatEnterButton.isEnabled = true
            chatMessageTextInput.setBackgroundResource(R.drawable.gradient_rounded_rectangle_background)
        }
    }
}

@BindingAdapter("chatMessageTextView")
fun bindChatMessageBackground(chatMessageTextView: TextView, chatMessage: ChatMessage) {
    val context = chatMessageTextView.context
    val isMe = chatMessage.isMe
    val resources = context.resources
    chatMessageTextView.apply {
        setTextColor(if (isMe) resources.getColor(R.color.white, null) else resources.getColor(R.color.black, null))
        text = chatMessage.message
        invalidate()
    }
}

@BindingAdapter("chatMessageFrameLayout")
fun bindChatMessageFrameLayout(chatMessageFrameLayout: FrameLayout, chatMessageUIItem: UiModel.ChatMessageUIItem) {
    val context = chatMessageFrameLayout.context
    val isMe = chatMessageUIItem.chatMessage.isMe
    val hasTail = chatMessageUIItem.hasTail

    chatMessageFrameLayout.apply {
        val backgroundResource = if (isMe) {
            if (hasTail) R.drawable.chat_sender_drawable_tail else R.drawable.chat_sender_drawable_regular
        } else {
            if (hasTail) R.drawable.chat_receiver_drawable_tail else R.drawable.chat_receiver_drawable_regular
        }
        background = context.getDrawable(backgroundResource)
        val params = layoutParams as FrameLayout.LayoutParams
        params.gravity = (if (isMe) Gravity.END else Gravity.START)
        layoutParams = params
        invalidate()
    }
}

@BindingAdapter("chatMessageTimeStamp")
fun bindChatMessageTimeStamp(chatMessageTimeStamp: TextView, chatMessageUIItem: UiModel.ChatMessageUIItem) {
    chatMessageTimeStamp.visibility = if (chatMessageUIItem.hasTimeStamp) View.VISIBLE else View.GONE
    chatMessageTimeStamp.text = chatMessageUIItem.chatMessage.date.separatorTimeStamp()
}

@BindingAdapter("chatMessageReadReceipts")
fun bindChatMessageReadReceipts(chatMessageReadReceipts: ImageView, chatMessage: ChatMessage) {
    chatMessageReadReceipts.isVisible = chatMessage.isMe
    when (chatMessage.messageReadStatus) {
        MessageReadStatus.Read,
        MessageReadStatus.Received -> R.drawable.ic_double_tick_indicator
        MessageReadStatus.Sent -> R.drawable.ic_double_tick_indicator
        else                       -> R.drawable.ic_double_tick_indicator
    }.let { chatMessageReadReceipts.setImageResource(it) }

    when (chatMessage.messageReadStatus) {
        MessageReadStatus.Sent -> R.color.white
        MessageReadStatus.Read -> R.color.gold
        MessageReadStatus.Received -> R.color.aqua_50
        else                       -> R.color.white
    }.let { chatMessageReadReceipts.setTint(it) }
}

fun ImageView.setTint(tintColor: Int) {
    DrawableCompat.setTint(
            DrawableCompat.wrap(drawable),
            ContextCompat.getColor(context, tintColor)
    );
}

