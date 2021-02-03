package com.moneybox.interview.ui.accountdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.moneybox.interview.R
import com.moneybox.interview.databinding.AccountDetailFragmentBinding
import com.moneybox.interview.databinding.AccountsFragmentBinding
import com.moneybox.interview.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


/**
 * A fragment representing a single Plant detail screen.
 */
@AndroidEntryPoint
class AccountDetailFragment : Fragment() {

    private val args: AccountDetailFragmentArgs by navArgs()

    private val accountDetailViewModel: AccountDetailViewModel by viewModels()
    private lateinit var binding: AccountDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<AccountDetailFragmentBinding>(
                inflater,
                R.layout.account_detail_fragment,
                container,
                false
        ).apply {
            viewModel = accountDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupObservers() {
        accountDetailViewModel.addMoneyResponse.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    Resource.Status.ERROR   -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    else                    -> {
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        val id = args.productId
        accountDetailViewModel.getAccountInformation(args.productId)
    }


}

