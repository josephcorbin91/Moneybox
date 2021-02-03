package com.moneybox.interview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneybox.interview.data.entities.AccountUserFriendly
import com.moneybox.interview.databinding.InvestorProductBinding
import com.moneybox.interview.ui.accounts.AccountsFragmentDirections

class AccountsAdapter() : ListAdapter<AccountUserFriendly, RecyclerView.ViewHolder>(ProductDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return PostViewHolder(
                InvestorProductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val product = getItem(position)
            (holder as PostViewHolder).bind(product)
        }

        class PostViewHolder(
            private val binding: InvestorProductBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            init {
                binding.setClickListener {
                    binding.accounts?.let { product ->
                        navigateToAccountDetail(product, it)
                    }
                }
            }

            private fun navigateToAccountDetail(
                    accountUserFriendly: AccountUserFriendly,
                    view: View
            ) {
                val direction =
                    AccountsFragmentDirections.actionAccountFragmentToAccountDetailFragment(
                            accountUserFriendly.id
                    )
                view.findNavController().navigate(direction)
            }

            fun bind(account: AccountUserFriendly) {
                binding.apply {
                    accounts = account
                    executePendingBindings()
                }
            }
        }
}


private class ProductDiffCallback : DiffUtil.ItemCallback<AccountUserFriendly>() {

    override fun areItemsTheSame(oldItem: AccountUserFriendly, newItem: AccountUserFriendly): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AccountUserFriendly, newItem: AccountUserFriendly): Boolean {
        return oldItem == newItem
    }
}
